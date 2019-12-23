package model;

import java.util.HashMap;
import java.util.Map;

public class CurrencySet {
    private final Map<String,Currency> currencyMap;

    public CurrencySet() {
        this.currencyMap = new HashMap<>();
    }

    public Map<String, Currency> currencyMap() {
        return currencyMap;
    }

    public void add(Currency currency){
        currencyMap.put(currency.code(),currency);
    }

    public Currency findCurrencyInCurrencySetByCode(String code) {
        return currencyMap.get(code);
    }
}