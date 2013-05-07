package com.gman.evaluator.engine.services.analitics.evaluation;

/**
 * @author gman
 * @since 06.03.13 19:48
 */
public enum EvaluatorFactory {

    LINEAR(new LinearEvaluator()),
    POLINOM(new PolinomEvaluator("distance", 0.5, "age", 1.2, "engine", 1.0));

    private final Evaluator impl;

    private EvaluatorFactory(Evaluator impl) {
        this.impl = impl;
    }

    public Evaluator create() {
        return impl;
    }
}
