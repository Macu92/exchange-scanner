package org.cryptoscanner.exchangescanner.indicators;

import org.ta4j.core.Decimal;
import org.ta4j.core.TimeSeries;

import java.time.Duration;

public class PercentagePriceChangeInTimeIndicator extends PriceChangeInTimeIndicator{


    public PercentagePriceChangeInTimeIndicator(TimeSeries series, Duration duration) {
        super(series, duration);
    }

    @Override
    public Decimal getValue(int i) {
        Decimal priceChange =  super.getValue(i);
        return priceChange.dividedBy(this.getTimeSeries().getBar(i).getClosePrice()).multipliedBy(100);
    }
}
