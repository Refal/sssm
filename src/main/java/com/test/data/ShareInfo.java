package com.test.data;

/**
 * Created by egor on 24.02.17.
 */
public class ShareInfo {

    private final String symbol;
    private final Type type;
    private final double lastDividend;
    private final double fixedDividend;
    private final double parValue;

    public ShareInfo(String symbol, Type type, double lastDividend, double fixedDividend, double parValue) {
        this.symbol = symbol;
        this.type = type;
        this.lastDividend = lastDividend;
        this.fixedDividend = fixedDividend;
        this.parValue = parValue;
    }

    public String getSymbol() {
        return symbol;
    }

    public Type getType() {
        return type;
    }

    public double getLastDividend() {
        return lastDividend;
    }

    public double getFixedDividend() {
        return fixedDividend;
    }

    public double getParValue() {
        return parValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShareInfo shareInfo = (ShareInfo) o;

        if (Double.compare(shareInfo.lastDividend, lastDividend) != 0) return false;
        if (Double.compare(shareInfo.fixedDividend, fixedDividend) != 0) return false;
        if (Double.compare(shareInfo.parValue, parValue) != 0) return false;
        if (!symbol.equals(shareInfo.symbol)) return false;
        return type == shareInfo.type;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = symbol.hashCode();
        result = 31 * result + type.hashCode();
        temp = Double.doubleToLongBits(lastDividend);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(fixedDividend);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(parValue);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "ShareInfo{" +
                "symbol='" + symbol + '\'' +
                ", type=" + type +
                ", lastDividend=" + lastDividend +
                ", fixedDividend=" + fixedDividend +
                ", parValue=" + parValue +
                '}';
    }

    public enum Type {Common, Preferred}
}
