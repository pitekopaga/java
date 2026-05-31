//----------------------------------------------------------------------
// Config.java - Configuration Constants for Crypto Analyzer
// Scott Elliott
// Creation Date: 05/31/2026
// Last Modification: 05/31/2026
//----------------------------------------------------------------------
// Description:
//   Contains all configuration constants including API endpoints,
//   rate limiting parameters, scoring thresholds, and timeouts.
//   This centralizes configuration for easy maintenance.
//----------------------------------------------------------------------

package com.crypto.analyzer;

/**
 * Central configuration class containing all constants used by the
 * Crypto Investment Analyzer application.
 */
public final class Config {

    // Private constructor to prevent instantiation
    private Config() {}

    // ==================== API CONFIGURATION ====================

    /** CoinGecko API base URL for all requests */
    public static final String COINGECKO_BASE_URL =
            "https://api.coingecko.com/api/v3";

    /** Target currency for price conversion (US Dollar) */
    public static final String VS_CURRENCY = "usd";

    // ==================== RATE LIMITING ====================

    /** CoinGecko free tier maximum calls per minute */
    public static final int MAX_CALLS_PER_MINUTE = 25;

    /** Minimum milliseconds between successive API calls */
    public static final int MIN_DELAY_MS = 3000;

    /** Maximum number of retries for rate-limited requests */
    public static final int MAX_RETRIES = 2;

    /** Milliseconds to wait before retrying after rate limit */
    public static final long RETRY_DELAY_MS = 8000L;

    // ==================== TIMEOUTS ====================

    /** Connection timeout in seconds */
    public static final int CONNECT_TIMEOUT_SEC = 10;

    /** Read timeout in seconds */
    public static final int READ_TIMEOUT_SEC = 10;

    // ==================== SCORING THRESHOLDS ====================

    /** Minimum score for STRONG BUY recommendation */
    public static final int SCORE_STRONG_BUY = 80;

    /** Minimum score for BUY recommendation */
    public static final int SCORE_BUY = 60;

    /** Minimum score for HOLD recommendation */
    public static final int SCORE_HOLD = 40;

    /** Minimum score for WATCH recommendation */
    public static final int SCORE_WATCH = 20;

    // ==================== SCORING WEIGHTS ====================

    /** Weight for momentum (24h price change) factor */
    public static final double WEIGHT_MOMENTUM = 0.25;

    /** Weight for market cap stability factor */
    public static final double WEIGHT_MARKET_CAP = 0.20;

    /** Weight for ATH distance (value opportunity) factor */
    public static final double WEIGHT_ATH_DISTANCE = 0.20;

    /** Weight for liquidity (volume/mcap ratio) factor */
    public static final double WEIGHT_LIQUIDITY = 0.20;

    /** Weight for rank (trust) factor */
    public static final double WEIGHT_RANK = 0.15;
}