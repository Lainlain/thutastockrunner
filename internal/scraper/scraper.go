package scraper

import (
	"context"
	"errors"
	"fmt"
	"io"
	"net/http"
	"net/url"
	"regexp"
	"strings"
	"time"

	"github.com/PuerkitoBio/goquery"

	"thaistockanalysis/internal/model"
	"thaistockanalysis/internal/timeutil"
)

const (
	defaultBoardURL = "https://www.lottosociety.com/index.php?board=4.0"
)

// Client wraps the scraping behaviour for the LottoSociety forum.
type Client struct {
	boardURL   string
	httpClient *http.Client
}

// NewClient constructs a scraper with a sane default HTTP client when none is supplied.
func NewClient(boardURL string, httpClient *http.Client) *Client {
	if boardURL == "" {
		boardURL = defaultBoardURL
	}
	if httpClient == nil {
		httpClient = &http.Client{Timeout: 30 * time.Second}
	}
	return &Client{boardURL: boardURL, httpClient: httpClient}
}

// FetchBoard retrieves the list of topic summaries from the board page.
func (c *Client) FetchBoard(ctx context.Context) ([]model.TopicSummary, error) {
	doc, err := c.getDocument(ctx, c.boardURL)
	if err != nil {
		return nil, err
	}

	summaries := make([]model.TopicSummary, 0, 32)
	doc.Find("td.subject").Each(func(_ int, sel *goquery.Selection) {
		link := sel.Find("span a").First()
		if link.Length() == 0 {
			return
		}
		title := strings.TrimSpace(link.Text())
		href, exists := link.Attr("href")
		if !exists {
			return
		}

		thaiDate, _ := extractThaiDate(title)

		topicID := extractTopicID(href)

		summaries = append(summaries, model.TopicSummary{
			Title:       title,
			URL:         href,
			TopicID:     topicID,
			ThaiDateRaw: thaiDate.Raw,
			Date:        thaiDate.Value,
		})
	})

	if len(summaries) == 0 {
		return nil, errors.New("no topics discovered on board page")
	}

	return summaries, nil
}

// FetchTopic parses a specific topic page and extracts the session specific data.
func (c *Client) FetchTopic(ctx context.Context, summary model.TopicSummary, session model.Session) (model.TopicDetail, error) {
	doc, err := c.getDocument(ctx, summary.URL)
	if err != nil {
		return model.TopicDetail{}, err
	}

	areas := doc.Find("div.postarea")
	index := sessionIndex(session)
	if areas.Length() <= index {
		return model.TopicDetail{}, fmt.Errorf("postarea index %d not available (got %d)", index, areas.Length())
	}

	target := areas.Eq(index)

	spans := collectSpanText(target)
	if len(spans) == 0 {
		return model.TopicDetail{}, errors.New("no span text captured in postarea")
	}

	headline := spans[0]
	var indexStr, changeStr string
	if len(spans) >= 3 {
		if idx, chg, ok := splitNumberLine(spans[2]); ok {
			indexStr, changeStr = idx, chg
		}
	}

	detail := model.TopicDetail{
		Summary:  summary,
		Headline: headline,
		Index:    indexStr,
		Change:   changeStr,
	}

	return detail, nil
}

func (c *Client) getDocument(ctx context.Context, target string) (*goquery.Document, error) {
	req, err := http.NewRequestWithContext(ctx, http.MethodGet, target, nil)
	if err != nil {
		return nil, err
	}

	// Add comprehensive browser headers to bypass Cloudflare protection
	req.Header.Set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36")
	req.Header.Set("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
	req.Header.Set("Accept-Language", "en-US,en;q=0.9,th;q=0.8")
	req.Header.Set("Accept-Encoding", "gzip, deflate, br")
	req.Header.Set("Connection", "keep-alive")
	req.Header.Set("Upgrade-Insecure-Requests", "1")
	req.Header.Set("Sec-Fetch-Dest", "document")
	req.Header.Set("Sec-Fetch-Mode", "navigate")
	req.Header.Set("Sec-Fetch-Site", "none")
	req.Header.Set("Sec-Fetch-User", "?1")
	req.Header.Set("Cache-Control", "max-age=0")
	req.Header.Set("sec-ch-ua", `"Google Chrome";v="131", "Chromium";v="131", "Not_A Brand";v="24"`)
	req.Header.Set("sec-ch-ua-mobile", "?0")
	req.Header.Set("sec-ch-ua-platform", `"Windows"`)
	// Set Referer to make it look like navigation from Google
	req.Header.Set("Referer", "https://www.google.com/")

	resp, err := c.httpClient.Do(req)
	if err != nil {
		return nil, err
	}
	defer resp.Body.Close()

	if resp.StatusCode < 200 || resp.StatusCode >= 300 {
		body, _ := io.ReadAll(io.LimitReader(resp.Body, 512))
		return nil, fmt.Errorf("unexpected status %d: %s", resp.StatusCode, strings.TrimSpace(string(body)))
	}

	return goquery.NewDocumentFromReader(resp.Body)
}

func sessionIndex(session model.Session) int {
	switch {
	case session.Slot == model.SlotMorning && session.Phase == model.PhaseOpen:
		return 0
	case session.Slot == model.SlotMorning && session.Phase == model.PhaseClose:
		return 1
	case session.Slot == model.SlotAfternoon && session.Phase == model.PhaseOpen:
		return 2
	default:
		return 3
	}
}

type thaiDate struct {
	Raw   string
	Value time.Time
}

func extractThaiDate(title string) (thaiDate, error) {
	title = strings.ReplaceAll(title, "(ช่อง9)", "")
	title = strings.ReplaceAll(title, "(ช่อง 9)", "")
	title = strings.TrimSpace(title)

	re := regexp.MustCompile(`(\d{1,2}\s+[ก-๙]+\s+\d{4})`)
	match := re.FindStringSubmatch(title)
	if len(match) < 2 {
		return thaiDate{}, errors.New("thai date not found in title")
	}

	dt, err := timeutil.ParseThaiDate(match[1])
	if err != nil {
		return thaiDate{}, err
	}

	return thaiDate{Raw: match[1], Value: dt}, nil
}

func extractTopicID(link string) string {
	u, err := url.Parse(link)
	if err != nil {
		return ""
	}

	return strings.TrimSuffix(u.Query().Get("topic"), ".0")
}

func collectSpanText(node *goquery.Selection) []string {
	texts := make([]string, 0, 16)
	node.Find("span").Each(func(_ int, s *goquery.Selection) {
		t := strings.TrimSpace(strings.ReplaceAll(s.Text(), "\u00a0", " "))
		if t != "" {
			texts = append(texts, t)
		}
	})
	return texts
}

func init() {
	// ensure timezone loaded for deterministic usage
	_ = time.Local
}
