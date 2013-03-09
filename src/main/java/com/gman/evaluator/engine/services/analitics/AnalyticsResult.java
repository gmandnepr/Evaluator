package com.gman.evaluator.engine.services.analitics;

import java.util.Collections;
import java.util.List;

/**
 * @author gman
 * @since 04.03.13 22:12
 */
public class AnalyticsResult {

    private final List<Period> periods;
    private final List<Period> prediction;

    public AnalyticsResult(List<Period> periods, List<Period> prediction) {
        this.periods = Collections.unmodifiableList(periods);
        this.prediction = Collections.unmodifiableList(prediction);
    }

    public List<Period> getPeriods() {
        return periods;
    }

    public List<Period> getPrediction() {
        return prediction;
    }
}
