package org.cryptoscanner.exchangescanner.adapter;

import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.bittrex.BittrexExchange;

public class ExchangeSpecificationAdapter {
    private ExchangeSpecification exchangeSpecification;

    public ExchangeSpecificationAdapter(EnumExchange enumExchange) {
        setExchange(enumExchange);

    }

    public ExchangeSpecification getExchangeSpecification() {
        return exchangeSpecification;
    }

    public void setExchange(EnumExchange enumExchange) {
        if (enumExchange.equals(EnumExchange.BITTREX)) {
            this.exchangeSpecification = new BittrexExchange().getExchangeSpecification();
        } else if (enumExchange.equals(EnumExchange.BINANCE)) {
            this.exchangeSpecification = new BinanceExchange().getExchangeSpecification();
        }
    }

    public void setUserName(String userName){
        exchangeSpecification.setUserName(userName);
    }

    public void setApiKey(String apiKey){
        exchangeSpecification.setApiKey(apiKey);
    }

    public void setSecretKey(String secretKey){
        exchangeSpecification.setSecretKey(secretKey);
    }


}
