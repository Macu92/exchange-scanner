package org.cryptoscanner.exchangescanner.adapter.bittrex;

import org.cryptoscanner.exchangescanner.adapter.PeriodAdapter;
import org.cryptoscanner.exchangescanner.adapter.marketdata.MarketDataAdapter;
import org.knowm.xchange.bittrex.BittrexUtils;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexChartData;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexTrade;
import org.knowm.xchange.bittrex.service.BittrexChartDataPeriodType;
import org.knowm.xchange.bittrex.service.BittrexMarketDataService;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.ta4j.core.*;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BittrexMarketDataAdapter implements MarketDataAdapter {

    BittrexMarketDataService bittrexMarketDataService;

    public BittrexMarketDataAdapter(MarketDataService marketDataService) {
        this.bittrexMarketDataService = (BittrexMarketDataService) marketDataService;
    }

    public TimeSeries getMarketHistory(CurrencyPair currencyPair, PeriodAdapter.Period period) throws IOException {
        List<BittrexChartData> chartData = bittrexMarketDataService.getBittrexChartData(currencyPair, BittrexPeriodAdapter.getPeriod(period));
//        System.out.println(chartData.get(chartData.size()-1));
        TimeSeries marketHistoryTs = new BaseTimeSeries("bittrexHistory", adaptBittrexChartData(chartData, period));
        return marketHistoryTs;
//        List<Tick>
//        chartData = bittrexMarketDataService.getBittrexLatestTick(CurrencyPair.ETH_BTC, BittrexChartDataPeriodType.ONE_MIN, null);
//        System.out.println(chartData);
//        System.out.println(bittrexMarketDataService.getTicker(CurrencyPair.ETH_BTC,100).getTimestamp());
//        System.out.println(bittrexMarketDataService.getBittrexTicker(CurrencyPair.ETH_BTC));
//        System.out.println(bittrexMarketDataService.getTrades(CurrencyPair.ETH_BTC, Date.valueOf(LocalDate.of(2016,11,12))));
//        System.out.println("getBIttexTicker " + bittrexMarketDataService.getBittrexTicker(CurrencyPair.ETH_BTC));
//        List<BittrexTrade> trades = Arrays.asList(bittrexMarketDataService.getBittrexTrades(BittrexUtils.toPairString(CurrencyPair.ETH_BTC),500));
//        for(int i=0; i<trades.size()-1;i++){
//            System.out.println(i+ " " + trades.get(i));
//        }
    }

    public double getActualPrice(CurrencyPair currencyPair) throws IOException {
        return bittrexMarketDataService.getBittrexTicker(currencyPair).getLast().doubleValue();
    }

    private List<Bar> adaptBittrexChartData(List<BittrexChartData> bittrexChartData, PeriodAdapter.Period period) {
        return bittrexChartData.stream().map(chartData ->
                new BaseBar(PeriodAdapter.getDuration(period),
                        chartData.getTimeStamp().toInstant().atZone(ZoneId.systemDefault()),
                        Decimal.valueOf(chartData.getOpen().doubleValue()),
                        Decimal.valueOf(chartData.getHigh().doubleValue()),
                        Decimal.valueOf(chartData.getLow().doubleValue()),
                        Decimal.valueOf(chartData.getClose().doubleValue()),
                        Decimal.valueOf(chartData.getVolume().doubleValue())))
                .collect(Collectors.toList());

    }
//                new BaseBar(
//                chartData.getTimeStamp().toInstant().atZone(ZoneId.systemDefault()),// plus period another constructor
//                chartData.getOpen().doubleValue(),
//                chartData.getHigh().doubleValue(),
//                chartData.getClose().doubleValue(),
//                chartData.getClose().doubleValue(),
//                chartData.getVolume().doubleValue())).collect(Collectors.toList());
}
