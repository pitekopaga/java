//----------------------------------------------------------------------
// CryptoAnalyzer.java - Main Application Entry Point
// Scott Elliott
// Creation Date: 05/31/2026
// Last Modification: 05/31/2026
//----------------------------------------------------------------------
// Description:
//   Main driver class for the Crypto Investment Analyzer application.
//   Orchestrates API calls, JSON parsing, scoring, and output display.
//   Demonstrates REST API consumption with rate limiting and manual
//   JSON parsing.
//----------------------------------------------------------------------
// Compilation Instructions:
//   javac -d . src/com/crypto/analyzer/*.java
//   java -cp . com.crypto.analyzer.CryptoAnalyzer
//
// Example Run:
//   java -cp . com.crypto.analyzer.CryptoAnalyzer
//   > Analyze default coins? (y/n): y
//----------------------------------------------------------------------

package com.crypto.analyzer;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

/**
 * Main application class for the Crypto Investment Analyzer.
 * Coordinates API requests, data parsing, scoring, and output.
 */
public final class CryptoAnalyzer {

    private static final ApiClient API_CLIENT = new ApiClient();
    private static final StringBuilder OUTPUT_LOG = new StringBuilder();

    // Private constructor to prevent instantiation
    private CryptoAnalyzer() {}

    /**
     * Application entry point.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        printBanner();
        log("\n=== Crypto Investment Analyzer ===");
        log("Started: " + LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        String[] defaultCoins = {
                "bitcoin", "ethereum", "binancecoin", "cardano", "solana"
        };

        Scanner scanner = new Scanner(System.in);
        System.out.print("\nAnalyze default coins? (y/n): ");
        String choice = scanner.nextLine().trim().toLowerCase();

        if (choice.equals("y") || choice.equals("yes")
                || choice.isEmpty()) {
            analyzeCoins(defaultCoins);
        } else {
            System.out.print(
                    "Enter coin IDs (comma-separated): ");
            String input = scanner.nextLine();
            String[] coins = input.split(",");
            for (int i = 0; i < coins.length; i++) {
                coins[i] = coins[i].trim().toLowerCase();
            }
            analyzeCoins(coins);
        }

        showTrendingCoins();
        saveOutputToFile();

        log("\n=== Analysis Complete ===");
        System.out.println(
                "\nAnalysis complete! Output saved to "
                        + "crypto_analysis_output.txt");
    }

    /**
     * Analyzes a list of cryptocurrencies.
     *
     * @param coinIds Array of coin identifiers to analyze
     */
    private static void analyzeCoins(String[] coinIds) {
        log("\n--- Analyzing Coins ---\n");

        for (String coinId : coinIds) {
            try {
                log("\n" + "=".repeat(60));
                log("Analyzing: " + coinId.toUpperCase());
                log("=".repeat(60));

                System.out.printf("Fetching data for %s... ", coinId);
                String jsonResponse = API_CLIENT.getCoinData(coinId);
                System.out.println("✓");

                MarketData data = JsonParser.parseCoinData(
                        jsonResponse, coinId);
                displayMarketData(data);

                InvestmentAdvice advice = ScoringEngine.analyze(data);
                log(advice.toString());

                Thread.sleep(500L);

            } catch (Exception e) {
                log("Error analyzing " + coinId + ": "
                        + e.getMessage());
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    /**
     * Displays formatted market data for a coin.
     *
     * @param data MarketData object containing coin information
     */
    private static void displayMarketData(MarketData data) {
        log("\nMARKET DATA:");
        log(String.format("  Name: %s (%s)",
                data.getName(), data.getSymbol().toUpperCase()));
        log(String.format("  Rank: #%d", data.getMarketCapRank()));
        log(String.format("  Current Price: $%.2f",
                data.getCurrentPrice()));

        String changeSymbol = data.getPriceChange24h() >= 0.0 ? "+" : "-";
        log(String.format("  24h Change: %s%.2f%%",
                changeSymbol, Math.abs(data.getPriceChange24h())));

        log(String.format("  Market Cap: $%.2f Billion",
                data.getMarketCap() / 1_000_000_000.0));
        log(String.format("  24h Volume: $%.2f Billion",
                data.getTotalVolume() / 1_000_000_000.0));
        log(String.format("  Volume/MCap Ratio: %.2f%%",
                data.getVolumeToMcRatio() * 100.0));
        log(String.format("  Distance from ATH: %.1f%%",
                data.getAthDistance()));
    }

    /**
     * Fetches and displays trending coins from CoinGecko.
     * Offers to analyze trending coins if user agrees.
     */
    private static void showTrendingCoins() {
        log("\n--- Trending Coins ---\n");

        try {
            String trendingJson = API_CLIENT.getTrending();
            List<String> trendingCoins =
                    JsonParser.parseTrendingCoins(trendingJson);

            if (!trendingCoins.isEmpty()) {
                log("Top trending coins right now:");
                for (int i = 0; i < trendingCoins.size(); i++) {
                    log(String.format("  %d. %s",
                            i + 1, trendingCoins.get(i)));
                }

                System.out.print("\nAnalyze trending coins? (y/n): ");
                Scanner scanner = new Scanner(System.in);
                if (scanner.nextLine().trim().toLowerCase()
                        .equals("y")) {
                    analyzeCoins(trendingCoins.toArray(
                            new String[0]));
                }
            }
        } catch (Exception e) {
            log("Could not fetch trending coins: "
                    + e.getMessage());
        }
    }

    /**
     * Appends a message to both console and output log.
     *
     * @param message Message to log
     */
    private static void log(String message) {
        OUTPUT_LOG.append(message).append("\n");
        System.out.println(message);
    }

    /**
     * Saves the accumulated log to a text file.
     */
    private static void saveOutputToFile() {
        try (PrintWriter writer = new PrintWriter(
                new FileWriter("crypto_analysis_output.txt"))) {
            writer.print(OUTPUT_LOG.toString());
        } catch (Exception e) {
            System.err.println(
                    "Warning: Could not save output to file: "
                            + e.getMessage());
        }
    }

    /**
     * Prints the application banner at startup.
     */
    private static void printBanner() {
        String banner =
                "\n=========================================\n"
                        + "  CRYPTO INVESTMENT ANALYZER            \n"
                        + "  REST API Client                       \n"
                        + "=========================================\n"
                        + "  Powered by CoinGecko API              \n"
                        + "  Demonstrates SOA & REST Principles    \n"
                        + "=========================================\n";
        System.out.println(banner);
    }
}