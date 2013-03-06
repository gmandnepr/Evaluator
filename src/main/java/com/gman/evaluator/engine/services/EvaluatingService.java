package com.gman.evaluator.engine.services;

import com.gman.evaluator.engine.DataHolder;
import com.gman.evaluator.engine.Evaluation;
import com.gman.evaluator.engine.Item;
import com.gman.evaluator.engine.Items;
import com.gman.evaluator.engine.Matrix;
import com.gman.evaluator.engine.services.analitics.evaluation.Evaluator;
import com.gman.evaluator.engine.services.analitics.evaluation.LinearEvaluator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author gman
 * @since 11/30/12 9:40 AM
 */
public class EvaluatingService extends AbstractService<Evaluation> {

    private final DataHolder<Items> items;

    private final Evaluator evaluator = new LinearEvaluator();

    public EvaluatingService(DataHolder<Items> items) {
        super("Evaluating service");
        this.items = items;
    }

    @Override
    public Evaluation call() {
        final Items processing = items.get();
        if (processing.isEmpty()) {
            throw new IllegalStateException("Nothing to evaluate!");
        }

        final Evaluation evaluation = evaluator.evaluate(processing);

        callback.processed("Done", 100);
        return evaluation;
    }
}
