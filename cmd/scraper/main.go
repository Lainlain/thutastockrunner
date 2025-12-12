package main

import (
	"context"
	"fmt"
	"log"
	"os/signal"
	"strings"
	"syscall"
	"time"

	"thaistockanalysis/internal/api"
	"thaistockanalysis/internal/config"
	"thaistockanalysis/internal/model"
	"thaistockanalysis/internal/scraper"
	"thaistockanalysis/internal/timeutil"
	"thaistockanalysis/internal/transformer"
)

const (
	checkInterval   = time.Second
	requestTimeout  = 45 * time.Second
	maxTopicsToShow = 5
)

func main() {
	ctx, stop := signal.NotifyContext(context.Background(), syscall.SIGINT, syscall.SIGTERM)
	defer stop()

	// Load configuration
	cfg := config.Load()
	log.Printf("🚀 Stock Analysis Runner starting...")
	log.Printf("📡 API Endpoint: %s", cfg.GetFullURL())
	log.Printf("🌍 Environment: %s", cfg.Environment)
	log.Printf("🕐 Timezone: %s", timeutil.Location())

	// Initialize clients
	scraperClient := scraper.NewClient("", nil)
	apiClient := api.NewClient(cfg)

	if next, ok := timeutil.NextWindow(time.Now()); ok {
		log.Printf("⏰ Next window: %s from %s to %s", next.Session, next.Start.Format(time.RFC3339), next.End.Format(time.RFC3339))
	}

	runScheduler(ctx, scraperClient, apiClient)
}

func runScheduler(ctx context.Context, scraperClient *scraper.Client, apiClient *api.Client) {
	ticker := time.NewTicker(checkInterval)
	defer ticker.Stop()

	// Track which sessions have been posted today
	postedSessions := make(map[string]struct{})
	currentDay := ""

	// Store data for combining open and close
	sessionData := make(map[string]model.TopicDetail)

	for {
		select {
		case <-ctx.Done():
			log.Printf("Shutting down: %v", ctx.Err())
			return
		case now := <-ticker.C:
			local := now.In(timeutil.Location())
			day := local.Format("2006-01-02")
			if day != currentDay {
				postedSessions = make(map[string]struct{})
				sessionData = make(map[string]model.TopicDetail)
				currentDay = day
				log.Printf("📅 New trading day detected (%s)", day)
			}

			win, ok := timeutil.WindowAt(now)
			if !ok {
				continue
			}

			session := win.Session
			log.Printf("🔍 Detected window: %s", session)

			// Scrape the data
			runCtx, cancel := context.WithTimeout(ctx, requestTimeout)
			detail, err := scrapeSession(runCtx, scraperClient, session)
			cancel()

			if err != nil {
				log.Printf("❌ Scrape failed for %s: %v", session, err)
				continue
			}

			// Store the scraped data
			sessionKey := fmt.Sprintf("%s_%s", session.Slot, session.Phase)
			sessionData[sessionKey] = detail
			log.Printf("✅ Scraped %s: SET=%s, Change=%s", session, detail.Index, detail.Change)

			// Post after close sessions
			shouldPost := false
			var apiSession string

			if session.Slot == model.SlotMorning && session.Phase == model.PhaseClose {
				apiSession = "morning"
				shouldPost = true
			} else if session.Slot == model.SlotAfternoon && session.Phase == model.PhaseClose {
				apiSession = "evening"
				shouldPost = true
			}

			if shouldPost {
				postKey := fmt.Sprintf("%s:%s", apiSession, day)
				if _, done := postedSessions[postKey]; !done {
					// Use the close data (most recent)
					log.Printf("📊 Posting %s session data to API", apiSession)
					err = postToAPI(ctx, apiClient, detail, apiSession)
					if err != nil {
						log.Printf("❌ Failed to post %s session: %v", apiSession, err)
						apiClient.LogFailedPost(transformer.TransformToStockAnalysis(detail, apiSession), err)
					} else {
						postedSessions[postKey] = struct{}{}
						log.Printf("✅ Successfully posted %s session", apiSession)
					}
				}
			}

			if next, ok := timeutil.NextWindow(win.End.Add(time.Millisecond)); ok {
				log.Printf("⏰ Next window: %s at %s", next.Session, next.Start.Format("15:04:05"))
			}
		}
	}
}

func scrapeSession(ctx context.Context, client *scraper.Client, session model.Session) (model.TopicDetail, error) {
	topics, err := client.FetchBoard(ctx)
	if err != nil {
		return model.TopicDetail{}, fmt.Errorf("board fetch failed: %w", err)
	}

	// Process topics until we find one with valid market data
	for i, summary := range topics {
		if i >= maxTopicsToShow {
			break
		}

		detail, err := client.FetchTopic(ctx, summary, session)
		if err != nil {
			log.Printf("topic %s skipped: %v", summary.TopicID, err)
			continue
		}

		// If we have valid market data, return it
		if detail.Index != "" {
			log.Printf("📊 Found market data: SET=%s, Change=%s", detail.Index, detail.Change)
			return detail, nil
		}
	}

	return model.TopicDetail{}, fmt.Errorf("no valid market data found")
}

// postToAPI transforms scraped data and posts to the main API
func postToAPI(ctx context.Context, apiClient *api.Client, detail model.TopicDetail, session string) error {
	// Transform the scraped data to API format
	stockData := transformer.TransformToStockAnalysis(detail, session)

	log.Printf("📤 Posting to API: Date=%s, Session=%s, Index=%s, Change=%s, Groups=%d numbers",
		stockData.Date, stockData.Session, stockData.OpenSet, stockData.Change, len(stockData.NumberGroups))

	// Post to the API
	return apiClient.PostStockAnalysis(ctx, stockData)
}

func printDetail(detail model.TopicDetail, session model.Session) {
	fmt.Println("================================")
	fmt.Printf("Date: %s (Thai: %s)\n", detail.Summary.Date.Format("2006-01-02"), detail.Summary.ThaiDateRaw)
	fmt.Printf("Session: %s\n", session)
	fmt.Printf("Title: %s\n", detail.Headline)
	fmt.Printf("SET Index: %s\n", fallback(detail.Index, "-"))
	fmt.Printf("Change: %s\n", fallback(detail.Change, "-"))
	fmt.Printf("Topic URL: %s\n", detail.Summary.URL)
}

func fallback(value, alt string) string {
	if strings.TrimSpace(value) == "" {
		return alt
	}
	return value
}
