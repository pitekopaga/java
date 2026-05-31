//----------------------------------------------------------------------
// InvestmentAdvice.java - Investment Recommendation Engine Output
// Scott Elliott
// Creation Date: 05/31/2026
// Last Modification: 05/31/2026
//----------------------------------------------------------------------
// Description:
//   Defines the output structure for investment recommendations
//   including Action enum (STRONG_BUY/BUY/HOLD/WATCH/SELL),
//   RiskLevel enum, and formatted output generation.
//----------------------------------------------------------------------

package com.crypto.analyzer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Container for investment recommendation results including action,
 * risk level, total score, factor breakdown, and supporting reasons.
 */
public final class InvestmentAdvice {

    /**
     * Investment action recommendation based on total score.
     * Maps score ranges (0-100) to specific actions.
     */
    public enum Action {
        STRONG_BUY("STRONG BUY", 80, 100),
        BUY("BUY", 60, 79),
        HOLD("HOLD", 40, 59),
        WATCH("WATCH", 20, 39),
        SELL("SELL", 0, 19);

        private final String displayName;
        private final int minScore;
        private final int maxScore;

        Action(String displayName, int minScore, int maxScore) {
            this.displayName = displayName;
            this.minScore = minScore;
            this.maxScore = maxScore;
        }

        public String getDisplayName() { return displayName; }
        public int getMinScore() { return minScore; }
        public int getMaxScore() { return maxScore; }

        /**
         * Determines the appropriate action from a numerical score.
         *
         * @param score Total investment score (0-100)
         * @return Corresponding Action enum value
         */
        public static Action fromScore(int score) {
            for (Action action : values()) {
                if (score >= action.minScore && score <= action.maxScore) {
                    return action;
                }
            }
            return WATCH;
        }
    }

    /**
     * Risk level assessment based on price volatility.
     * Higher volatility indicates higher risk.
     */
    public enum RiskLevel {
        LOW("Low", 0, 30),
        MEDIUM("Medium", 31, 60),
        HIGH("High", 61, 100);

        private final String displayName;
        private final int min;
        private final int max;

        RiskLevel(String displayName, int min, int max) {
            this.displayName = displayName;
            this.min = min;
            this.max = max;
        }

        public String getDisplayName() { return displayName; }

        /**
         * Determines risk level from volatility percentage.
         *
         * @param volatilityPercent Absolute price change percentage
         * @return Corresponding RiskLevel enum value
         */
        public static RiskLevel fromVolatility(double volatilityPercent) {
            if (volatilityPercent <= 30.0) return LOW;
            if (volatilityPercent <= 60.0) return MEDIUM;
            return HIGH;
        }
    }

    private final Action action;
    private final RiskLevel riskLevel;
    private final int totalScore;
    private final List<String> reasons;
    private final Map<String, Integer> factorScores;

    private InvestmentAdvice(Builder builder) {
        this.action = builder.action;
        this.riskLevel = builder.riskLevel;
        this.totalScore = builder.totalScore;
        this.reasons = builder.reasons;
        this.factorScores = builder.factorScores;
    }

    public Action getAction() { return action; }
    public RiskLevel getRiskLevel() { return riskLevel; }
    public int getTotalScore() { return totalScore; }
    public List<String> getReasons() { return reasons; }
    public Map<String, Integer> getFactorScores() { return factorScores; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("=========================================\n");
        sb.append("          INVESTMENT ANALYSIS            \n");
        sb.append("=========================================\n\n");

        sb.append(String.format("  Action:       %s\n",
                action.getDisplayName()));
        sb.append(String.format("  Score:        %d/100\n", totalScore));
        sb.append(String.format("  Risk Level:   %s\n\n",
                riskLevel.getDisplayName()));

        sb.append("  --- Factor Breakdown ---\n");
        for (Map.Entry<String, Integer> entry :
                factorScores.entrySet()) {
            sb.append(String.format("  %-18s %3d points\n",
                    entry.getKey() + ":", entry.getValue()));
        }

        sb.append("\n  --- Key Reasons ---\n");
        for (String reason : reasons) {
            sb.append(String.format("  * %s\n", reason));
        }

        sb.append("\n=========================================\n");
        return sb.toString();
    }

    /**
     * Builder pattern for constructing InvestmentAdvice objects.
     * Allows step-by-step construction with method chaining.
     */
    public static class Builder {
        private Action action;
        private RiskLevel riskLevel;
        private int totalScore;
        private List<String> reasons = new ArrayList<>();
        private Map<String, Integer> factorScores = new HashMap<>();

        public Builder action(Action action) {
            this.action = action;
            return this;
        }

        public Builder riskLevel(RiskLevel riskLevel) {
            this.riskLevel = riskLevel;
            return this;
        }

        public Builder totalScore(int totalScore) {
            this.totalScore = totalScore;
            return this;
        }

        public Builder addReason(String reason) {
            this.reasons.add(reason);
            return this;
        }

        public Builder addFactorScore(String factor, int score) {
            this.factorScores.put(factor, score);
            return this;
        }

        /**
         * Builds the final InvestmentAdvice object.
         * @return Immutable InvestmentAdvice instance
         */
        public InvestmentAdvice build() {
            return new InvestmentAdvice(this);
        }
    }
}