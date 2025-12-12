package main

import (
	"encoding/json"
	"fmt"
	"time"

	"thaistockanalysis/internal/model"
	"thaistockanalysis/internal/transformer"
)

func main() {
	fmt.Println("🧪 Transformer Logic Test (Mock Data)")
	fmt.Println("=" + string(make([]byte, 60)))
	fmt.Println()

	// Create mock scraped data - simulating real Thai stock data
	testCases := []struct {
		name    string
		index   string
		change  string
		session string
	}{
		{
			name:    "Test Case 1",
			index:   "1245.12",
			change:  "-3.65",
			session: "morning",
		},
		{
			name:    "Test Case 2",
			index:   "1305.23",
			change:  "+2.45",
			session: "evening",
		},
		{
			name:    "Test Case 3",
			index:   "1412.89",
			change:  "+5.78",
			session: "morning",
		},
	}

	for i, tc := range testCases {
		fmt.Printf("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n")
		fmt.Printf("📊 %s: %s\n", tc.name, tc.session)
		fmt.Printf("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n")

		// Create mock detail
		detail := model.TopicDetail{
			Summary: model.TopicSummary{
				Date:        time.Now(),
				ThaiDateRaw: "12 ธันวาคม 2568",
				TopicID:     fmt.Sprintf("mock-%d", i),
				URL:         "https://example.com",
			},
			Headline: "ตลาดหุ้นไทยวันนี้",
			Index:    tc.index,
			Change:   tc.change,
		}

		fmt.Println("📦 INPUT (Scraped Data):")
		fmt.Println("─────────────────────────────────────────────────────────────")
		fmt.Printf("  SET Index:     %s\n", detail.Index)
		fmt.Printf("  Change:        %s\n", detail.Change)
		fmt.Println()

		// Transform the data
		stockData := transformer.TransformToStockAnalysis(detail, tc.session)

		fmt.Println("🔄 TRANSFORMATION LOGIC:")
		fmt.Println("─────────────────────────────────────────────────────────────")

		// Extract last digit before decimal from index
		indexParts := splitByDot(tc.index)
		lastDigitIndex := ""
		if len(indexParts[0]) > 0 {
			lastDigitIndex = string(indexParts[0][len(indexParts[0])-1])
		}
		fmt.Printf("  1️⃣  SET Index '%s' → Last digit before decimal = '%s'\n", tc.index, lastDigitIndex)

		// Extract and round change
		changeVal := extractNumber(tc.change)
		rounded := int(changeVal + 0.5)
		fmt.Printf("  2️⃣  Change '%s' → %.2f rounds to %d (≥0.5 rounds up)\n", tc.change, changeVal, rounded)
		fmt.Println()

		fmt.Println("✅ OUTPUT (API Payload):")
		fmt.Println("─────────────────────────────────────────────────────────────")
		fmt.Printf("  Date:          %s\n", stockData.Date)
		fmt.Printf("  Session:       %s\n", stockData.Session)
		fmt.Printf("  Open Set:      %s  (full SET index)\n", stockData.OpenSet)
		fmt.Printf("  Change:        %s  (exact change)\n", stockData.Change)
		fmt.Println()

		fmt.Println("  Number Groups:")
		for j, group := range stockData.NumberGroups {
			fmt.Printf("    Group %d - Digit '%s':\n", j+1, group.Digit)
			fmt.Printf("      Numbers: %v\n", group.Numbers)
		}
		fmt.Println()

		// Show as JSON
		fmt.Println("📋 JSON Format:")
		fmt.Println("─────────────────────────────────────────────────────────────")
		jsonData, _ := json.MarshalIndent(stockData, "  ", "  ")
		fmt.Printf("  %s\n", string(jsonData))
		fmt.Println()
	}

	fmt.Println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━")
	fmt.Println("✅ All test cases completed!")
	fmt.Println()
	fmt.Println("👉 Verify:")
	fmt.Println("   ✓ open_set = full SET index (e.g., '1245.12')")
	fmt.Println("   ✓ change = exact change value (e.g., '-3.65')")
	fmt.Println("   ✓ Digit 1 = last digit before decimal in index")
	fmt.Println("   ✓ Digit 2 = rounded change value")
	fmt.Println("   ✓ Each digit generates 10 numbers (X0 to X9)")
}

func splitByDot(s string) []string {
	result := []string{"", ""}
	dotIndex := -1
	for i, c := range s {
		if c == '.' {
			dotIndex = i
			break
		}
	}
	if dotIndex == -1 {
		result[0] = s
	} else {
		result[0] = s[:dotIndex]
		if dotIndex+1 < len(s) {
			result[1] = s[dotIndex+1:]
		}
	}
	return result
}

func extractNumber(s string) float64 {
	var result float64
	cleaned := s
	if len(s) > 0 && (s[0] == '+' || s[0] == '-') {
		cleaned = s[1:]
	}
	fmt.Sscanf(cleaned, "%f", &result)
	return result
}
