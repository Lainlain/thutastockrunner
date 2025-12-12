package api

import (
	"bytes"
	"context"
	"encoding/json"
	"fmt"
	"io"
	"log"
	"net/http"
	"time"

	"thaistockanalysis/internal/config"
	"thaistockanalysis/internal/model"
)

// Client handles API communication
type Client struct {
	httpClient *http.Client
	config     *config.Config
}

// NewClient creates a new API client
func NewClient(cfg *config.Config) *Client {
	return &Client{
		httpClient: &http.Client{
			Timeout: cfg.APITimeout,
		},
		config: cfg,
	}
}

// PostStockAnalysis sends stock analysis data to the API
func (c *Client) PostStockAnalysis(ctx context.Context, data model.StockAnalysisRequest) error {
	url := c.config.GetFullURL()

	// Marshal request to JSON
	jsonData, err := json.Marshal(data)
	if err != nil {
		return fmt.Errorf("failed to marshal request: %w", err)
	}

	log.Printf("📤 Posting to %s: %s", url, string(jsonData))

	// Attempt with retries
	var lastErr error
	for attempt := 0; attempt <= c.config.MaxRetries; attempt++ {
		if attempt > 0 {
			log.Printf("🔄 Retry attempt %d/%d", attempt, c.config.MaxRetries)
			time.Sleep(c.config.RetryDelay * time.Duration(attempt))
		}

		// Create request
		req, err := http.NewRequestWithContext(ctx, "POST", url, bytes.NewBuffer(jsonData))
		if err != nil {
			lastErr = fmt.Errorf("failed to create request: %w", err)
			continue
		}

		req.Header.Set("Content-Type", "application/json")

		// Send request
		resp, err := c.httpClient.Do(req)
		if err != nil {
			lastErr = fmt.Errorf("request failed: %w", err)
			log.Printf("❌ Request error: %v", err)
			continue
		}

		// Read response
		body, _ := io.ReadAll(resp.Body)
		resp.Body.Close()

		// Check status code
		if resp.StatusCode >= 200 && resp.StatusCode < 300 {
			log.Printf("✅ Successfully posted stock analysis (HTTP %d)", resp.StatusCode)
			log.Printf("📥 Response: %s", string(body))
			return nil
		}

		lastErr = fmt.Errorf("API returned error (HTTP %d): %s", resp.StatusCode, string(body))
		log.Printf("❌ API error: %v", lastErr)

		// Don't retry on 4xx errors (except 429 Too Many Requests)
		if resp.StatusCode >= 400 && resp.StatusCode < 500 && resp.StatusCode != 429 {
			return lastErr
		}
	}

	// All retries failed
	log.Printf("❌ All retry attempts exhausted")
	return fmt.Errorf("failed after %d retries: %w", c.config.MaxRetries, lastErr)
}

// LogFailedPost logs a failed post for manual review
func (c *Client) LogFailedPost(data model.StockAnalysisRequest, err error) {
	jsonData, _ := json.MarshalIndent(data, "", "  ")
	log.Printf("❌ FAILED POST - Manual review needed:\n%s\nError: %v", string(jsonData), err)

	// Could also write to a file here for persistence
	// failureLog := fmt.Sprintf("/var/log/stockrunner/failed-%s.json", time.Now().Format("20060102-150405"))
	// os.WriteFile(failureLog, jsonData, 0644)
}
