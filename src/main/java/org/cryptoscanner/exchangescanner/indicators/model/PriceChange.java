package org.cryptoscanner.exchangescanner.indicators.model;

import org.ta4j.core.Bar;
import org.ta4j.core.Decimal;

import java.time.Duration;

public class PriceChange {
    private Duration duration;
    private Decimal priceChange;
    private Decimal percentageChange;

    public PriceChange(Duration duration, Decimal priceChange, Decimal percentageChange) {
        this.duration = duration;
        this.priceChange = priceChange;
        this.percentageChange = percentageChange;
    }

    public Decimal getPriceChange() {
        return priceChange;
    }

    public Duration getDuration() {
        return duration;
    }

    public Decimal getPercentageChange() {
        return percentageChange;
    }
}
