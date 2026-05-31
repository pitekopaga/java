//----------------------------------------------------------------------
// ScoringEngine.java - Weighted Investment Scoring Algorithm
// Scott Elliott
// Creation Date: 05/31/2026
// Last Modification: 05/31/2026
//----------------------------------------------------------------------
// Description:
//   Implements a multi-factor scoring algorithm that evaluates
//   cryptocurrency investment potential. Scores range from 0-100
//   based on momentum, market cap, ATH distance, liquidity, and rank.
//----------------------------------------------------------------------
// Scoring Factors:
//   1. Momentum (25 pts) - Positive 24h price change
//   2. Market Cap (20 pts) - Larger cap = lower risk
//   3. ATH Distance (20 pts) - Distance from all-time high (value opp)
//   4. Liquidity (20 pts) - Volume to market cap ratio
//   5. Rank (15 pts) - Market cap rank (Top 10 = more trusted)
//----------------------------------------------------------------------

package com.crypto.analyzer;

/**
 * Scoring engine that evaluates cryptocurrency investment potential.
 * Uses weighted factors to produce a score from 0 to 100.
 */
public final class ScoringEngine {

    // Private constructor to prevent instantiation
    private ScoringEngine() {}

    /**
     * Analyzes market data and generates investment advice.
     *
     * @param data MarketData object containing coin metrics
     * @return InvestmentAdvice with recommendation and breakdown
     */
    public static InvestmentAdvice analyze(MarketData data) {
        InvestmentAdvice.Builder builder =
                new InvestmentAdvice.Builder();
        int totalScore = 0;

        // Factor 1: Momentum (24h price change) - 25 points max
        int momentumScore = calculateMomentumScore(
                data.getPriceChange24h());
        totalScore += momentumScore;
        builder.addFactorScore("Momentum", momentumScore);

        if (data.getPriceChange24h() > 5.0) {
            builder.addReason("Strong positive momentum (+"
                    + String.format("%.1f", data.getPriceChange24h()) + "%)");
        } else if (data.getPriceChange24h() < -5.0) {
            builder.addReason("Negative momentum ("
                    + String.format("%.1f", data.getPriceChange24h()) + "%)");
        }

        // Factor 2: Market Cap Stability - 20 points max
        int marketCapScore = calculateMarketCapScore(
                data.getMarketCap());
        totalScore += marketCapScore;
        builder.addFactorScore("Market Cap", marketCapScore);

        if (data.getMarketCap() > 10_000_000_000.0) {
            builder.addReason(
                    "Large cap coin (>$10B) - lower risk");
        }

        // Factor 3: ATH Distance (Value opportunity) - 20 points max
        int athDistanceScore = calculateAthDistanceScore(
                data.getAthDistance());
        totalScore += athDistanceScore;
        builder.addFactorScore("ATH Distance", athDistanceScore);

        if (data.getAthDistance() > 50.0) {
            builder.addReason("Significant discount from ATH (-"
                    + String.format("%.0f", data.getAthDistance()) + "%)");
        }

        // Factor 4: Liquidity (Volume/MCap ratio) - 20 points max
        int liquidityScore = calculateLiquidityScore(
                data.getVolumeToMcRatio());
        totalScore += liquidityScore;
        builder.addFactorScore("Liquidity", liquidityScore);

        if (data.getVolumeToMcRatio() > 0.1) {
            builder.addReason(
                    "High liquidity - easy entry/exit");
        }

        // Factor 5: Trust (Market Cap Rank) - 15 points max
        int rankScore = calculateRankScore(data.getMarketCapRank());
        totalScore += rankScore;
        builder.addFactorScore("Rank", rankScore);

        if (data.getMarketCapRank() <= 10) {
            builder.addReason(
                    "Top 10 coin - established project");
        } else if (data.getMarketCapRank() <= 50) {
            builder.addReason(
                    "Top 50 coin - good liquidity and recognition");
        }

        // Determine action based on score
        InvestmentAdvice.Action action =
                InvestmentAdvice.Action.fromScore(totalScore);
        builder.action(action);
        builder.totalScore(totalScore);
        addRecommendationReason(builder, action, totalScore);

        // Calculate risk level based on volatility
        double volatility = Math.abs(data.getPriceChange24h());
        InvestmentAdvice.RiskLevel riskLevel =
                InvestmentAdvice.RiskLevel.fromVolatility(volatility);
        builder.riskLevel(riskLevel);

        return builder.build();
    }

    /**
     * Calculates momentum score from 24h price change percentage.
     *
     * @param priceChangePercent Percentage change in last 24 hours
     * @return Score from 0-25 points
     */
    private static int calculateMomentumScore(double priceChangePercent) {
        if (priceChangePercent >= 20.0) return 25;
        if (priceChangePercent >= 10.0) return 20;
        if (priceChangePercent >= 5.0)  return 15;
        if (priceChangePercent >= 0.0)  return 10;
        if (priceChangePercent >= -5.0) return 5;
        if (priceChangePercent >= -10.0) return 2;
        return 0;
    }

    /**
     * Calculates market cap score based on total market capitalization.
     *
     * @param marketCap Total market cap in USD
     * @return Score from 0-20 points
     */
    private static int calculateMarketCapScore(double marketCap) {
        if (marketCap >= 100_000_000_000.0) return 20;  // $100B+
        if (marketCap >= 50_000_000_000.0)  return 18;  // $50B+
        if (marketCap >= 10_000_000_000.0)  return 15;  // $10B+
        if (marketCap >= 1_000_000_000.0)   return 10;  // $1B+
        if (marketCap >= 100_000_000.0)     return 5;   // $100M+
        return 2;
    }

    /**
     * Calculates ATH distance score - higher discount yields more points.
     *
     * @param athDistancePercent Percentage down from all-time high
     * @return Score from 0-20 points
     */
    private static int calculateAthDistanceScore(double athDistancePercent) {
        if (athDistancePercent >= 70.0) return 20;
        if (athDistancePercent >= 50.0) return 18;
        if (athDistancePercent >= 30.0) return 15;
        if (athDistancePercent >= 20.0) return 12;
        if (athDistancePercent >= 10.0) return 8;
        if (athDistancePercent >= 0.0)  return 5;
        return 0;
    }

    /**
     * Calculates liquidity score from volume-to-market-cap ratio.
     *
     * @param volumeToMcRatio Trading volume divided by market cap
     * @return Score from 0-20 points
     */
    private static int calculateLiquidityScore(double volumeToMcRatio) {
        if (volumeToMcRatio >= 0.30) return 20;
        if (volumeToMcRatio >= 0.15) return 15;
        if (volumeToMcRatio >= 0.10) return 12;
        if (volumeToMcRatio >= 0.05) return 8;
        if (volumeToMcRatio >= 0.01) return 4;
        return 1;
    }

    /**
     * Calculates rank score - higher rank (lower number) yields more points.
     *
     * @param rank Market cap rank (1 = largest)
     * @return Score from 0-15 points
     */
    private static int calculateRankScore(int rank) {
        if (rank <= 10)   return 15;
        if (rank <= 25)   return 13;
        if (rank <= 50)   return 11;
        if (rank <= 100)  return 8;
        if (rank <= 250)  return 5;
        if (rank <= 500)  return 3;
        return 1;
    }

    /**
     * Adds final recommendation reason based on action.
     *
     * @param builder InvestmentAdvice.Builder to modify
     * @param action  Final recommendation action
     * @param score   Total score achieved
     */
    private static void addRecommendationReason(
            InvestmentAdvice.Builder builder,
            InvestmentAdvice.Action action,
            int score) {
        switch (action) {
            case STRONG_BUY:
                builder.addReason(
                        "EXCELLENT entry point based on multiple factors");
                builder.addReason(
                        "Strong fundamentals and favorable metrics");
                break;
            case BUY:
                builder.addReason(
                        "Good accumulation zone with positive indicators");
                break;
            case HOLD:
                builder.addReason(
                        "Fair valuation - consider DCA strategy");
                break;
            case WATCH:
                builder.addReason(
                        "Wait for better entry point or confirmation");
                break;
            case SELL:
                builder.addReason(
                        "Consider taking profits or reallocating");
                break;
            default:
                break;
        }
    }
}