#!/bin/bash

# Test scraping lottosociety.com with curl
# This mimics a real browser to bypass Cloudflare protection

echo "🧪 Testing lottosociety.com scraping with curl..."
echo ""

curl -v "https://www.lottosociety.com/index.php?board=4.0" \
  -H "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36" \
  -H "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7" \
  -H "Accept-Language: en-US,en;q=0.9,th;q=0.8" \
  -H "Accept-Encoding: gzip, deflate, br" \
  -H "Cache-Control: max-age=0" \
  -H "Sec-Ch-Ua: \"Not_A Brand\";v=\"8\", \"Chromium\";v=\"120\", \"Google Chrome\";v=\"120\"" \
  -H "Sec-Ch-Ua-Mobile: ?0" \
  -H "Sec-Ch-Ua-Platform: \"Windows\"" \
  -H "Sec-Fetch-Dest: document" \
  -H "Sec-Fetch-Mode: navigate" \
  -H "Sec-Fetch-Site: none" \
  -H "Sec-Fetch-User: ?1" \
  -H "Upgrade-Insecure-Requests: 1" \
  -H "Connection: keep-alive" \
  --compressed \
  -L \
  -o /tmp/lottosociety-test.html

echo ""
echo "---"
echo ""

if [ -f /tmp/lottosociety-test.html ]; then
  FILE_SIZE=$(stat -f%z /tmp/lottosociety-test.html 2>/dev/null || stat -c%s /tmp/lottosociety-test.html 2>/dev/null)
  echo "✅ Downloaded: $FILE_SIZE bytes"
  echo ""
  echo "📄 First 50 lines:"
  head -50 /tmp/lottosociety-test.html
  echo ""
  echo "---"
  echo ""
  echo "🔍 Checking for SET index data..."
  grep -i "SET\|index\|หุ้น" /tmp/lottosociety-test.html | head -10
else
  echo "❌ Download failed"
fi
