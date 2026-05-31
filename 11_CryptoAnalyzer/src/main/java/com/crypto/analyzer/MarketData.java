//----------------------------------------------------------------------
// MarketData.java - Cryptocurrency Market Data Model
// Scott Elliott
// Creation Date: 05/31/2026
// Last Modification: 05/31/2026
//----------------------------------------------------------------------
// Description:
//   POJO (Plain Old Java Object) representing cryptocurrency market
//   data retrieved from the CoinGecko API. Contains fields for price,
//   volume, market cap, rank, and derived metrics like liquidity ratio.
//----------------------------------------------------------------------

package com.crypto.analyzer;

/**
 * Data model for cryptocurrency market information.
 * Stores raw API data and provides calculated derived metrics.
 */
public final class MarketData {

    // Basic identification fields
    private String id;
    private String symbol;
    private String name;
    private int marketCapRank;

    // Market metrics
    private double currentPrice;
    private double priceChange24h;
    private double marketCap;
    private double totalVolume;
    private double ath;  // All-time high price
    private double atl;  // All-time low price

    // Calculated derived metrics
    private double volumeToMcRatio;
    private double athDistance;

    /**
     * Default constructor - creates empty MarketData object.
     * Use setters to populate fields after parsing.
     */
    public MarketData() {}

    // ==================== GETTERS AND SETTERS ====================

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getMarketCapRank() { return marketCapRank; }
    public void setMarketCapRank(int marketCapRank) {
        this.marketCapRank = marketCapRank;
    }

    public double getCurrentPrice() { return currentPrice; }
    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public double getPriceChange24h() { return priceChange24h; }
    public void setPriceChange24h(double priceChange24h) {
        this.priceChange24h = priceChange24h;
    }

    public double getMarketCap() { return marketCap; }
    public void setMarketCap(double marketCap) {
        this.marketCap = marketCap;
    }

    public double getTotalVolume() { return totalVolume; }
    public void setTotalVolume(double totalVolume) {
        this.totalVolume = totalVolume;
    }

    public double getAth() { return ath; }
    public void setAth(double ath) { this.ath = ath; }

    public double getAtl() { return atl; }
    public void setAtl(double atl) { this.atl = atl; }

    public double getVolumeToMcRatio() { return volumeToMcRatio; }
    public void setVolumeToMcRatio(double volumeToMcRatio) {
        this.volumeToMcRatio = volumeToMcRatio;
    }

    public double getAthDistance() { return athDistance; }
    public void setAthDistance(double athDistance) {
        this.athDistance = athDistance;
    }

    /**
     * Calculates derived metrics from raw market data.
     * Must be called after all raw data fields are populated.
     */
    public void calculateDerivedMetrics() {
        // Volume to Market Cap ratio (liquidity indicator)
        if (marketCap > 0.0) {
            this.volumeToMcRatio = totalVolume / marketCap;
        }

        // Distance from ATH as percentage down from peak
        if (ath > 0.0 && currentPrice > 0.0) {
            this.athDistance = ((ath - currentPrice) / ath) * 100.0;
        }
    }

    @Override
    public String toString() {
        return String.format(
                "MarketData{name='%s', symbol='%s', price=$%.2f, "
                        + "24hChange=%.2f%%, rank=%d}",
                name, symbol, currentPrice, priceChange24h, marketCapRank);
    }
}