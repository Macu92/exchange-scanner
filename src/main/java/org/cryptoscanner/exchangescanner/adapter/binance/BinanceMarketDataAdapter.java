package org.cryptoscanner.exchangescanner.adapter.binance;

import org.cryptoscanner.exchangescanner.adapter.PeriodAdapter;
import org.cryptoscanner.exchangescanner.adapter.marketdata.MarketDataAdapter;
import org.knowm.xchange.binance.service.BinanceMarketDataService;
import org.knowm.xchange.binance.service.BinanceMarketDataServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.ta4j.core.TimeSeries;

import java.io.IOException;

public class BinanceMarketDataAdapter implements MarketDataAdapter{
    private BinanceMarketDataService binanceMarketDataService;
    public BinanceMarketDataAdapter(MarketDataService marketDataService) {
        this.binanceMarketDataService = (BinanceMarketDataService)marketDataService;
    }


    @Override
    public double getActualPrice(CurrencyPair currencyPair) throws IOException {
        return 0;
    }

    @Override
    public TimeSeries getMarketHistory(CurrencyPair currencyPair, PeriodAdapter.Period period) throws IOException {
        return null;
    }
}
