package model

import "time"

// Slot represents the session of the trading day.
type Slot string

const (
	SlotMorning   Slot = "morning"
	SlotAfternoon Slot = "afternoon"
)

// Phase represents whether the market session is open or closed.
type Phase string

const (
	PhaseOpen  Phase = "open"
	PhaseClose Phase = "close"
)

// Session captures the current logical period for scraping logic.
type Session struct {
	Slot  Slot
	Phase Phase
}

func (s Session) String() string {
	return string(s.Slot) + "_" + string(s.Phase)
}

// TopicSummary describes a row scraped from the board page.
type TopicSummary struct {
	Title       string
	URL         string
	TopicID     string
	ThaiDateRaw string
	Date        time.Time
}

// TopicDetail holds the data extracted from an individual topic page.
type TopicDetail struct {
	Summary TopicSummary

	Headline string
	Index    string
	Change   string

	PrimaryDigits   string
	SecondaryDigits string
}
