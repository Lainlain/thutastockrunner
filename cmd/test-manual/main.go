package main

import (
	"context"
	"encoding/json"
	"fmt"
	"log"
	"time"

	"thaistockanalysis/internal/api"
	"thaistockanalysis/internal/config"
	"thaistockanalysis/internal/model"
	"thaistockanalysis/internal/scraper"
	"thaistockanalysis/internal/transformer"
)

func main() {
	fmt.Println("🧪 Manual Scraper Test - NO SCHEDULER")
	fmt.Println("=" + string(make([]byte, 60)))
	fmt.Println()

	// Load config
	cfg := config.Load()
	fmt.Printf("📡 API Endpoint: %s\n", cfg.APIBaseURL+cfg.APIEndpoint)
	fmt.Println()

	// Create scraper client
	scraperClient := scraper.NewClient("", nil) // Use default URL
	
	// Create API client
	apiClient := api.NewClient(cfg)
	
	// Create context with timeout
	ctx, cancel := context.WithTimeout(context.Background(), 30*time.Second)
	defer cancel()

	// Try to scrape data NOW
	fmt.Println("📋 Step 1: Fetching board from lottosociety.com...")
	topics, err := scraperClient.FetchBoard(ctx)
	if err != nil {
		log.Fatalf("❌ Failed to fetch board: %v", err)
	}
	fmt.Printf("✅ Found %d topics on the board\n\n", len(topics))

	// Try to fetch morning close session data (use any session for now)
	session := model.Session{
		Slot:  model.SlotMorning,
		Phase: model.PhaseClose,
	}

	fmt.Println("📊 Step 2: Looking for topic with market data...")
	var detail model.TopicDetail
	found := false
	
	for i, summary := range topics {
		if i >= 5 { // Check first 5 topics
			break
		}

		fmt.Printf("   Checking topic %d: %s\n", i+1, summary.TopicID)
		
		detail, err = scraperClient.FetchTopic(ctx, summary, session)
		if err != nil {
			fmt.Printf("   ⚠️  Error: %v\n", err)
			continue
		}

		// Check if we have market data
		if detail.Index != "" {
			found = true
			fmt.Printf("   ✅ Found market data!\n\n")
			break
		} else {
			fmt.Printf("   ⚠️  No market data (Index empty)\n")
		}
	}

	if !found {
		log.Fatal("❌ No market data found in any topics")
	}

	// Display scraped raw data
	fmt.Println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━")
	fmt.Println("📦 RAW DATA FROM LOTTOSOCIETY.COM")
	fmt.Println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━")
	fmt.Printf("Date:          %s\n", detail.Summary.Date.Format("2006-01-02"))
	fmt.Printf("Thai Date:     %s\n", detail.Summary.ThaiDateRaw)
	fmt.Printf("Headline:      %s\n", detail.Headline)
	fmt.Printf("SET Index:     %s\n", detail.Index)
	fmt.Printf("Change:        %s\n", detail.Change)
	fmt.Printf("Topic URL:     %s\n", detail.Summary.URL)
	fmt.Println()

	// Transform the data
	fmt.Println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━")
	fmt.Println("🔄 TRANSFORMATION TO API FORMAT")
	fmt.Println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━")
	
	stockData := transformer.TransformToStockAnalysis(detail, "morning")
	
	fmt.Printf("Date:          %s\n", stockData.Date)
	fmt.Printf("Session:       %s\n", stockData.Session)
	fmt.Printf("Open Set:      %s\n", stockData.OpenSet)
	fmt.Printf("Change:        %s\n", stockData.Change)
	fmt.Println()
	
	fmt.Println("Number Groups:")
	for i, group := range stockData.NumberGroups {
		fmt.Printf("  Group %d - Digit '%s': %v\n", i+1, group.Digit, group.Numbers)
	}
	fmt.Println()

	// Show as JSON
	fmt.Println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━")
	fmt.Println("📋 JSON PAYLOAD TO POST")
	fmt.Println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━")
	jsonData, err := json.MarshalIndent(stockData, "", "  ")
	if err != nil {
		log.Fatalf("Failed to marshal JSON: %v", err)
	}
	fmt.Println(string(jsonData))
	fmt.Println()

	// Ask user if they want to post to API
	fmt.Println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━")
	fmt.Println("📤 POSTING TO API")
	fmt.Println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━")
	
	err = apiClient.PostStockAnalysis(ctx, stockData)
	if err != nil {
		fmt.Printf("❌ Failed to post to API: %v\n", err)
		apiClient.LogFailedPost(stockData, err)
	} else {
		fmt.Println("✅ Successfully posted to API!")
	}
	fmt.Println()

	fmt.Println("✅ Test completed!")
	fmt.Println()
	fmt.Println("👉 Check:")
	fmt.Println("   1. Is the scraped data correct?")
	fmt.Println("   2. Are the number groups calculated properly?")
	fmt.Println("   3. Did the API accept the data?")
}
