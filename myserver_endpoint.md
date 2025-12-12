
#### Step 1: Morning Opening
```bash
curl -X POST http://localhost:7777/api/market-data-analysis \
  -H "Content-Type: application/json" \
  -d '{
    "date": "2025-11-06",
    "morning_open": {
      "index": 1305.23,
      "change": 2.56,
      "highlights": "<strong>Banking sector +4.2%</strong> <br><br> <strong>Tech stocks +3.1%</strong>"
    }
  }'
```

#### Step 2: Morning Closing
```bash
curl -X POST http://localhost:7777/api/market-data-close \
  -H "Content-Type: application/json" \
  -d '{
    "date": "2025-11-06",
    "morning_close": {
      "index": 1308.45,
      "change": 5.78
    }
  }'
```

#### Step 3: Afternoon Opening
```bash
curl -X POST http://localhost:7777/api/market-data-analysis \
  -H "Content-Type: application/json" \
  -d '{
    "date": "2025-11-06",
    "afternoon_open": {
      "index": 1287.01,
      "change": -4.47,
      "highlights": "<strong>Energy sector -2.8%</strong> <br><br> <strong>Property -1.9%</strong>"
    }
  }'
```

#### Step 4: Afternoon Closing (End of Day)
```bash
curl -X POST http://localhost:7777/api/market-data-close \
  -H "Content-Type: application/json" \
  -d '{
    "date": "2025-11-06",
    "afternoon_close": {
      "index": 1291.23,
      "change": -0.32
    }
  }'
```
