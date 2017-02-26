package com.test;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.test.data.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by egor on 24.02.17.
 */


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Configuration.class, loader = AnnotationConfigContextLoader.class)
public class CalculatorTest {
    @MockBean
    private MarketData marketData;

    @MockBean
    private TradeStore tradeStore;

    @Autowired
    private Calculator calculator;

    @Test
    public void testGetEmptyDividendYield() {
        when(marketData.getMarketData()).thenReturn(Collections.emptyMap());
        Map<String, Double> dividendYield = calculator.getDividendYield(Collections.emptyList());

        assertTrue(dividendYield.isEmpty());
    }


    @Test
    public void testZeroDividendYieldOneCommonTrade() {
        when(marketData.getMarketData()).thenReturn(getMarketData());
        List<Price> prices = Lists.newArrayList();
        String symbol = "TEA";
        prices.add(new Price(symbol, 1.1d));
        Map<String, Double> dividendYield = calculator.getDividendYield(prices);

        assertFalse(dividendYield.isEmpty());
        assertNotNull(dividendYield.get(symbol));
        assertEquals(0.0d, dividendYield.get(symbol), 0.0001d);
    }


    @Test
    public void testInvalidDividendYieldOneCommonTrade() {
        when(marketData.getMarketData()).thenReturn(getMarketData());
        List<Price> prices = Lists.newArrayList();
        String symbol = "ERROR";
        prices.add(new Price(symbol, 10d));
        Map<String, Double> dividendYield = calculator.getDividendYield(prices);

        assertTrue(dividendYield.isEmpty());
    }

    @Test
    public void testGetDividendYieldOneCommonTrade() {
        when(marketData.getMarketData()).thenReturn(getMarketData());
        List<Price> prices = Lists.newArrayList();
        String symbol = "POP";
        prices.add(new Price(symbol, 1.1));
        Map<String, Double> dividendYield = calculator.getDividendYield(prices);

        assertFalse(dividendYield.isEmpty());
        assertEquals(8 / 1.1d, dividendYield.get(symbol), 0.0001d);
    }


    @Test
    public void testGetDividendYieldOnePreferedTrade() {
        when(marketData.getMarketData()).thenReturn(getMarketData());
        List<Price> prices = Lists.newArrayList();
        String symbol = "GIN";
        prices.add(new Price(symbol, 110d));
        Map<String, Double> dividendYield = calculator.getDividendYield(prices);

        assertFalse(dividendYield.isEmpty());
        assertEquals(2 * 100 / 110.0d, dividendYield.get(symbol), 0.0001d);
    }


    @Test
    public void testPERatioForOneTrade() {
        when(marketData.getMarketData()).thenReturn(getMarketData());
        List<Price> prices = Lists.newArrayList();
        String symbol = "JOE";
        prices.add(new Price(symbol, 1.1d));
        Map<String, Double> peRatio = calculator.getPERatio(prices);

        assertFalse(peRatio.isEmpty());
        assertEquals(1.1d / 13, peRatio.get(symbol), 0.001d);
    }


    @Test
    public void testEmptyVolumeWSP() {
        when(marketData.getMarketData()).thenReturn(getMarketData());
        when(tradeStore.getTradeForLast15Minutes()).thenReturn(Collections.emptyList());
        Double volumeWSP = calculator.getVolumeWSP();

        assertEquals(Double.NaN, volumeWSP, 0.001d);
    }

    @Test
    public void testVolumeWSP() {
        when(marketData.getMarketData()).thenReturn(getMarketData());
        List<Trade> trades = Lists.newArrayList();
        String symbol = "TEA";
        trades.add(new Trade(System.currentTimeMillis(), 1, Trade.Direction.BUY, 1.1d, symbol));
        when(tradeStore.getTradeForLast15Minutes()).thenReturn(trades);
        Double volumeWSP = calculator.getVolumeWSP();

        assertEquals(1.1d, volumeWSP, 0.001d);
    }


    @Test
    public void testEmptyGeometricMean() {
        when(marketData.getMarketData()).thenReturn(getMarketData());
        List<Price> prices = Lists.newArrayList();
        Double volumeWSP = calculator.getGeometricMean(prices);

        assertEquals(Double.NaN, volumeWSP, 0.001d);
    }

    @Test
    public void testOnePriceGeometricMean() {
        when(marketData.getMarketData()).thenReturn(getMarketData());
        List<Price> prices = Lists.newArrayList();
        String symbol = "TEA";
        prices.add(new Price(symbol, 1.1d));
        Double volumeWSP = calculator.getGeometricMean(prices);

        assertEquals(1.1d, volumeWSP, 0.001d);
    }


    @Test
    public void testEqualsGeometricMean() {
        when(marketData.getMarketData()).thenReturn(getMarketData());
        List<Price> prices = Lists.newArrayList();
        prices.add(new Price("TEA", 3d));
        prices.add(new Price("POP", 3d));
        prices.add(new Price("ALE", 3d));
        prices.add(new Price("GIN", 3d));
        prices.add(new Price("JOE", 3d));
        Double volumeWSP = calculator.getGeometricMean(prices);

        assertEquals(3d, volumeWSP, 0.001d);
    }

    private Map<String, ShareInfo> getMarketData() {
        Map<String, ShareInfo> data = Maps.newHashMap();
        data.put("TEA", new ShareInfo("TEA", ShareInfo.Type.Common, 0, 0, 100));
        data.put("POP", new ShareInfo("POP", ShareInfo.Type.Common, 8, 0, 100));
        data.put("ALE", new ShareInfo("ALE", ShareInfo.Type.Common, 23, 0, 60));
        data.put("GIN", new ShareInfo("GIN", ShareInfo.Type.Preferred, 8, 2, 100));
        data.put("JOE", new ShareInfo("JOE", ShareInfo.Type.Common, 13, 0, 250));
        return data;
    }
}
