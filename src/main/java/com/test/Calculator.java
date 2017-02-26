package com.test;

import com.test.data.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Created by egor on 24.02.17.
 */
public class Calculator {
    private final static Logger logger = LoggerFactory.getLogger(Calculator.class);
    private final MarketData marketData;
    private final TradeStore tradeStore;


    @Autowired
    public Calculator(MarketData marketData, TradeStore tradeStore) {
        this.marketData = marketData;
        this.tradeStore = tradeStore;
    }

    public Map<String, Double> getDividendYield(List<Price> prices) {
        logger.info("getDividendYield for prices:{}", prices);
        return prices.stream()
                .filter(price -> marketData.getMarketData().get(price.getSymbol()) != null)
                .collect(Collectors.toMap(Price::getSymbol, price -> {
                    ShareInfo shareInfo = marketData.getMarketData().get(price.getSymbol());
                    switch (shareInfo.getType()) {
                        case Common:
                            return shareInfo.getLastDividend() / price.getPrice();
                        case Preferred:
                            return shareInfo.getFixedDividend() * shareInfo.getParValue() / price.getPrice();
                    }
                    throw new IllegalArgumentException("Invalid share type:" + shareInfo.getType());
                }));
    }

    public Map<String, Double> getPERatio(List<Price> prices) {
        logger.info("getPERatio for prices:{}", prices);
        return prices.stream()
                .filter(price -> marketData.getMarketData().get(price.getSymbol()) != null && marketData.getMarketData().get(price.getSymbol()).getLastDividend() > 0)
                .collect(Collectors.toMap(Price::getSymbol,
                        price -> price.getPrice() / marketData.getMarketData().get(price.getSymbol()).getLastDividend()));
    }

    public Double getGeometricMean(List<Price> prices) {
        logger.info("getGeometricMean for prices:{}", prices);
        List<Price> priceList = prices.stream().filter(price -> price.getPrice() != 0d).collect(Collectors.toList());
        final int count = priceList.size();
        if (count > 0) {
            return priceList.stream().mapToDouble(price -> Math.pow(price.getPrice(), 1d / count)).reduce(1d, (_1, _2) -> _1 * _2);
        }
        return Double.NaN;
    }


    public Double getVolumeWSP() {
        AtomicLong quantity = new AtomicLong();
        Collection<Trade> tradeForLast15Minutes = tradeStore.getTradeForLast15Minutes();
        logger.info("getVolumeWSP for trades:{}", tradeForLast15Minutes);
        Double summ = tradeForLast15Minutes.stream()
                .filter(trade -> trade.getQuantity() > 0)
                .mapToDouble(trade -> {
                    quantity.addAndGet(trade.getQuantity());
                    return trade.getPrice() * trade.getQuantity();
                }).sum();
        return quantity.get() > 0 ? summ / quantity.get() : Double.NaN;
    }
}
