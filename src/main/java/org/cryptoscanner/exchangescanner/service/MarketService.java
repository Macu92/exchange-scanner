package org.cryptoscanner.exchangescanner.service;

import org.cryptoscanner.exchangescanner.adapter.EnumExchange;
import org.cryptoscanner.exchangescanner.model.Market;
import org.knowm.xchange.currency.CurrencyPair;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MarketService {

    List<Market> markets;

    public MarketService(List<Market> markets) {
        this.markets = markets;
    }

    public MarketService() {
        this.markets = new LinkedList<>();
    }

    private MarketJobService marketJobService;

    public MarketService(MarketJobService marketJobService) {
        this.marketJobService = marketJobService;
    }

    public Market addMarket(EnumExchange enumExchange, CurrencyPair currencyPair){
        Market market = new Market(enumExchange,currencyPair);
        System.out.println("---ADD MARKET---");
        System.out.println(Integer.toHexString(System.identityHashCode(market)));
        markets.add(market);
        return market;
    }

    public List<Market> getMarkets() {
        return markets;
    }

    public void setMarkets(List<Market> markets) {
        this.markets = markets;
    }

    public List<Market> getMarketsByExchange(EnumExchange enumExchange){
        return this.markets.stream().filter(market -> market.getEchange().equals(enumExchange)).collect(Collectors.toList());
    }

    public Market getMarketByExchangeAndCurrencyPair(EnumExchange enumExchange, CurrencyPair currencyPair){
        return this.markets.stream().filter(market -> market.getEchange().equals(enumExchange))
                .filter(market -> market.getCurrencyPair().equals(currencyPair))
                .collect(Collectors.toList()).get(0);
    }
}
