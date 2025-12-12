package timeutil

import (
	"errors"
	"strings"
	"time"

	"thaistockanalysis/internal/model"
)

var (
	yangonLoc = mustLoadLocation("Asia/Yangon")
)

// Location exposes the configured timezone for scheduling.
func Location() *time.Location {
	return yangonLoc
}

type sessionWindow struct {
	session  model.Session
	startSec int
	endSec   int
}

var sessionWindows = []sessionWindow{
	{session: model.Session{Slot: model.SlotMorning, Phase: model.PhaseOpen}, startSec: clockToSeconds(9, 40, 0), endSec: clockToSeconds(9, 40, 3)},
	{session: model.Session{Slot: model.SlotMorning, Phase: model.PhaseClose}, startSec: clockToSeconds(12, 5, 0), endSec: clockToSeconds(12, 5, 3)},
	{session: model.Session{Slot: model.SlotAfternoon, Phase: model.PhaseOpen}, startSec: clockToSeconds(14, 0, 0), endSec: clockToSeconds(14, 0, 3)},
	{session: model.Session{Slot: model.SlotAfternoon, Phase: model.PhaseClose}, startSec: clockToSeconds(16, 15, 0), endSec: clockToSeconds(16, 15, 3)},
}

// CurrentSession returns the session that matches the provided instant.
// The second return value indicates whether the instant is inside one of the configured windows.
func CurrentSession(now time.Time) (model.Session, bool) {
	if win, ok := WindowAt(now); ok {
		return win.Session, true
	}
	return model.Session{}, false
}

// Window describes a concrete execution window for a session.
type Window struct {
	Session model.Session
	Start   time.Time
	End     time.Time
}

// DailyWindows returns the execution windows for the day of the supplied instant.
func DailyWindows(reference time.Time) []Window {
	local := reference.In(yangonLoc)
	startOfDay := time.Date(local.Year(), local.Month(), local.Day(), 0, 0, 0, 0, yangonLoc)
	windows := make([]Window, len(sessionWindows))
	for i, w := range sessionWindows {
		windows[i] = Window{
			Session: w.session,
			Start:   startOfDay.Add(time.Duration(w.startSec) * time.Second),
			End:     startOfDay.Add(time.Duration(w.endSec) * time.Second),
		}
	}
	return windows
}

// WindowAt returns the window that contains the provided instant, if any.
func WindowAt(now time.Time) (Window, bool) {
	for _, win := range DailyWindows(now) {
		if !now.Before(win.Start) && !now.After(win.End) {
			return win, true
		}
	}
	return Window{}, false
}

// NextWindow returns the next window (today or tomorrow) after the provided instant.
func NextWindow(now time.Time) (Window, bool) {
	local := now.In(yangonLoc)
	for _, win := range DailyWindows(local) {
		if local.Before(win.End) {
			return win, true
		}
	}
	// If all windows today have passed, return the first window of tomorrow.
	tomorrow := local.Add(24 * time.Hour)
	windowsTomorrow := DailyWindows(tomorrow)
	if len(windowsTomorrow) == 0 {
		return Window{}, false
	}
	return windowsTomorrow[0], true
}

func secondsSinceMidnight(t time.Time) int {
	return t.Hour()*3600 + t.Minute()*60 + t.Second()
}

func clockToSeconds(hour, min, sec int) int {
	return hour*3600 + min*60 + sec
}

func mustLoadLocation(name string) *time.Location {
	loc, err := time.LoadLocation(name)
	if err != nil {
		panic(err)
	}
	return loc
}

var thaiMonths = map[string]time.Month{
	"มกราคม":     time.January,
	"กุมภาพันธ์": time.February,
	"มีนาคม":     time.March,
	"เมษายน":     time.April,
	"พฤษภาคม":    time.May,
	"มิถุนายน":   time.June,
	"กรกฎาคม":    time.July,
	"สิงหาคม":    time.August,
	"กันยายน":    time.September,
	"ตุลาคม":     time.October,
	"พฤศจิกายน":  time.November,
	"ธันวาคม":    time.December,
}

// ParseThaiDate converts a string like "7 พฤศจิกายน 2568" to a time.Time in Yangon.
func ParseThaiDate(s string) (time.Time, error) {
	s = strings.TrimSpace(strings.ReplaceAll(s, "\u00a0", " "))
	s = strings.ReplaceAll(s, "กรกฏาคม", "กรกฎาคม")
	s = strings.ReplaceAll(s, "พฤศจกายน", "พฤศจิกายน")
	s = strings.ReplaceAll(s, "เมษายนยน", "เมษายน")

	parts := strings.Fields(s)
	if len(parts) < 3 {
		return time.Time{}, errors.New("thai date: insufficient parts")
	}

	day, err := parseInt(parts[0])
	if err != nil {
		return time.Time{}, err
	}

	month, ok := thaiMonths[parts[1]]
	if !ok {
		return time.Time{}, errors.New("thai date: unknown month")
	}

	year, err := parseInt(parts[2])
	if err != nil {
		return time.Time{}, err
	}

	gregYear := year - 543

	return time.Date(gregYear, month, day, 0, 0, 0, 0, yangonLoc), nil
}

func parseInt(txt string) (int, error) {
	var v int
	for _, r := range txt {
		if r < '0' || r > '9' {
			return 0, errors.New("thai date: invalid digit")
		}
		v = v*10 + int(r-'0')
	}
	return v, nil
}
