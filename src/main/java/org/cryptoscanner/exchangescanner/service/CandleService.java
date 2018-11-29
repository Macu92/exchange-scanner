package org.cryptoscanner.exchangescanner.service;

import org.cryptoscanner.exchangescanner.adapter.PeriodAdapter;
import org.cryptoscanner.exchangescanner.model.Market;
import org.springframework.stereotype.Service;
import org.ta4j.core.BaseBar;
import org.ta4j.core.Decimal;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class CandleService {

    public BaseBar getActualCandle(PeriodAdapter.Period period) {
        Timestamp todayMidnight = Timestamp.valueOf(LocalDateTime.now().withHour(0).withSecond(0).withMinute(0).withNano(0));
        Long durationInNanos = PeriodAdapter.getDuration(period).getSeconds() * 1000;

        Long multiplicityOfPeriodToday = Math.floorDiv(
                Timestamp.valueOf(LocalDateTime.now()).getTime() - todayMidnight.getTime(),
                durationInNanos);
        Long barTodayEndTimeInNanos = (multiplicityOfPeriodToday * durationInNanos) + durationInNanos;

        todayMidnight.setTime(todayMidnight.getTime() + barTodayEndTimeInNanos);
        LocalDateTime barEndDate = todayMidnight.toLocalDateTime();

        return new BaseBar(PeriodAdapter.getDuration(period), ZonedDateTime.of(barEndDate, ZoneId.systemDefault()));
    }

    public void buildActualCandle(double price, double volume, Market market) {
        if (market.getActualCandle() == null) {
            market.setActualCandle(new BaseBar(PeriodAdapter.getDuration(market.getPeriod()),
                    ZonedDateTime.of(LocalDateTime.now().withNano(0).withSecond(0).plusMinutes(PeriodAdapter.getDuration(market.getPeriod()).toMinutes()), ZoneId.systemDefault())));
            market.addCandle(market.getActualCandle());
        } else if (market.getActualCandle().getEndTime().withSecond(0).withNano(0).isBefore(
                ZonedDateTime.now(market.getActualCandle().getEndTime().getZone()).withSecond(0).withNano(0)) && market.getActualCandle() != null) {
            market.setActualCandle(new BaseBar(PeriodAdapter.getDuration(market.getPeriod()),
                    ZonedDateTime.of(LocalDateTime.now().withNano(0).withSecond(0).plusMinutes(PeriodAdapter.getDuration(market.getPeriod()).toMinutes()), ZoneId.systemDefault())));
            market.addCandle(market.getActualCandle());
        }
        market.getActualCandle().addTrade(Decimal.valueOf(volume), Decimal.valueOf(price));
    }

    public void build1mActualCandle(double price, double volume, Market market) {
        if (market.getActualCandle1m() == null) {
            market.setActualCandle(new BaseBar(PeriodAdapter.getDuration(PeriodAdapter.Period._1m),
                    ZonedDateTime.of(LocalDateTime.now().withNano(0).withSecond(0), ZoneId.systemDefault())));
            market.addCandle1m(market.getActualCandle1m());
        } else if (market.getActualCandle1m().getEndTime().withSecond(0).withNano(0).isBefore(
                ZonedDateTime.now(market.getActualCandle1m().getEndTime().getZone()).withSecond(0).withNano(0)) && market.getActualCandle1m() != null) {
            market.setActualCandle(new BaseBar(PeriodAdapter.getDuration(PeriodAdapter.Period._1m),
                    ZonedDateTime.of(LocalDateTime.now().withNano(0).withSecond(0), ZoneId.systemDefault())));
            market.addCandle1m(market.getActualCandle1m());
        }
        market.getActualCandle1m().addTrade(Decimal.valueOf(volume), Decimal.valueOf(price));
    }
}
