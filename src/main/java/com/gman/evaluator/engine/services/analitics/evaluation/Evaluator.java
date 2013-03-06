package com.gman.evaluator.engine.services.analitics.evaluation;

import com.gman.evaluator.engine.Evaluation;
import com.gman.evaluator.engine.Items;

/**
 * @author gman
 * @since 06.03.13 19:40
 */
public interface Evaluator {
    
    Evaluation evaluate(Items items);
}
