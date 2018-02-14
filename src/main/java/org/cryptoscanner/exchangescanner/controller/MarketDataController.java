package org.cryptoscanner.exchangescanner.controller;

import org.cryptoscanner.exchangescanner.adapter.EnumExchange;
import org.cryptoscanner.exchangescanner.adapter.ExchangeAdapter;
import org.cryptoscanner.exchangescanner.adapter.bittrex.BittrexMarketDataAdapter;
import org.cryptoscanner.exchangescanner.model.Market;
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

    public MarketDataController(MarketJobService marketJobService) {
        this.marketJobService = marketJobService;
    }

    @RequestMapping("/test")
    public @ResponseBody String testCotroller() throws Exception {
        System.out.println("reakcja");

        marketJobService.addMarketJob(marketService.addMarket(EnumExchange.BITTREX,CurrencyPair.BTC_USDT));
        Market market = marketService.getMarketByExchangeAndCurrencyPair(EnumExchange.BITTREX,CurrencyPair.BTC_USDT);
        System.out.println("---CONTROLLER MARKET---");
        System.out.println("---AFTER ADD INDI---");
        marketJobService.startJobs();


        return "dupa";
    }

}
