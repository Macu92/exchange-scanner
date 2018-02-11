package org.cryptoscanner.exchangescanner.adapter.bittrex;

import org.cryptoscanner.exchangescanner.adapter.PeriodAdapter;
import org.knowm.xchange.bittrex.service.BittrexChartDataPeriodType;

public interface BittrexPeriodAdapter extends PeriodAdapter {

    public static BittrexChartDataPeriodType getPeriod(Period period) {
        switch (period) {
            case _1m:
                return BittrexChartDataPeriodType.ONE_MIN;
            case _5m:
                return BittrexChartDataPeriodType.FIVE_MIN;
            case _30m:
                return BittrexChartDataPeriodType.THIRTY_MIN;
            case _1h:
                return BittrexChartDataPeriodType.ONE_HOUR;
            case _1D:
                return BittrexChartDataPeriodType.ONE_DAY;
            default:
                return BittrexChartDataPeriodType.ONE_HOUR;
        }
    }

}
