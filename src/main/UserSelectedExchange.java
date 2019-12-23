package main;

import model.Currency;
import model.Exchange;
import model.Money;
import view.ExchangeRateLoader;

import javax.swing.*;

import static main.Main.components;
import static main.Main.currencySet;

public class UserSelectedExchange implements ExchangeRateLoader {
    @Override
    public Exchange load() {
        return new Exchange(new Money(currencyFrom(), amount()), currencyTo());
    }

    public Currency currencyFrom() {
        try{
            return  currencySet().findCurrencyInCurrencySetByCode(((JComboBox) components().get("OriginalCombo")).getSelectedItem().toString());
        }catch (Exception e){
            return null;
        }
    }

    public double amount() {
        try {
            return Double.parseDouble(((JTextField) components().get("OriginalMoneyTextField")).getText());
        }catch (Exception e){
            return 0;
        }
    }

    public Currency currencyTo() {
        try {
            return currencySet().findCurrencyInCurrencySetByCode(((JComboBox) components().get("ExchangeToCombo")).getSelectedItem().toString());
        }catch (Exception e){
            return null;
        }
    }
}