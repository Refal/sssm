package com.test.data;

/**
 * Created by egor on 24.02.17.
 */
public class Trade {
    private final long timeStamp;
    private final long quantity;
    private final Direction buySell;
    private final Double price;
    private final String symbol;

    public Trade(long timeStamp, long quantity, Direction buySell, Double price, String symbol) {
        this.timeStamp = timeStamp;
        this.quantity = quantity;
        this.buySell = buySell;
        this.price = price;
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public long getQuantity() {
        return quantity;
    }

    public Direction getBuySell() {
        return buySell;
    }

    public Double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Trade{" +
                "timeStamp=" + timeStamp +
                ", quantity=" + quantity +
                ", buySell=" + buySell +
                ", price=" + price +
                ", symbol='" + symbol + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Trade trade = (Trade) o;

        if (timeStamp != trade.timeStamp) return false;
        if (quantity != trade.quantity) return false;
        if (buySell != trade.buySell) return false;
        if (price != null ? !price.equals(trade.price) : trade.price != null) return false;
        return symbol != null ? symbol.equals(trade.symbol) : trade.symbol == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (timeStamp ^ (timeStamp >>> 32));
        result = 31 * result + (int) (quantity ^ (quantity >>> 32));
        result = 31 * result + (buySell != null ? buySell.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (symbol != null ? symbol.hashCode() : 0);
        return result;
    }

    public enum Direction {BUY, SELL}
}
