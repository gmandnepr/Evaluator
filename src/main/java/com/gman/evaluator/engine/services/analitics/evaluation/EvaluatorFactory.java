package com.gman.evaluator.engine.services.analitics.evaluation;

/**
 * @author gman
 * @since 06.03.13 19:48
 */
public enum EvaluatorFactory {

    LINEAR(LinearEvaluator.class);

    private final Class<? extends Evaluator> clazz;

    private EvaluatorFactory(Class<? extends Evaluator> clazz) {
        this.clazz = clazz;
    }

    public Evaluator create() {
        try {
            return this.clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
