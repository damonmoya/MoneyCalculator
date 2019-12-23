package control;

import main.UserSelectedExchange;
import main.ExchangeRateSomeSourceReader;
import main.MoneyResultField;
import model.Exchange;

public class CalculateMoneyCommand implements Command {

    @Override
    public void execute() {
        Exchange exchange = new UserSelectedExchange().load();
        new MoneyResultField(exchange,new ExchangeRateSomeSourceReader(exchange).load()).show();
    }
}