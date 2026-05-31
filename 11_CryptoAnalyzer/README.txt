# Crypto Investment Analyzer

[![Java](https://img.shields.io/badge/Java-11-blue.svg)](https://java.com)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)
[![API](https://img.shields.io/badge/API-CoinGecko-orange.svg)](https://www.coingecko.com/en/api)

## Overview

A command-line Java application that consumes the **CoinGecko REST API** to fetch real-time cryptocurrency market data and provides automated investment recommendations using a weighted scoring algorithm.

Built for **CSS 503 - Service Oriented Architecture** at UW Bothell.

## Features

- 📊 Real-time price data for 5+ major cryptocurrencies
- 🔥 Trending coins discovery
- 📈 Weighted investment scoring (0-100 points)
- 💡 Buy/Hold/Sell/Watch recommendations with detailed reasoning
- ⏱️ Rate limiting respecting API free tier constraints
- 📄 Auto-saves output to text file

## Scoring Algorithm

| Factor | Weight | Description |
|--------|--------|-------------|
| Momentum | 25% | 24h price change |
| Market Cap | 20% | Larger cap = lower risk |
| ATH Distance | 20% | Distance from all-time high |
| Liquidity | 20% | Volume / market cap ratio |
| Rank | 15% | Market cap position |

**Recommendation Scale:** 80+ STRONG BUY | 60-79 BUY | 40-59 HOLD | 20-39 WATCH | 0-19 SELL

## Tech Stack

- Java 11 (built-in HttpClient)
- Manual JSON parsing (no external libraries)
- CoinGecko Free API (no authentication)

## Quick Start

```bash
# Compile
javac -d . src/main/java/com/crypto/analyzer/*.java

# Run
java -cp . com.crypto.analyzer.CryptoAnalyzer
