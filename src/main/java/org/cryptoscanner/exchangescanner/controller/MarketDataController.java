package org.cryptoscanner.exchangescanner.controller;

import org.cryptoscanner.exchangescanner.adapter.EnumExchange;
import org.cryptoscanner.exchangescanner.adapter.ExchangeAdapter;
import org.cryptoscanner.exchangescanner.adapter.bittrex.BittrexMarketDataAdapter;
import org.cryptoscanner.exchangescanner.model.Market;
import org.cryptoscanner.exchangescanner.service.MarketJobService;
import org.knowm.xchange.currency.CurrencyPair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller(value = "/")
public class MarketDataController {

    private MarketJobService marketJobService;

    public MarketDataController(MarketJobService marketJobService) {
        this.marketJobService = marketJobService;
    }

    @RequestMapping("/test")
    public @ResponseBody String testCotroller() throws Exception {
        System.out.println("reakcja");
//        Exchange exchange = ExchangeFactory.INSTANCE.createExchange(BittrexExchange.class.getName());
//        BittrexMarketDataServiceRaw marketDataService = (BittrexMarketDataServiceRaw)exchange.getMarketDataService();
//        List<BittrexChartData> chartData = null;
//        try {
//            chartData = marketDataService.getBittrexChartData(CurrencyPair.ETH_BTC, BittrexChartDataPeriodType.ONE_MIN);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        ExchangeAdapter exchangeAdapter = new ExchangeAdapter(EnumExchange.BITTREX);
//        BittrexMarketDataAdapter marketDataAdapter = (BittrexMarketDataAdapter)exchangeAdapter.getMarketDataAdapter();
//        marketDataAdapter.getMarketHistory(CurrencyPair.BCC_BTC,"1m");
//        marketDataAdapter.getActualPrice(CurrencyPair.ETH_BTC);

        marketJobService.addMarketJob(new Market(EnumExchange.BITTREX,CurrencyPair.BTC_USDT));
        marketJobService.startJobs();

        return "dupa";
    }

}
