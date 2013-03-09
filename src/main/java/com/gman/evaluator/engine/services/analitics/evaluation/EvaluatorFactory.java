package com.gman.evaluator.engine.services.analitics.evaluation;

/**
 * @author gman
 * @since 06.03.13 19:48
 */
public enum EvaluatorFactory {

    LINEAR(new LinearEvaluator());

    private final Evaluator impl;

    private EvaluatorFactory(Evaluator impl) {
        this.impl = impl;
    }

    public Evaluator create() {
        return impl;
    }
}
