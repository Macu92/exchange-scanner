package org.cryptoscanner.exchangescanner.model;

import lombok.Getter;
import lombok.Setter;
import org.cryptoscanner.exchangescanner.adapter.EnumExchange;
import org.cryptoscanner.exchangescanner.adapter.PeriodAdapter;
import org.knowm.xchange.currency.CurrencyPair;
import org.ta4j.core.*;

import java.time.*;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class Market {
    private TimeSeries candles;
    private TimeSeries candles1m;
    private BaseBar actualCandle;
    private BaseBar actualCandle1m;
    private double actualPrice;
    private List<Indicator> indicators;
    private EnumExchange exchange;
    private CurrencyPair currencyPair;
    private PeriodAdapter.Period period;

    //temp solution for bittrex data hole
    public LocalDateTime firstHistoryDataRequest;
    public boolean secondGetHistory = false;

    public Market(EnumExchange exchange, CurrencyPair currencyPair) {
        this.exchange = exchange;
        this.currencyPair = currencyPair;
        this.period = PeriodAdapter.Period._30m;
        this.indicators = new LinkedList<>();
    }

    public void addIndicator(Indicator indicator) {
        this.indicators.add(indicator);
    }

    public void addCandle(BaseBar baseBar){
        candles.addBar(baseBar);
    }

    public void addCandle1m(BaseBar baseBar){
        candles1m.addBar(baseBar);
    }

    //temp solution for bittrex data hole
    //-----------------------------
    public LocalDateTime getFirstHistoryDataRequest() {
        return firstHistoryDataRequest;
    }

    public void setFirstHistoryDataRequest(LocalDateTime firstHistoryDataRequest) {
        this.firstHistoryDataRequest = firstHistoryDataRequest;
    }

    public boolean isSecondGetHistory() {
        return secondGetHistory;
    }

    public void setSecondGetHistory(boolean secondGetHistory) {
        this.secondGetHistory = secondGetHistory;
    }

    public void fixDataHole(TimeSeries candles) {
        ZonedDateTime newDataLastCandleTime = candles.getLastBar().getEndTime();
        Integer synchornizedIndex = null;
        for (int i = 0; i < 30; i++) {
            if (this.candles.getBar(this.candles.getEndIndex() - i).getEndTime().isEqual(newDataLastCandleTime)) {
                synchornizedIndex = i;
                break;
            }
        }
        if (null != synchornizedIndex) {
//            for (int i = 0; i < 30; i++) {
//                System.out.println(this.candles.getBar(this.candles.getEndIndex()-synchornizedIndex-i));
//                System.out.println(candles.getBar(candles.getEndIndex()-i));
//            }
            TimeSeries temp = this.candles.getSubSeries(0, synchornizedIndex);
            this.candles = candles;
            for (int i = 0; i < temp.getEndIndex(); i++) {
                this.candles.addBar(temp.getBar(i));
            }
        }
        throw new Error("Index not found");
    }
    //------------------------------

    public void buildActualCandle(double price, double volume) {
        if (actualCandle == null) {
            actualCandle = new BaseBar(PeriodAdapter.getDuration(period),
                    ZonedDateTime.of(LocalDateTime.now().withNano(0).withSecond(0).plusMinutes(PeriodAdapter.getDuration(period).toMinutes()), ZoneId.systemDefault()));
            candles.addBar(actualCandle);
        } else if (actualCandle.getEndTime().withSecond(0).withNano(0).isBefore(
                ZonedDateTime.now(actualCandle.getEndTime().getZone()).withSecond(0).withNano(0)) && actualCandle != null) {
            actualCandle = new BaseBar(PeriodAdapter.getDuration(period),
                    ZonedDateTime.of(LocalDateTime.now().withNano(0).withSecond(0), ZoneId.systemDefault()));
            candles.addBar(actualCandle);
//            ZonedDateTime.now().get()
        }
        actualCandle.addTrade(Decimal.valueOf(volume), Decimal.valueOf(price));
    }

    public void build1mActualCandle(double price, double volume) {
        if (actualCandle1m == null) {
            actualCandle1m = new BaseBar(PeriodAdapter.getDuration(PeriodAdapter.Period._1m),
                    ZonedDateTime.of(LocalDateTime.now().withNano(0).withSecond(0), ZoneId.systemDefault()));
            candles1m.addBar(actualCandle1m);
        } else if (actualCandle1m.getEndTime().withSecond(0).withNano(0).isBefore(
                ZonedDateTime.now(actualCandle1m.getEndTime().getZone()).withSecond(0).withNano(0)) && actualCandle1m != null) {
            actualCandle1m = new BaseBar(PeriodAdapter.getDuration(PeriodAdapter.Period._1m),
                    ZonedDateTime.of(LocalDateTime.now().withNano(0).withSecond(0), ZoneId.systemDefault()));
            candles1m.addBar(actualCandle1m);
        }
        actualCandle1m.addTrade(Decimal.valueOf(volume), Decimal.valueOf(price));
    }
}
