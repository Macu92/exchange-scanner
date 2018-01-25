package org.cryptoscanner.exchangescanner.controller;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bittrex.BittrexExchange;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexChartData;
import org.knowm.xchange.bittrex.service.BittrexChartDataPeriodType;
import org.knowm.xchange.bittrex.service.BittrexMarketDataServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

@Controller(value = "/")
public class MarketDataController {

    @RequestMapping("/test")
    public @ResponseBody String testCotroller() {
        System.out.println("reakcja");
        Exchange exchange = ExchangeFactory.INSTANCE.createExchange(BittrexExchange.class.getName());
        BittrexMarketDataServiceRaw marketDataService = (BittrexMarketDataServiceRaw)exchange.getMarketDataService();
        List<BittrexChartData> chartData = null;
        try {
            chartData = marketDataService.getBittrexChartData(CurrencyPair.ETH_BTC, BittrexChartDataPeriodType.ONE_MIN);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return chartData.toString();
    }

}
