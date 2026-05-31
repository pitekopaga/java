//----------------------------------------------------------------------
// JsonParser.java - Manual JSON Parser for CoinGecko API Responses
// Scott Elliott
// Creation Date: 05/31/2026
// Last Modification: 05/31/2026
//----------------------------------------------------------------------
// Description:
//   Parses JSON responses from CoinGecko API using regular expressions.
//   No external JSON libraries are used per assignment requirements.
//   Extracts market data including prices, volumes, and derived metrics.
//----------------------------------------------------------------------
// Note: This parser is specifically designed for CoinGecko's response
//       structure. It is not a general-purpose JSON parser.
//----------------------------------------------------------------------

package com.crypto.analyzer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Manual JSON parser for CoinGecko API responses.
 * Uses regex pattern matching to extract specific fields.
 */
public final class JsonParser {

    // Private constructor to prevent instantiation
    private JsonParser() {}

    /**
     * Parses complete coin data from CoinGecko API response.
     *
     * @param json   Raw JSON string from /coins/{id} endpoint
     * @param coinId Identifier of the coin being parsed
     * @return Populated MarketData object
     */
    public static MarketData parseCoinData(String json, String coinId) {
        MarketData data = new MarketData();
        data.setId(coinId);

        // Extract basic identification fields
        data.setName(extractStringValue(json, "name"));
        data.setSymbol(extractStringValue(json, "symbol"));
        data.setMarketCapRank(
            extractIntValue(json, "market_cap_rank", 999));

        // Extract nested market_data object
        String marketData = extractObject(json, "market_data");
        if (marketData != null) {
            // Extract numeric values in USD
            data.setCurrentPrice(
                extractUsdFromMarketData(marketData, "current_price"));
            data.setPriceChange24h(
                extractDoubleValue(marketData,
                    "price_change_percentage_24h", 0.0));
            data.setMarketCap(
                extractUsdFromMarketData(marketData, "market_cap"));
            data.setTotalVolume(
                extractUsdFromMarketData(marketData, "total_volume"));
            data.setAth(
                extractUsdFromMarketData(marketData, "ath"));
            data.setAtl(
                extractUsdFromMarketData(marketData, "atl"));
        }

        data.calculateDerivedMetrics();
        return data;
    }

    /**
     * Extracts USD value from nested objects like {"usd": 123.45}.
     *
     * @param marketData The market_data JSON object as string
     * @param key        The parent key (e.g., "current_price")
     * @return USD value as double, or 0.0 if not found
     */
    private static double extractUsdFromMarketData(String marketData,
            String key) {
        String patternStr = "\"" + key + "\"\\s*:\\s*\\{";
        Pattern p = Pattern.compile(patternStr);
        Matcher m = p.matcher(marketData);

        if (m.find()) {
            int start = m.end() - 1;  // Position of opening '{'
            int braceCount = 0;
            int end = start;

            // Find matching closing brace
            for (int i = start; i < marketData.length(); i++) {
                char c = marketData.charAt(i);
                if (c == '{') braceCount++;
                if (c == '}') braceCount--;
                if (braceCount == 0) {
                    end = i;
                    break;
                }
            }

            String objectStr = marketData.substring(start, end + 1);
            Pattern usdPattern =
                Pattern.compile("\"usd\"\\s*:\\s*([\\d.]+)");
            Matcher usdMatcher = usdPattern.matcher(objectStr);

            if (usdMatcher.find()) {
                try {
                    return Double.parseDouble(usdMatcher.group(1));
                } catch (NumberFormatException e) {
                    return 0.0;
                }
            }
        }
        return 0.0;
    }

    /**
     * Extracts a string value from JSON by key.
     *
     * @param json Raw JSON string
     * @param key  Key to search for
     * @return String value or "N/A" if not found
     */
    private static String extractStringValue(String json, String key) {
        Pattern pattern = Pattern.compile(
            "\"" + key + "\"\\s*:\\s*\"([^\"]+)\"");
        Matcher matcher = pattern.matcher(json);
        return matcher.find() ? matcher.group(1) : "N/A";
    }

    /**
     * Extracts an integer value from JSON by key.
     *
     * @param json         Raw JSON string
     * @param key          Key to search for
     * @param defaultValue Default value if key not found
     * @return Integer value or defaultValue
     */
    private static int extractIntValue(String json, String key,
            int defaultValue) {
        Pattern pattern = Pattern.compile(
            "\"" + key + "\"\\s*:\\s*(\\d+)");
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
            try {
                return Integer.parseInt(matcher.group(1));
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    /**
     * Extracts a double value from JSON by key.
     *
     * @param json         Raw JSON string
     * @param key          Key to search for
     * @param defaultValue Default value if key not found
     * @return Double value or defaultValue
     */
    private static double extractDoubleValue(String json, String key,
            double defaultValue) {
        Pattern pattern = Pattern.compile(
            "\"" + key + "\"\\s*:\\s*(-?\\d+\\.?\\d*)");
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
            try {
                return Double.parseDouble(matcher.group(1));
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    /**
     * Extracts a JSON object by its key.
     *
     * @param json Raw JSON string
     * @param key  Key of the object to extract
     * @return Object as string or null if not found
     */
    private static String extractObject(String json, String key) {
        String searchFor = "\"" + key + "\"\\s*:\\s*\\{";
        Pattern pattern = Pattern.compile(searchFor);
        Matcher matcher = pattern.matcher(json);

        if (matcher.find()) {
            int startIndex = matcher.start();
            int braceIndex = json.indexOf('{', startIndex);
            int braceCount = 0;

            for (int i = braceIndex; i < json.length(); i++) {
                char c = json.charAt(i);
                if (c == '{') braceCount++;
                if (c == '}') braceCount--;
                if (braceCount == 0) {
                    return json.substring(braceIndex, i + 1);
                }
            }
        }
        return null;
    }

    /**
     * Parses trending coins list from search/trending endpoint.
     *
     * @param json Raw JSON from trending endpoint
     * @return List of trending coin IDs (max 5)
     */
    public static List<String> parseTrendingCoins(String json) {
        List<String> trending = new ArrayList<>();

        int coinsIndex = json.indexOf("\"coins\":");
        if (coinsIndex == -1) return trending;

        int arrayStart = json.indexOf('[', coinsIndex);
        if (arrayStart == -1) return trending;

        int arrayEnd = findMatchingBracket(json, arrayStart);
        if (arrayEnd == -1) return trending;

        String coinsArray = json.substring(arrayStart, arrayEnd + 1);

        // Find each coin's ID within the array
        Pattern pattern = Pattern.compile("\"id\"\\s*:\\s*\"([^\"]+)\"");
        Matcher matcher = pattern.matcher(coinsArray);

        while (matcher.find() && trending.size() < 5) {
            trending.add(matcher.group(1));
        }

        return trending;
    }

    /**
     * Finds the matching closing bracket for an opening bracket.
     *
     * @param json       String to search within
     * @param startIndex Index of the opening bracket
     * @return Index of matching closing bracket, or -1 if not found
     */
    private static int findMatchingBracket(String json, int startIndex) {
        int bracketCount = 0;
        for (int i = startIndex; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '[') bracketCount++;
            if (c == ']') bracketCount--;
            if (bracketCount == 0) {
                return i;
            }
        }
        return -1;
    }
}