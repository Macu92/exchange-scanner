package org.cryptoscanner.exchangescanner.adapter;

import java.time.Duration;

public interface PeriodAdapter {
    public enum Period {
        _1m,
        _5m,
//        _15m,
        _30m,
        _1h,
//        _4h,
        _1D
    }

    public static Duration getDuration(Period period) {
        switch (period) {
            case _1m:
                return Duration.ofMinutes(1);
            case _5m:
                return Duration.ofMinutes(5);
            case _30m:
                return Duration.ofMinutes(30);
            case _1h:
                return Duration.ofHours(1);
            case _1D:
                return Duration.ofDays(1);
            default:
                return Duration.ofHours(1);
        }
    }
}
