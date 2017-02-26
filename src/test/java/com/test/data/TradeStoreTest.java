package com.test.data;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by egor on 25.02.17.
 */
@RunWith(JUnit4.class)
public class TradeStoreTest {
    private TradeStore tradeStore;


    @Before
    public void setUp() {
        tradeStore = new TradeStore();
    }

    @Test
    public void testEmptyTradeStore() throws Exception {
        assertTrue(tradeStore.getAlltrades().isEmpty());

    }

    @Test
    public void testTradeForLast15Minutes() throws Exception {
        long now = System.currentTimeMillis();
        Trade tradeNow = new Trade(now, 1, Trade.Direction.SELL, 1.0, "TEA");
        tradeStore.addTrade(tradeNow);
        Trade tradeOld = new Trade(now - 20 * 60 * 1000, 1, Trade.Direction.BUY, 1.0, "TEA");
        tradeStore.addTrade(tradeOld);

        Collection<Trade> last15Minutes = tradeStore.getTradeForLast15Minutes();
        assertEquals(1, last15Minutes.size());
        assertEquals(tradeNow, last15Minutes.iterator().next());


    }


}