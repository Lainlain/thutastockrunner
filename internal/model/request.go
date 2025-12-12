package model

// NumberGroup represents 2D numbers grouped by leading digit
type NumberGroup struct {
	Digit   string `json:"digit"`
	Numbers []int  `json:"numbers"`
}

// StockAnalysisRequest is the payload sent to the API
type StockAnalysisRequest struct {
	Date         string        `json:"date"`          // Format: YYYY-MM-DD
	Session      string        `json:"session"`       // "morning" or "evening"
	OpenSet      string        `json:"open_set"`      // SET index value
	Change       string        `json:"change"`        // Change value with +/- sign
	NumberGroups []NumberGroup `json:"number_groups"` // 2D lottery numbers
}
