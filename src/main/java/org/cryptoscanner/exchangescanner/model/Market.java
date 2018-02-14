package org.cryptoscanner.exchangescanner.model;

import org.cryptoscanner.exchangescanner.adapter.EnumExchange;
import org.cryptoscanner.exchangescanner.adapter.PeriodAdapter;
import org.knowm.xchange.currency.CurrencyPair;
import org.ta4j.core.*;

import java.time.*;
import java.util.LinkedList;
import java.util.List;

public class Market {
    private TimeSeries candles;
    private BaseBar actualCandle;
    private double actualPrice;
    List<Indicator> indicators;
    EnumExchange echange;
    CurrencyPair currencyPair;
    PeriodAdapter.Period period;

    //temp solution for bittrex data hole
    public LocalDateTime firstHistoryDataRequest;
    public boolean secondGetHistory = false;

    public Market(EnumExchange echange, CurrencyPair currencyPair) {
        this.echange = echange;
        this.currencyPair = currencyPair;
        this.period = PeriodAdapter.Period._5m;
        this.indicators =  new LinkedList<>();
    }

    public TimeSeries getCandles() {
        return candles;
    }

    public void setCandles(TimeSeries candles) {
        this.candles = candles;
    }

    public Bar getActualCandle() {
        return actualCandle;
    }

    public void setActualCandle(BaseBar actualCandle) {
        this.actualCandle = actualCandle;
    }

    public EnumExchange getEchange() {
        return echange;
    }

    public void setEchange(EnumExchange echange) {
        this.echange = echange;
    }

    public CurrencyPair getCurrencyPair() {
        return currencyPair;
    }

    public void setCurrencyPair(CurrencyPair currencyPair) {
        this.currencyPair = currencyPair;
    }

    public double getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(double actualPrice) {
        this.actualPrice = actualPrice;
    }

    public PeriodAdapter.Period getPeriod() {
        return period;
    }

    public void setPeriod(PeriodAdapter.Period period) {
        this.period = period;
    }

    public void addIndicator(Indicator indicator){
        this.indicators.add(indicator);
    }

    public List<Indicator> getIndicators() {
        return indicators;
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
            TimeSeries temp = this.candles.getSubSeries(0,synchornizedIndex);
            this.candles = candles;
            for(int i=0;i<temp.getEndIndex();i++){
                this.candles.addBar(temp.getBar(i));
            }
        }
        throw new Error("Index not found");
    }
    //------------------------------

    public void buildActualCandle(double price, double volume) {
        if (actualCandle == null) {
            actualCandle = new BaseBar(PeriodAdapter.getDuration(period),
                    ZonedDateTime.of(LocalDateTime.now().withNano(0).withSecond(0), ZoneId.systemDefault()));
        } else if (actualCandle.getEndTime().withSecond(0).withNano(0).isBefore(
                ZonedDateTime.now(actualCandle.getEndTime().getZone()).withSecond(0).withNano(0)) && actualCandle != null) {
            candles.addBar(actualCandle);
            actualCandle = new BaseBar(PeriodAdapter.getDuration(period),
                    ZonedDateTime.of(LocalDateTime.now().withNano(0).withSecond(0), ZoneId.systemDefault()));
        }
        actualCandle.addTrade(Decimal.valueOf(volume), Decimal.valueOf(price));
    }
}
