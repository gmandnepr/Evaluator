package com.gman.evaluator.engine.services.analitics.prediction.regression;

/**
 * @author gman
 * @since 11/6/12 11:00 AM
 */
public abstract class FunctionWithParameters {

    private final double from;
    private final double to;
    private final double[] coefficients;

    public FunctionWithParameters(double from, double to, double[] coefficients) {
        this.from = from;
        this.to = to;
        this.coefficients = coefficients;
    }

    public double getFrom() {
        return from;
    }

    public double getTo() {
        return to;
    }

    public double[] getCoefficients() {
        return coefficients;
    }

    public abstract double getValue(int x);
}
