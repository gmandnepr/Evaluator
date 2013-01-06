package com.gman.evaluator.engine;

/**
 * @author gman
 * @since 23.12.12 11:13
 */
public enum Currency {

    USD(1.0),
    EUR(0.7539),
    UAH(0.1231);

    private double exchangeRate;

    private Currency(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
}
