# Stock Runner Integration Analysis

## 📋 Current Situation

### Stock Runner (Source Project)
- **Purpose:** Scrapes Thai stock market data from lottosociety.com
- **Current Endpoint:** `localhost:7777`
  - `/api/market-data-analysis` (for open sessions)
  - `/api/market-data-close` (for close sessions)
- **Schedule:** 4 times daily (Yangon timezone)
  - Morning Open: 09:40:00 - 09:40:03
  - Morning Close: 12:05:00 - 12:05:03
  - Afternoon Open: 14:00:00 - 14:00:03
  - Afternoon Close: 16:15:00 - 16:15:03
- **Data Structure:**
```json
{
  "date": "2025-11-06",
  "morning_open": {
    "index": 1305.23,
    "change": 2.56,
    "highlights": "<strong>Banking sector +4.2%</strong>"
  }
}
```

### This Project (2D Thu Ta)
- **Endpoint:** `localhost:4597` (production: `aungthuata.online`)
  - `/api/2d/stock-analysis`
- **Schedule:** Need 2 times daily
  - Morning session
  - Evening session
- **Data Structure:**
```json
{
  "date": "2025-12-10",
  "session": "morning",
  "open_set": "1305.23",
  "change": "+2.56",
  "number_groups": [
    {
      "digit": "1",
      "numbers": [12, 14, 15, 16, 17, 18]
    }
  ]
}
```

## 🔄 Required Changes

### 1. API Configuration Changes
**Files to modify:**
- Create new config file or environment variables for:
  - Base URL: `http://localhost:4597` (dev) or `http://aungthuata.online` (prod)
  - Endpoint: `/api/2d/stock-analysis`
  - API timeout settings

### 2. Data Model Transformation
**Mapping:**
```
stockrunner          →    2D Thu Ta
─────────────────────────────────────
morning_open.index   →    open_set
morning_open.change  →    change
morning_close        →    (combine with open)
afternoon_open       →    open_set
afternoon_close      →    (combine with open)
highlights           →    (discard or process)
```

### 3. Session Consolidation
**Current:** 4 scrapes (morning_open, morning_close, afternoon_open, afternoon_close)
**Target:** 2 sessions (morning, evening)

**Strategy:**
- **Morning Session:** Wait for morning_close, then post combined morning data
- **Evening Session:** Wait for afternoon_close, then post combined afternoon data

### 4. Number Groups Generation
**Options:**

**Option A: Derive from SET Index**
```
SET Index: 1305.23
→ Extract digits: 1, 3, 0, 5, 2, 3
→ Group by leading digit:
   "1": [13, 15]
   "2": [23]
   "3": [30, 32]
   "5": [52, 53]
```

**Option B: Leave Empty Initially**
```json
"number_groups": []
```

**Option C: Use legacy Calculate logic** (mentioned in README)

### 5. Schedule Adjustment
**Old (4 windows):**
```
09:40:00 - morning open
12:05:00 - morning close
14:00:00 - afternoon open
16:15:00 - afternoon close
```

**New (2 windows):**
```
12:05:03 - Post morning session (after close)
16:15:03 - Post evening session (after close)
```

## 📝 Implementation Plan

### Phase 1: Configuration (Todo #3)
1. Create `config/config.go` in stockrunner
2. Add environment variables:
   ```go
   API_BASE_URL=http://localhost:4597
   API_ENDPOINT=/api/2d/stock-analysis
   API_TIMEOUT=30s
   ```

### Phase 2: Data Transformation (Todo #4)
1. Create `internal/transformer/` package
2. Implement `TransformToStockAnalysis()` function:
   ```go
   func TransformToStockAnalysis(detail TopicDetail, session string) StockAnalysisRequest {
       return StockAnalysisRequest{
           Date:         detail.Summary.Date.Format("2006-01-02"),
           Session:      session, // "morning" or "evening"
           OpenSet:      detail.Index,
           Change:       detail.Change,
           NumberGroups: []NumberGroup{}, // or calculated
       }
   }
   ```

### Phase 3: HTTP Client (Todo #7)
1. Create `internal/api/client.go`
2. Implement POST method:
   ```go
   func PostStockAnalysis(ctx context.Context, data StockAnalysisRequest) error {
       url := fmt.Sprintf("%s%s", baseURL, endpoint)
       // HTTP POST logic with retry
   }
   ```

### Phase 4: Session Logic (Todo #6)
1. Modify `cmd/scraper/main.go`
2. Accumulate data during each session
3. Post only after close:
   ```go
   morningData := accumulate(morningOpen, morningClose)
   postToAPI(morningData, "morning")
   
   eveningData := accumulate(afternoonOpen, afternoonClose)
   postToAPI(eveningData, "evening")
   ```

### Phase 5: Number Groups (Todo #5)
**Recommended: Start with empty array, add calculation later**
```go
func CalculateNumberGroups(index string) []NumberGroup {
    // TODO: Implement digit extraction and grouping
    // For now, return empty
    return []NumberGroup{}
}
```

### Phase 6: Error Handling (Todo #8)
1. Retry failed POST (3 attempts with exponential backoff)
2. Log failures to file: `/var/log/stockrunner/failed-posts.log`
3. Send alert if all retries fail

### Phase 7: Deployment (Todo #9)
1. Create `stockrunner/build.sh`:
   ```bash
   #!/bin/bash
   cd stockrunner
   go build -o stockrunner ./cmd/scraper
   ```

2. Create systemd service:
   ```ini
   [Unit]
   Description=Stock Analysis Runner
   After=network.target
   
   [Service]
   Type=simple
   User=www-data
   WorkingDirectory=/www/wwwroot/thaimasterserver/stockrunner
   ExecStart=/www/wwwroot/thaimasterserver/stockrunner/stockrunner
   Restart=always
   
   [Install]
   WantedBy=multi-user.target
   ```

3. Update `start-complete-system.sh`:
   ```bash
   # Start stockrunner
   cd stockrunner
   ./stockrunner &
   ```

## 🧪 Testing Strategy

### Unit Tests
```go
func TestTransformToStockAnalysis(t *testing.T) {
    // Test data transformation
}

func TestCalculateNumberGroups(t *testing.T) {
    // Test number group calculation
}
```

### Integration Tests
```bash
# 1. Test API endpoint manually
curl -X POST http://localhost:4597/api/2d/stock-analysis \
  -H "Content-Type: application/json" \
  -d '{
    "date": "2025-12-12",
    "session": "morning",
    "open_set": "1305.23",
    "change": "+2.56",
    "number_groups": []
  }'

# 2. Run stockrunner in test mode
./stockrunner --test

# 3. Verify database entry
sqlite3 thaimaster2d.db "SELECT * FROM stock_analysis WHERE date='2025-12-12';"

# 4. Check Flutter app displays data
```

## 📊 File Structure Changes

```
stockrunner/
├── cmd/
│   └── scraper/
│       └── main.go              # Modified: Add API posting
├── internal/
│   ├── api/
│   │   └── client.go            # NEW: HTTP client for posting
│   ├── config/
│   │   └── config.go            # NEW: Configuration
│   ├── model/
│   │   ├── types.go             # Existing
│   │   └── request.go           # NEW: API request types
│   ├── scraper/
│   │   └── scraper.go           # Existing
│   ├── timeutil/
│   │   └── windows.go           # Modified: 2 sessions instead of 4
│   └── transformer/
│       └── transformer.go       # NEW: Data transformation
├── build.sh                     # NEW: Build script
├── stockrunner.service          # NEW: Systemd service
└── README.md                    # Update with new info
```

## 🎯 Key Decisions Needed

### 1. Number Groups Strategy
**Question:** How to generate `number_groups`?
**Options:**
- A) Calculate from SET index digits ⭐ **Recommended initially**
- B) Leave empty and add UI to manually input
- C) Implement full Calculate logic from legacy

**Recommendation:** Start with empty array, add calculation logic in Phase 2

### 2. Session Timing
**Question:** Post immediately after close or wait a bit?
**Options:**
- A) Post immediately (16:15:03) ⭐ **Recommended**
- B) Wait 1 minute (16:16:00)
- C) Configurable delay

**Recommendation:** Post immediately, add configurable delay if needed

### 3. Data Persistence During Session
**Question:** How to store data between open and close?
**Options:**
- A) In-memory map with session key ⭐ **Recommended**
- B) Temporary file
- C) SQLite local cache

**Recommendation:** In-memory storage with session locking

### 4. Failure Handling
**Question:** What to do if POST fails?
**Options:**
- A) Retry 3 times, then log and continue ⭐ **Recommended**
- B) Retry indefinitely
- C) Save to file for manual retry

**Recommendation:** Limited retry with failure logging

## 📅 Timeline Estimate

- **Phase 1 (Config):** 30 minutes
- **Phase 2 (Transform):** 1 hour
- **Phase 3 (HTTP Client):** 1 hour
- **Phase 4 (Session Logic):** 1.5 hours
- **Phase 5 (Number Groups):** 2 hours (if calculating) or 15 min (if empty)
- **Phase 6 (Error Handling):** 1 hour
- **Phase 7 (Deployment):** 30 minutes
- **Testing:** 1 hour

**Total:** ~8 hours (or ~4.5 hours with empty number_groups)

## 🚀 Quick Start Recommendation

**For fastest results:**
1. Start with empty `number_groups: []`
2. Focus on getting API integration working
3. Test with manual data first
4. Add number calculation logic later as enhancement

This gets stock data flowing to the app **today**, with room for enhancement later.

---

**Next Step:** Should I proceed with implementing Phase 1 (Configuration)?
