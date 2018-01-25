package org.cryptoscanner.exchangescanner.adapter;

import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.bittrex.BittrexExchange;

public enum EnumExchange {
    BITTREX(BittrexExchange.class.getName()),
    BINANCE(BinanceExchange.class.getName());

    private String className;

    private EnumExchange(String className) {
        this.className = className;
    }

    public String getExchangeName(){
        return this.className;
    }
}
