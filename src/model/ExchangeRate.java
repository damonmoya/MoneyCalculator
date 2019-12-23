package model;


public class ExchangeRate {
    private final Currency from;
    private final Currency to;
    private final double rate;

    public ExchangeRate(Currency from, Currency to, double rate) {
        this.from = from;
        this.to = to;
        this.rate = rate;
    }

    public Currency from() {
        return from;
    }

    public Currency to() {
        return to;
    }

    public double rate() {
        return rate;
    }
}