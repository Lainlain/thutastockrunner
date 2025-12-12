package transformer

import (
	"fmt"
	"strings"

	"thaistockanalysis/internal/model"
)

// TransformToStockAnalysis converts scraped data to API request format
func TransformToStockAnalysis(detail model.TopicDetail, session string) model.StockAnalysisRequest {
	// Use full SET index as open_set (e.g., "1245.12")
	openSet := detail.Index

	// Use change exactly as scraped (e.g., "-3.65" or "+3.65")
	change := formatChange(detail.Change)

	// Calculate number groups from the last digit before decimal of both index and change
	numberGroups := CalculateNumberGroups(detail.Index, change)

	return model.StockAnalysisRequest{
		Date:         detail.Summary.Date.Format("2006-01-02"),
		Session:      session, // "morning" or "evening"
		OpenSet:      openSet,
		Change:       change,
		NumberGroups: numberGroups,
	}
}

// extractOpenSet gets the first 2 digits from SET index
// Example: "1305.23" -> "13"
func extractOpenSet(index string) string {
	if index == "" {
		return "00"
	}

	// Remove decimal point
	cleaned := strings.ReplaceAll(index, ".", "")

	// Get first 2 digits
	if len(cleaned) >= 2 {
		return cleaned[:2]
	}

	// Fallback
	return "00"
}

// formatChange ensures the change value has proper +/- sign
func formatChange(change string) string {
	change = strings.TrimSpace(change)
	if change == "" {
		return "0.00"
	}

	// If it doesn't start with + or -, add appropriate sign
	if !strings.HasPrefix(change, "+") && !strings.HasPrefix(change, "-") {
		return "+" + change
	}

	return change
}

// CalculateNumberGroups generates exactly 2 number groups from SET index and change
// Logic:
// - Digit 1: Last digit before decimal in SET index (e.g., "1245.12" -> 5)
// - Digit 2: Rounded integer from change (e.g., "-3.65" -> 4, because 3.65 rounds to 4)
// - Each digit generates 2D numbers (00-99)
func CalculateNumberGroups(index string, change string) []model.NumberGroup {
	// Extract digit 1: last digit before decimal in index
	digit1 := extractLastDigitBeforeDecimal(index)

	// Extract digit 2: rounded value from change
	digit2 := extractRoundedChangeDigit(change)

	// Generate 2D numbers for each digit (0-9 combinations)
	group1Numbers := generate2DNumbers(digit1)
	group2Numbers := generate2DNumbers(digit2)

	return []model.NumberGroup{
		{
			Digit:   fmt.Sprintf("%d", digit1),
			Numbers: group1Numbers,
		},
		{
			Digit:   fmt.Sprintf("%d", digit2),
			Numbers: group2Numbers,
		},
	}
}

// extractLastDigitBeforeDecimal gets the last digit before decimal point
// Example: "1245.12" -> 5
func extractLastDigitBeforeDecimal(index string) int {
	if index == "" {
		return 0
	}

	// Find decimal point
	parts := strings.Split(index, ".")
	if len(parts) == 0 {
		return 0
	}

	// Get the integer part
	intPart := parts[0]
	if len(intPart) == 0 {
		return 0
	}

	// Get last digit
	lastChar := intPart[len(intPart)-1]
	digit := int(lastChar - '0')

	if digit < 0 || digit > 9 {
		return 0
	}

	return digit
}

// extractRoundedChangeDigit extracts and rounds the change value
// Example: "-3.65" -> 4 (because 3.65 rounds to 4)
// Example: "+2.45" -> 2 (because 2.45 rounds to 2)
func extractRoundedChangeDigit(change string) int {
	if change == "" {
		return 0
	}

	// Remove +/- sign
	cleaned := strings.TrimPrefix(strings.TrimPrefix(change, "+"), "-")
	cleaned = strings.TrimSpace(cleaned)

	// Parse as float
	var value float64
	_, err := fmt.Sscanf(cleaned, "%f", &value)
	if err != nil {
		return 0
	}

	// Round to nearest integer
	// If decimal >= 0.5, round up
	rounded := int(value + 0.5)

	// Get last digit (for numbers > 9)
	return rounded % 10
}

// generate2DNumbers generates all 10 2D lottery numbers for a given digit
// Example: digit 5 -> [50, 51, 52, 53, 54, 55, 56, 57, 58, 59]
func generate2DNumbers(digit int) []int {
	numbers := make([]int, 10)
	for i := 0; i < 10; i++ {
		numbers[i] = digit*10 + i
	}
	return numbers
}

// parseToInt safely parses string to int
func parseToInt(s string) (int, error) {
	var num int
	_, err := fmt.Sscanf(s, "%d", &num)
	return num, err
}

// removeDuplicates removes duplicate numbers from slice
func removeDuplicates(numbers []int) []int {
	seen := make(map[int]bool)
	var result []int

	for _, num := range numbers {
		if !seen[num] {
			seen[num] = true
			result = append(result, num)
		}
	}

	return result
}
