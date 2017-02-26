package com.test;

import com.test.data.MarketData;
import com.test.data.MarketDataImpl;
import com.test.data.TradeStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by egor on 24.02.17.
 */
@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    MarketData getMarketData() {
        return new MarketDataImpl();
    }

    @Bean
    TradeStore tradeStore() {
        return new TradeStore();
    }

    @Bean
    Calculator calculator() {
        return new Calculator(getMarketData(), tradeStore());
    }

}
