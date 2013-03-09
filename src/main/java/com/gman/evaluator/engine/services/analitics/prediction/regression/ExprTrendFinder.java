package com.gman.evaluator.engine.services.analitics.prediction.regression;

/**
 * @author gman
 * @since 11/16/12 10:04 AM
 */
public class ExprTrendFinder implements TrendFinder {

    public Trend find(double[] source) {

        final int size = source.length;

        double min = Double.MAX_VALUE;
        double max = -Double.MAX_VALUE;

        double x0 = size * 1.0;
        double x1 = 0.0;
        double x2 = 0.0;
        double y = 0.0;
        double xy = 0.0;

        for (int i = 0; i < size; i++) {
            final double nx = i;
            final double ny = Math.log(source[i]);
            min = Math.min(min, nx);
            max = Math.max(max, nx);
            x2 += nx * nx;
            x1 += nx;
            y += ny;
            xy += nx * ny;
        }
        final double delta = x2 * x0 - x1 * x1;
        final double a = (x2 * y - x1 * xy) / delta;
        final double b = (xy * x0 - y * x1) / delta;

        final FunctionWithParameters functionWithParameters = new FunctionWithParameters(min, max, new double[]{a, b}) {
            @Override
            public double getValue(int x) {
                return Math.exp(getCoefficients()[0] + getCoefficients()[1] * x);
            }
        };
        final double dispersion = RegressionUtils.countDispersion(source, functionWithParameters);

        return new Trend("a*e^b*t", functionWithParameters, dispersion);
    }
}
