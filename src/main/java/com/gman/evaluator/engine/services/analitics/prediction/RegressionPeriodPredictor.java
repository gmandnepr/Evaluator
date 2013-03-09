package com.gman.evaluator.engine.services.analitics.prediction;

import com.gman.evaluator.engine.services.analitics.Period;

import java.util.Collections;
import java.util.List;

/**
 * @author gman
 * @since 09.03.13 11:06
 */
public class RegressionPeriodPredictor implements PeriodPredictor {
    @Override
    public List<Period> predict(List<Period> history) {
        return Collections.emptyList();
    }
}
