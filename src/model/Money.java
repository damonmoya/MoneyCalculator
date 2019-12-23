package model;

public class Money {
    private final Currency currency;
    private final  double amount;

    public Money(Currency currency, double amount) {
        this.currency = currency;
        this.amount = amount;
    }

    public Currency currency() {
        return currency;
    }

    public double amount() {
        return amount;
    }

    @Override
    public String toString() {
        return String.valueOf(amount)+" "+currency.symbol();
    }
}