package com.gman.evaluator.engine.services.analitics.prediction;

import com.gman.evaluator.engine.services.analitics.Period;

import java.util.List;

/**
 * @author gman
 * @since 09.03.13 11:05
 */
public interface PeriodPredictor {

    int PERIOD_TO_PREDICT = 6;

    List<Period> predict(List<Period> history);
}
