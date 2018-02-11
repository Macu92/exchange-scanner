package org.cryptoscanner.exchangescanner.job;

import org.cryptoscanner.exchangescanner.adapter.ExchangeAdapter;
import org.cryptoscanner.exchangescanner.adapter.marketdata.MarketDataAdapter;
import org.cryptoscanner.exchangescanner.model.Market;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bittrex.BittrexExchange;

import java.io.IOException;
import java.util.TimerTask;

public class MarketJob extends TimerTask{

    private Market market;
    private MarketDataAdapter marketDataAdapter;

    public MarketJob(Market market) throws Exception {
        this.market = market;
        this.marketDataAdapter = new ExchangeAdapter(market.getEchange()).getMarketDataAdapter();
    }

    public void run(){
        System.out.println(market.getEchange().toString());
        System.out.println(market.getCurrencyPair().toString());
        try {
            this.market.buildActualCanlde(this.marketDataAdapter.getActualPrice(market.getCurrencyPair()),0);
            if(market.getCandles() == null || market.getCandles().isEmpty()) {
                market.setCandles(this.marketDataAdapter.getMarketHistory(market.getCurrencyPair(), market.getPeriod()));
            }
            for(int i=0; i<5; i++) {
                System.out.println(this.market.getCandles().getBar(this.market.getCandles().getEndIndex()-i).getBeginTime() + " "
                       + this.market.getCandles().getBar(this.market.getCandles().getEndIndex()-i).toString());
            }
            System.out.println("------------ACTUAL------------------");
            System.out.println(this.market.getActualCandle().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
