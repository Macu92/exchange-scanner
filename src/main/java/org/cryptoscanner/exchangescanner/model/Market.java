package org.cryptoscanner.exchangescanner.model;

import org.cryptoscanner.exchangescanner.adapter.EnumExchange;
import org.cryptoscanner.exchangescanner.adapter.PeriodAdapter;
import org.knowm.xchange.currency.CurrencyPair;
import org.ta4j.core.Bar;
import org.ta4j.core.BaseBar;
import org.ta4j.core.Decimal;
import org.ta4j.core.TimeSeries;

import java.time.*;
import java.util.List;

public class Market {
    private TimeSeries candles;
    private BaseBar actualCandle;
    private double actualPrice;
    //    List<Indicators> indicators;
    EnumExchange echange;
    CurrencyPair currencyPair;
    PeriodAdapter.Period period;

    public Market(EnumExchange echange, CurrencyPair currencyPair) {
        this.echange = echange;
        this.currencyPair = currencyPair;
        this.period = PeriodAdapter.Period._1m;
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

    public void buildActualCanlde(double price, double volume) {
        if (actualCandle == null) {
            actualCandle = new BaseBar(Duration.ofMinutes(1),
                    ZonedDateTime.of(LocalDateTime.now().withNano(0).withSecond(0), ZoneId.systemDefault()));
        } else if (actualCandle.getEndTime().withSecond(0).withNano(0).isBefore(
                ZonedDateTime.now(actualCandle.getEndTime().getZone()).withSecond(0).withNano(0)) && actualCandle != null) {
            candles.addBar(actualCandle);
            actualCandle = new BaseBar(Duration.ofMinutes(1),
                    ZonedDateTime.of(LocalDateTime.now().withNano(0).withSecond(0), ZoneId.systemDefault()));
        }
        actualCandle.addTrade(Decimal.valueOf(volume), Decimal.valueOf(price));
    }
}
