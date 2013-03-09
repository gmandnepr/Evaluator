package com.gman.evaluator.engine.services.analitics.prediction;

/**
 * @author gman
 * @since 09.03.13 11:07
 */
public enum PeriodPredictorFactory {

    LINEAR(new RegressionPeriodPredictor());

    private final PeriodPredictor impl;

    private PeriodPredictorFactory(PeriodPredictor impl) {
        this.impl = impl;
    }

    public PeriodPredictor create() {
        return impl;
    }
}
