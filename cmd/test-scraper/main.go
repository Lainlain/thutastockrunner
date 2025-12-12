package main

import (
	"context"
	"encoding/json"
	"fmt"
	"log"
	"time"

	"thaistockanalysis/internal/model"
	"thaistockanalysis/internal/scraper"
	"thaistockanalysis/internal/transformer"
)

func main() {
	fmt.Println("🧪 Real Data Scraping Test")
	fmt.Println("=" + string(make([]byte, 50)))
	fmt.Println()

	// Create scraper client with default board URL
	scraperClient := scraper.NewClient("", nil) // Empty string uses default URL

	// Create context with timeout
	ctx, cancel := context.WithTimeout(context.Background(), 30*time.Second)
	defer cancel()

	// Fetch the board to see available topics
	fmt.Println("📋 Fetching board...")
	topics, err := scraperClient.FetchBoard(ctx)
	if err != nil {
		log.Fatalf("❌ Failed to fetch board: %v", err)
	}

	fmt.Printf("✅ Found %d topics\n\n", len(topics))

	// Try to fetch morning close session data
	session := model.Session{
		Slot:  model.SlotMorning,
		Phase: model.PhaseClose,
	}

	fmt.Println("📊 Scraping Morning Close session data...")
	fmt.Println()

	// Find and fetch the first topic with market data
	var detail model.TopicDetail
	found := false

	for i, summary := range topics {
		if i >= 5 { // Check first 5 topics
			break
		}

		fmt.Printf("   Checking topic %d: %s\n", i+1, summary.TopicID)

		detail, err = scraperClient.FetchTopic(ctx, summary, session)
		if err != nil {
			fmt.Printf("   ⚠️  Skipped: %v\n", err)
			continue
		}

		// Check if we have market data
		if detail.Index != "" {
			found = true
			fmt.Printf("   ✅ Found market data!\n\n")
			break
		}
	}

	if !found {
		log.Fatal("❌ No market data found in any topics")
	}

	// Display scraped raw data
	fmt.Println("📦 RAW SCRAPED DATA:")
	fmt.Println("=" + string(make([]byte, 50)))
	fmt.Printf("Date:          %s\n", detail.Summary.Date.Format("2006-01-02"))
	fmt.Printf("Thai Date:     %s\n", detail.Summary.ThaiDateRaw)
	fmt.Printf("Headline:      %s\n", detail.Headline)
	fmt.Printf("SET Index:     %s\n", detail.Index)
	fmt.Printf("Change:        %s\n", detail.Change)
	fmt.Printf("Topic URL:     %s\n", detail.Summary.URL)
	fmt.Println()

	// Transform the data
	fmt.Println("🔄 TRANSFORMATION:")
	fmt.Println("=" + string(make([]byte, 50)))

	stockData := transformer.TransformToStockAnalysis(detail, "morning")

	fmt.Printf("Date:          %s\n", stockData.Date)
	fmt.Printf("Session:       %s\n", stockData.Session)
	fmt.Printf("Open Set:      %s  (full SET index)\n", stockData.OpenSet)
	fmt.Printf("Change:        %s  (exact change)\n", stockData.Change)
	fmt.Println()

	// Show digit extraction logic
	fmt.Println("📐 DIGIT EXTRACTION LOGIC:")
	fmt.Println("=" + string(make([]byte, 50)))
	fmt.Printf("SET Index:     %s\n", detail.Index)
	fmt.Printf("  → Last digit before decimal: %s\n", stockData.NumberGroups[0].Digit)
	fmt.Println()
	fmt.Printf("Change:        %s\n", detail.Change)
	fmt.Printf("  → Rounded value: %s (decimal ≥ 0.5 rounds up)\n", stockData.NumberGroups[1].Digit)
	fmt.Println()

	// Display number groups
	fmt.Println("🎲 NUMBER GROUPS (2D Lottery Numbers):")
	fmt.Println("=" + string(make([]byte, 50)))
	for i, group := range stockData.NumberGroups {
		fmt.Printf("Group %d - Digit '%s':\n", i+1, group.Digit)
		fmt.Printf("  Numbers: %v\n", group.Numbers)
		fmt.Println()
	}

	// Show as JSON
	fmt.Println("📋 FINAL API PAYLOAD (JSON):")
	fmt.Println("=" + string(make([]byte, 50)))
	jsonData, err := json.MarshalIndent(stockData, "", "  ")
	if err != nil {
		log.Fatalf("Failed to marshal JSON: %v", err)
	}
	fmt.Println(string(jsonData))
	fmt.Println()

	fmt.Println("✅ Test completed successfully!")
	fmt.Println()
	fmt.Println("👉 Please verify:")
	fmt.Println("   1. Does the digit extraction look correct?")
	fmt.Println("   2. Are the 2D numbers (00-99) generated properly?")
	fmt.Println("   3. Is the rounding logic working for the change value?")
}
