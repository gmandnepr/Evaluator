package com.gman.evaluator.engine.services.analitics.prediction.regression;

/**
 * @author gman
 * @since 11.11.12 15:35
 */
public interface TrendFinder {

    Trend find(double[] source);
}
