package com.test.data;

import java.math.BigDecimal;

/**
 * Created by egor on 25.02.17.
 */
public class Price {

    private final String symbol;
    private final Double price;

    public Price(String symbol, Double price) {
        this.symbol = symbol;
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public Double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Price price1 = (Price) o;

        if (symbol != null ? !symbol.equals(price1.symbol) : price1.symbol != null) return false;
        return price != null ? price.equals(price1.price) : price1.price == null;
    }

    @Override
    public int hashCode() {
        int result = symbol != null ? symbol.hashCode() : 0;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Price{" +
                "symbol='" + symbol + '\'' +
                ", price=" + price +
                '}';
    }
}
