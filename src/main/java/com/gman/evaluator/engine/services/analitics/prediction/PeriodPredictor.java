package com.gman.evaluator.engine.services.analitics.prediction;

import com.gman.evaluator.engine.services.analitics.Period;

import java.util.List;

/**
 * @author gman
 * @since 09.03.13 11:05
 */
public interface PeriodPredictor {

    List<Period> predict(List<Period> history);
}
