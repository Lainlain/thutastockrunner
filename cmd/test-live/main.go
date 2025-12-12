package main

import (
	"context"
	"flag"
	"log"
	"time"

	"thaistockanalysis/internal/api"
	"thaistockanalysis/internal/config"
	"thaistockanalysis/internal/model"
	"thaistockanalysis/internal/scraper"
	"thaistockanalysis/internal/transformer"
)

const requestTimeout = 45 * time.Second

func main() {
	// Command line flags
	session := flag.String("session", "morning", "Session type: morning or evening")
	flag.Parse()

	// Validate session
	if *session != "morning" && *session != "evening" {
		log.Fatal("❌ Invalid session. Use: morning or evening")
	}

	log.Printf("🧪 Live Test Mode - Scraping and Posting to API")
	log.Printf("📋 Session: %s", *session)

	// Load configuration
	cfg := config.Load()
	log.Printf("📡 API Endpoint: %s", cfg.GetFullURL())

	// Initialize clients
	// Use IP address to bypass DNS issue (104.21.78.28 is www.lottosociety.com)
	scraperClient := scraper.NewClient("https://104.21.78.28/index.php?board=4.0", nil)
	apiClient := api.NewClient(cfg)

	// Scrape data
	log.Printf("🌐 Scraping lottosociety.com...")
	ctx, cancel := context.WithTimeout(context.Background(), requestTimeout)
	defer cancel()

	// Fetch board to get topic summaries
	summaries, err := scraperClient.FetchBoard(ctx)
	if err != nil {
		log.Fatalf("❌ Board fetch failed: %v", err)
	}

	if len(summaries) == 0 {
		log.Fatal("❌ No topics found")
	}

	// Use "close" session type for scraping (we'll map to morning/evening for API)
	sessionType := model.Session{Slot: model.SlotMorning, Phase: model.PhaseClose}
	if *session == "evening" {
		sessionType = model.Session{Slot: model.SlotAfternoon, Phase: model.PhaseClose}
	}

	// Get detail from first topic
	detail, err := scraperClient.FetchTopic(ctx, summaries[0], sessionType)
	if err != nil {
		log.Fatalf("❌ Topic fetch failed: %v", err)
	}

	log.Printf("✅ Scraped data:")
	log.Printf("   Title: %s", detail.Summary.Title)
	log.Printf("   Date: %s", detail.Summary.Date.Format("2006-01-02"))
	log.Printf("   SET Index: %s", detail.Index)
	log.Printf("   Change: %s", detail.Change)

	// Transform to API format
	payload := transformer.TransformToStockAnalysis(detail, *session)

	log.Printf("🔄 Transformed payload:")
	log.Printf("   Date: %s", payload.Date)
	log.Printf("   Session: %s", payload.Session)
	log.Printf("   Open Set: %s", payload.OpenSet)
	log.Printf("   Change: %s", payload.Change)
	log.Printf("   Number Groups: %d", len(payload.NumberGroups))
	for i, group := range payload.NumberGroups {
		log.Printf("     Group %d - Digit '%s': %v", i+1, group.Digit, group.Numbers)
	}

	// Post to API
	log.Printf("📤 Posting to API...")
	if err := apiClient.PostStockAnalysis(ctx, payload); err != nil {
		log.Fatalf("❌ API post failed: %v", err)
	}

	log.Printf("✅ SUCCESS! Data posted to API")
	log.Printf("🎯 Verify at: %s?date=%s", cfg.GetFullURL(), payload.Date)
}
