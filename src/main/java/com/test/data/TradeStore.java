package com.test.data;

import com.google.common.collect.Maps;

import java.util.Collection;
import java.util.SortedMap;

/**
 * Created by egor on 24.02.17.
 */
public class TradeStore {
    private final static long OFFSET = 15 * 60 * 1000L;
    private SortedMap<Long, Trade> trades = Maps.newTreeMap();


    public void addTrade(Trade trade) {
        trades.put(trade.getTimeStamp(), trade);
    }


    public Collection<Trade> getTradeForLast15Minutes() {
        long current = System.currentTimeMillis();
        return trades.tailMap(current - OFFSET).values();

    }


    public Collection<Trade> getAlltrades() {
        return trades.values();
    }
}
