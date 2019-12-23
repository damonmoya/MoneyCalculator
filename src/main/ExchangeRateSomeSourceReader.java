package main;

import model.Exchange;
import model.ExchangeRate;

public class ExchangeRateSomeSourceReader {
    private Exchange exchange;

    public ExchangeRateSomeSourceReader(Exchange exchange) {
        this.exchange = exchange;
    }

    public ExchangeRate load() {
        return new ExchangeRate(exchange.moneyFrom().currency(),exchange.currencyTo(),1.2);
    }
}