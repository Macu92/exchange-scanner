package org.cryptoscanner.exchangescanner.adapter;

import org.cryptoscanner.exchangescanner.adapter.marketdata.MarketDataAdapter;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;

public class ExchangeAdapter {
    Exchange exchange;

    public ExchangeAdapter (EnumExchange enumExchange){
        exchange = ExchangeFactory.INSTANCE.createExchange(enumExchange.getExchangeName());
    }

    public ExchangeAdapter (ExchangeSpecificationAdapter exchangeSpecificationAdapter){
        exchange = ExchangeFactory.INSTANCE.createExchange(exchangeSpecificationAdapter.getExchangeSpecification());
    }

//    public MarketDataAdapter getMarketDataAdapter(){
//        return exchange.getMarketDataService();
//    }


}
