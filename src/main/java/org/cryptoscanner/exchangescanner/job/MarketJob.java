package org.cryptoscanner.exchangescanner.job;

import org.cryptoscanner.exchangescanner.adapter.ExchangeAdapter;
import org.cryptoscanner.exchangescanner.adapter.marketdata.MarketDataAdapter;
import org.cryptoscanner.exchangescanner.model.Market;
import org.ta4j.core.indicators.RSIIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.TimerTask;

import static java.lang.Thread.sleep;

public class MarketJob extends TimerTask {

    private Market market;
    private MarketDataAdapter marketDataAdapter;
    private boolean isIndicatorInitialized = false;

    public MarketJob(Market market) throws Exception {
        this.market = market;
        this.marketDataAdapter = new ExchangeAdapter(market.getEchange()).getMarketDataAdapter();
    }

    public void run() {
        System.out.println(market.getEchange().toString());
        System.out.println(market.getCurrencyPair().toString());
        try {
            this.market.buildActualCandle(this.marketDataAdapter.getActualPrice(market.getCurrencyPair()), 0);
            if (market.getCandles() == null || market.getCandles().isEmpty()) {
                market.setCandles(this.marketDataAdapter.getMarketHistory(market.getCurrencyPair(), market.getPeriod()));
                market.setFirstHistoryDataRequest(LocalDateTime.now());
            }
//            else if (ChronoUnit.MINUTES.between(market.getFirstHistoryDataRequest(), LocalDateTime.now()) > 6 &&
//                    market.isSecondGetHistory() == false) {
//                market.fixDataHole(this.marketDataAdapter.getMarketHistory(market.getCurrencyPair(), market.getPeriod()));
                market.setSecondGetHistory(true);
//            }
            for (int i = 0; i < 5; i++) {
                System.out.println(this.market.getCandles().getBar(this.market.getCandles().getEndIndex() - i).getBeginTime() + " "
                        + this.market.getCandles().getBar(this.market.getCandles().getEndIndex() - i).toString());
            }

            if(this.market.getCandles()!=null && !this.isIndicatorInitialized){
                ClosePriceIndicator closePrice = new ClosePriceIndicator(market.getCandles());
                RSIIndicator indicator = new RSIIndicator(closePrice,14);
                market.addIndicator(indicator);
                this.isIndicatorInitialized = true;

            }
            System.out.println("---JOB MARKET---");
            System.out.println("------------ACTUAL------------------");
            System.out.println(this.market.getActualCandle().toString());
            System.out.println("------------TS TEST------------------");
            System.out.println(this.market.getCandles().getLastBar());
            if(this.market.getIndicators()!=null) {
                System.out.println(this.market.getIndicators().get(0).getValue(this.market.getIndicators().get(0).getTimeSeries().getEndIndex()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
