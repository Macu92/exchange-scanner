package org.cryptoscanner.exchangescanner.indicators;

import org.ta4j.core.Decimal;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.AbstractIndicator;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsLowerIndicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsMiddleIndicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsUpperIndicator;
import org.ta4j.core.indicators.statistics.StandardDeviationIndicator;

public class BollingerBandsPricePositionIndicator extends AbstractIndicator<Decimal>{

    private BollingerBandsLowerIndicator bollingerBandsLowerIndicator;
    private BollingerBandsMiddleIndicator bollingerBandsMiddleIndicator;
    private BollingerBandsUpperIndicator bollingerBandsUpperIndicator;

    public BollingerBandsPricePositionIndicator(SMAIndicator indicator, StandardDeviationIndicator standardDeviationIndicator) {
        super(indicator.getTimeSeries());
        this.bollingerBandsMiddleIndicator = new BollingerBandsMiddleIndicator(indicator);
        this.bollingerBandsLowerIndicator = new BollingerBandsLowerIndicator(this.bollingerBandsMiddleIndicator,standardDeviationIndicator);
        this.bollingerBandsUpperIndicator = new BollingerBandsUpperIndicator(this.bollingerBandsMiddleIndicator,standardDeviationIndicator);
    }


    @Override
    public Decimal getValue(int i) {
        Decimal price = this.getTimeSeries().getLastBar().getClosePrice();
        Decimal bbmValue = this.bollingerBandsMiddleIndicator.getValue(i);
        Decimal bbuValue = this.bollingerBandsUpperIndicator.getValue(i);
        Decimal bblValue = this.bollingerBandsLowerIndicator.getValue(i);
        System.out.println("Price: "+price+" BbmValue: "+bbmValue+" bbuValue "+bbuValue+" bblValue: "+bblValue);
        if(price.isGreaterThanOrEqual(bbmValue)){
            Decimal priceBand = price.minus(bbmValue);
            Decimal upperBand = bbuValue.minus(bbmValue);
            Decimal pricePosition = priceBand.dividedBy(upperBand);
            return pricePosition.multipliedBy(Decimal.valueOf(100));
        }else {
            Decimal priceBand = bbmValue.minus(price);
            Decimal lowerBand = bbmValue.minus(bblValue);
            Decimal pricePosition = priceBand.dividedBy(lowerBand);
            return pricePosition.multipliedBy(Decimal.valueOf(-100));
        }
    }
}
