# Thai Stock Analysis Scraper (Go)

A Go reimplementation of the LottoSociety scraping logic. The current iteration fetches the board page, selects the active session (morning/afternoon, open/close) using the Yangon timezone, opens the corresponding topic entries, and prints the SET index payload to the terminal. The executable can now run continuously, automatically triggering once inside each configured window, or you can invoke it manually via cron if you prefer.

## Prerequisites

- Go 1.21 or later (module targets 1.25 for future-proofing).
- Outbound HTTPS access to `https://www.lottosociety.com/`.

## Run locally

```bash
cd apirunner
go run ./cmd/scraper
```

The program stays alive, checking the current Yangon time every second. When it enters one of the four 3-second windows it performs a scrape, prints up to five topic snapshots (SET index, change, headline), and logs the next window. Interrupt with `Ctrl+C` to stop. If the board cannot be resolved (e.g. DNS/network restrictions), the active session run logs the failure and the process keeps waiting for the next slot.

### Suggested cron setup (Asia/Yangon)

If you prefer discrete invocations, run the command once for each window. Example crontab (convert to your server local time as needed):

```
# Morning open (09:40:00 - 09:40:03)
40 9 * * * /usr/local/bin/go run /path/to/apirunner/cmd/scraper
# Morning close (12:05:00 - 12:05:03)
5 12 * * * /usr/local/bin/go run /path/to/apirunner/cmd/scraper
# Afternoon open (14:00:00 - 14:00:03)
0 14 * * * /usr/local/bin/go run /path/to/apirunner/cmd/scraper
# Afternoon close (16:15:00 - 16:15:03)
15 16 * * * /usr/local/bin/go run /path/to/apirunner/cmd/scraper
```

## Next steps

- Persist the parsed payload and forward to your API endpoint once ready.
- Port the remainder of the legacy `Calculate` logic (digit combinations, highlights) and surface them alongside the current fields.
- Add unit tests against saved HTML fixtures for deterministic verification.
# thutastockrunner
