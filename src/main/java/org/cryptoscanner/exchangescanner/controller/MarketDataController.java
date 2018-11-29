package org.cryptoscanner.exchangescanner.controller;

import org.cryptoscanner.exchangescanner.adapter.EnumExchange;
import org.cryptoscanner.exchangescanner.adapter.ExchangeAdapter;
import org.cryptoscanner.exchangescanner.adapter.PeriodAdapter;
import org.cryptoscanner.exchangescanner.adapter.bittrex.BittrexMarketDataAdapter;
import org.cryptoscanner.exchangescanner.model.Market;
import org.cryptoscanner.exchangescanner.service.CandleService;
import org.cryptoscanner.exchangescanner.service.MarketJobService;
import org.cryptoscanner.exchangescanner.service.MarketService;
import org.knowm.xchange.currency.CurrencyPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.ta4j.core.indicators.RSIIndicator;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

import static java.lang.Thread.sleep;

@Controller(value = "/")
public class MarketDataController {

    @Autowired
    private MarketJobService marketJobService;
    @Autowired
    private MarketService marketService;
    @Autowired
    private CandleService candleService;

    public MarketDataController(MarketJobService marketJobService) {
        this.marketJobService = marketJobService;
    }

    @RequestMapping("/start")
    public @ResponseBody
    String startCotroller() throws Exception {
        marketJobService.addMarketJob(marketService.addMarket(EnumExchange.BITTREX, CurrencyPair.BTC_USDT));
        Market market = marketService.getMarketByExchangeAndCurrencyPair(EnumExchange.BITTREX, CurrencyPair.BTC_USDT);
        marketJobService.startJobs();


        return "dupa";
    }

    @RequestMapping("/test")
    public @ResponseBody
    String testCotroller() {
        candleService.getActualCandle(PeriodAdapter.Period._5m);
        return "dupa";
    }

}
