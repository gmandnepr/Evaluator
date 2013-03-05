package com.gman.evaluator.engine.services.analitics;

import java.util.List;

/**
 * @author gman
 * @since 04.03.13 22:12
 */
public class AnalyticsResult {

    private final List<Period> periods;

    public AnalyticsResult(List<Period> periods) {
        this.periods = periods;
    }

    public List<Period> getPeriods() {
        return periods;
    }
}
