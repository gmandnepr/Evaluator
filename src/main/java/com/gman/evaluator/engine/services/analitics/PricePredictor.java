package com.gman.evaluator.engine.services.analitics;

/**
 * @author gman
 * @since 2/18/13 2:57 PM
 */
public interface PricePredictor {

    PriceTrend predict(PriceTrend trend);
}
