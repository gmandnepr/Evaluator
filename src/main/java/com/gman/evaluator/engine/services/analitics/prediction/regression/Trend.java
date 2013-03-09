package com.gman.evaluator.engine.services.analitics.prediction.regression;

/**
 * @author gman
 * @since 11.11.12 15:34
 */
public class Trend {

    private final String formula;
    private final FunctionWithParameters function;
    private final double dispersion;

    public Trend(String formula, FunctionWithParameters function, double dispersion) {
        this.formula = formula;
        this.function = function;
        this.dispersion = dispersion;
    }

    public String getFormula() {
        return formula;
    }

    public FunctionWithParameters getFunction() {
        return function;
    }

    public double getDispersion() {
        return dispersion;
    }
}
