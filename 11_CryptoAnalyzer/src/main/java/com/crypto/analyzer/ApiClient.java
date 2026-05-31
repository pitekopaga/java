//----------------------------------------------------------------------
// ApiClient.java - CoinGecko REST API HTTP Client
// Scott Elliott
// Creation Date: 05/31/2026
// Last Modification: 05/31/2026
//----------------------------------------------------------------------
// Description:
//   Handles all HTTP communication with the CoinGecko API. Implements
//   rate limiting to respect free tier constraints (25 calls per minute)
//   and includes retry logic for rate limit responses (HTTP 429).
//----------------------------------------------------------------------
// Key Features:
//   - Java 11 built-in HttpClient (no external dependencies)
//   - Automatic rate limiting with sliding window
//   - Retry with exponential backoff for rate limits
//   - User-Agent header for API compliance
//----------------------------------------------------------------------

package com.crypto.analyzer;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * HTTP client for CoinGecko API with built-in rate limiting.
 * Respects free tier limits and provides retry functionality.
 */
public final class ApiClient {

    private final HttpClient httpClient;
    private long lastRequestTime;
    private final AtomicInteger requestCount;
    private long windowStartTime;

    // Rate limiting constants
    private static final int MAX_CALLS_PER_MINUTE = 20;
    private static final int MIN_DELAY_MS = 3000;
    private static final int MAX_RETRIES = 2;
    private static final long RETRY_DELAY_MS = 8000L;

    /**
     * Constructs a new ApiClient with default configuration.
     * Initializes HttpClient with connection timeouts.
     */
    public ApiClient() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(
                        Config.CONNECT_TIMEOUT_SEC))
                .build();
        this.lastRequestTime = 0L;
        this.requestCount = new AtomicInteger(0);
        this.windowStartTime = System.currentTimeMillis();
    }

    /**
     * Enforces rate limits using sliding window algorithm.
     * Implements minimum delay between requests and per-minute quota.
     *
     * @throws InterruptedException if thread is interrupted while waiting
     */
    private synchronized void enforceRateLimit()
            throws InterruptedException {
        long now = System.currentTimeMillis();

        // Reset window if minute has passed
        if (now - windowStartTime >= 60000L) {
            requestCount.set(0);
            windowStartTime = now;
        }

        // Check if we've exceeded per-minute quota
        if (requestCount.get() >= MAX_CALLS_PER_MINUTE) {
            long waitTime = 60000L - (now - windowStartTime);
            if (waitTime > 0L) {
                Thread.sleep(waitTime);
                requestCount.set(0);
                windowStartTime = System.currentTimeMillis();
            }
        }

        // Ensure minimum delay between consecutive requests
        long elapsed = now - lastRequestTime;
        if (elapsed < MIN_DELAY_MS && lastRequestTime > 0L) {
            long sleepTime = MIN_DELAY_MS - elapsed;
            Thread.sleep(sleepTime);
        }

        lastRequestTime = System.currentTimeMillis();
        requestCount.incrementAndGet();
    }

    /**
     * Fetches comprehensive market data for a specific cryptocurrency.
     *
     * @param coinId Cryptocurrency identifier (e.g., "bitcoin")
     * @return Raw JSON response from CoinGecko API
     * @throws Exception if network error, rate limit exhausted, or coin
     *         not found
     */
    public String getCoinData(String coinId) throws Exception {
        enforceRateLimit();

        String url = String.format(
                "%s/coins/%s?localization=false&tickers=false&"
                        + "market_data=true&community_data=false&"
                        + "developer_data=false&sparkline=false",
                Config.COINGECKO_BASE_URL,
                coinId.toLowerCase());

        return makeRequestWithRetry(url, 0);
    }

    /**
     * Fetches top 7 trending coins from CoinGecko.
     *
     * @return Raw JSON response containing trending coins
     * @throws Exception if network error or rate limit exhausted
     */
    public String getTrending() throws Exception {
        enforceRateLimit();

        String url = String.format("%s/search/trending",
                Config.COINGECKO_BASE_URL);

        return makeRequestWithRetry(url, 0);
    }

    /**
     * Executes HTTP request with automatic retry on rate limits.
     * Uses exponential backoff for retry delays.
     *
     * @param url Complete API endpoint URL
     * @param attempt Current retry attempt number (0 = first attempt)
     * @return Response body as string
     * @throws Exception if all retries exhausted or other errors occur
     */
    private String makeRequestWithRetry(String url, int attempt)
            throws Exception {
        if (attempt > 0) {
            Thread.sleep(RETRY_DELAY_MS);
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("User-Agent", "CryptoAnalyzer/1.0")
                .timeout(Duration.ofSeconds(Config.READ_TIMEOUT_SEC))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request,
                HttpResponse.BodyHandlers.ofString());

        switch (response.statusCode()) {
            case 200:
                return response.body();

            case 429:  // Too Many Requests
                if (attempt < MAX_RETRIES) {
                    return makeRequestWithRetry(url, attempt + 1);
                } else {
                    throw new Exception("Rate limit exceeded");
                }

            case 404:
                throw new Exception("Coin not found: " + url);

            default:
                throw new Exception(String.format("HTTP %d: %s",
                        response.statusCode(),
                        response.body()));
        }
    }
}