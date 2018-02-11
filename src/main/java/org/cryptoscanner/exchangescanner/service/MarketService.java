package org.cryptoscanner.exchangescanner.service;

import org.cryptoscanner.exchangescanner.adapter.EnumExchange;
import org.cryptoscanner.exchangescanner.model.Market;
import org.knowm.xchange.currency.CurrencyPair;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class MarketService {

    List<Market> markets;

    public MarketService(List<Market> markets) {
        this.markets = markets;
    }

    public MarketService() {
        this.markets = new LinkedList<Market>();
    }

    private MarketJobService marketJobService;

    public MarketService(MarketJobService marketJobService) {
        this.marketJobService = marketJobService;
    }

    public void addMarket(EnumExchange enumExchange, CurrencyPair currencyPair){
        markets.add(new Market(enumExchange,currencyPair));
    }
}
