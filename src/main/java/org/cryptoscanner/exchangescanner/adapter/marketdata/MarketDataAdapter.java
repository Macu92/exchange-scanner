package org.cryptoscanner.exchangescanner.adapter.marketdata;

import org.cryptoscanner.exchangescanner.adapter.PeriodAdapter;
import org.knowm.xchange.currency.CurrencyPair;
import org.ta4j.core.TimeSeries;

import java.io.IOException;

public interface MarketDataAdapter {
    public double getActualPrice(CurrencyPair currencyPair) throws IOException;
    public TimeSeries getMarketHistory(CurrencyPair currencyPair, PeriodAdapter.Period period) throws IOException;
}
