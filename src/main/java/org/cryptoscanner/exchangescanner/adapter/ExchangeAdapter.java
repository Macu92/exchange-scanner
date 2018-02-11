package org.cryptoscanner.exchangescanner.adapter;

import org.cryptoscanner.exchangescanner.adapter.marketdata.MarketDataAdapter;
import org.cryptoscanner.exchangescanner.adapter.binance.BinanceMarketDataAdapter;
import org.cryptoscanner.exchangescanner.adapter.bittrex.BittrexMarketDataAdapter;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.bittrex.BittrexExchange;

public class ExchangeAdapter {
    private Exchange exchange;


    //tu mozna bedzie zorbic klase tworzaca konfiguracje juz na starcie i statyczne metody by zwracaly odpowiedznie serwisy na podstawei enuma gieldy
    public ExchangeAdapter(EnumExchange enumExchange) {
        exchange = ExchangeFactory.INSTANCE.createExchange(enumExchange.getExchangeName());
    }

    public ExchangeAdapter(ExchangeSpecificationAdapter exchangeSpecificationAdapter) {
        exchange = ExchangeFactory.INSTANCE.createExchange(exchangeSpecificationAdapter.getExchangeSpecification());
    }

    public MarketDataAdapter getMarketDataAdapter() throws Exception {
        if (exchange instanceof BittrexExchange) {
            return new BittrexMarketDataAdapter(exchange.getMarketDataService());
        } else if (exchange instanceof BinanceExchange) {
            return new BinanceMarketDataAdapter(exchange.getMarketDataService());
        } else {
            throw new Exception("No suitable market");
        }
    }


}
