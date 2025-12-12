package scraper

import (
	"regexp"
	"sort"
	"strings"
)

var numberLinePattern = regexp.MustCompile(`(?P<index>\d{4}\.\d{2})\s*([\s-]+)(?P<change>[+-]?\d+\.\d{2})`)

// splitNumberLine extracts the index and change values from a formatted string
// such as "1400.12 - 5.34".
func splitNumberLine(line string) (index, change string, ok bool) {
	line = strings.TrimSpace(strings.ReplaceAll(line, "\u00a0", " "))
	if line == "" {
		return "", "", false
	}

	match := numberLinePattern.FindStringSubmatch(line)
	if len(match) >= 4 {
		return match[1], match[3], true
	}

	fields := strings.Fields(line)
	if len(fields) >= 3 {
		return fields[0], fields[1] + fields[2], true
	}
	return "", "", false
}

// adjustDouble replicates the AdjustDouble behaviour from the original B4J code.
func adjustDouble(input string) string {
	if input == "" {
		return ""
	}
	parts := strings.SplitN(input, ".", 2)
	if len(parts) == 0 {
		return input
	}
	intPart := parts[0]
	if len(parts) == 2 && len(parts[1]) > 0 {
		if parts[1][0] > '5' {
			carry := addOne(intPart)
			if carry == "10" {
				return "0"
			}
			return carry
		}
	}
	if intPart == "10" {
		return "0"
	}
	return intPart
}

func addOne(s string) string {
	bytes := []byte(s)
	carry := byte(1)
	for i := len(bytes) - 1; i >= 0; i-- {
		sum := bytes[i] - '0' + carry
		bytes[i] = sum%10 + '0'
		carry = sum / 10
		if carry == 0 {
			break
		}
	}
	if carry > 0 {
		bytes = append([]byte{'1'}, bytes...)
	}
	return string(bytes)
}

// digitSumMod10 sums digits of a numeric string and returns the mod 10.
func digitSumMod10(s string) int {
	sum := 0
	for _, r := range s {
		if r < '0' || r > '9' {
			continue
		}
		sum += int(r - '0')
	}
	return sum % 10
}

// mostFrequentDigit returns the digit that appears most often in the supplied string.
func mostFrequentDigit(input string) int {
	counts := make([]int, 10)
	for _, r := range input {
		if r < '0' || r > '9' {
			continue
		}
		counts[r-'0']++
	}
	maxDigit := 0
	maxCount := 0
	for d, c := range counts {
		if c > maxCount {
			maxCount = c
			maxDigit = d
		}
	}
	return maxDigit
}

// combineDigitWithList attaches the provided digit to each rune in the string and returns a list of pairs.
func combineDigitWithList(digit int, digits string) []string {
	result := make([]string, 0, len(digits))
	for _, r := range digits {
		if r < '0' || r > '9' {
			continue
		}
		result = append(result, strings.TrimSpace(strings.Join([]string{string('0' + digit), string(r)}, "")))
	}
	sort.Strings(result)
	return result
}

// addSevenLastDigit mirrors Add7AndReturnLastDigit in the legacy code.
func addSevenLastDigit(n int) int {
	return (n + 7) % 10
}

// generateThreeGradeIDsFromSingleDigit mirrors the helper in the original app.
func generateThreeGradeIDsFromSingleDigit(startDigit int) []string {
	result := make([]string, 0, 3)
	d1 := (startDigit + 3) % 10
	d2 := (startDigit + 6) % 10
	d3 := (startDigit + 9) % 10
	result = append(result, strings.Join([]string{
		string('0' + startDigit),
		string('0' + d1),
		string('0' + d2),
		string('0' + d3),
	}, ""))

	g2 := (startDigit + 1) % 10
	d4 := (g2 + 3) % 10
	d5 := (g2 + 6) % 10
	result = append(result, strings.Join([]string{
		string('0' + g2),
		string('0' + d4),
		string('0' + d5),
	}, ""))

	g3 := (startDigit + 2) % 10
	d6 := (g3 + 3) % 10
	d7 := (g3 + 6) % 10
	result = append(result, strings.Join([]string{
		string('0' + g3),
		string('0' + d6),
		string('0' + d7),
	}, ""))

	return result
}
