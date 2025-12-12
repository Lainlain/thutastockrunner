package config

import (
	"os"
	"time"
)

// Config holds the application configuration
type Config struct {
	// API Configuration
	APIBaseURL  string
	APIEndpoint string
	APITimeout  time.Duration

	// Retry Configuration
	MaxRetries int
	RetryDelay time.Duration

	// Environment
	Environment string // "development" or "production"
}

// Load returns the configuration based on environment variables
func Load() *Config {
	env := getEnv("ENVIRONMENT", "development")

	// Set base URL based on environment
	baseURL := getEnv("API_BASE_URL", "http://localhost:4597")
	if env == "production" {
		baseURL = getEnv("API_BASE_URL", "http://aungthuata.online")
	}

	return &Config{
		APIBaseURL:  baseURL,
		APIEndpoint: "/api/2d/stock-analysis",
		APITimeout:  30 * time.Second,
		MaxRetries:  3,
		RetryDelay:  2 * time.Second,
		Environment: env,
	}
}

// getEnv gets an environment variable or returns a default value
func getEnv(key, defaultValue string) string {
	if value := os.Getenv(key); value != "" {
		return value
	}
	return defaultValue
}

// GetFullURL returns the complete API URL
func (c *Config) GetFullURL() string {
	return c.APIBaseURL + c.APIEndpoint
}
