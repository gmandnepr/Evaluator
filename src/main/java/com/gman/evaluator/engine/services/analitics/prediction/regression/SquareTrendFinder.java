package com.gman.evaluator.engine.services.analitics.prediction.regression;

/**
 * @author gman
 * @since 02.12.12 18:05
 */
public class SquareTrendFinder implements TrendFinder {

    public Trend find(double[] source) {

        final int size = source.length;

        double min = Double.MAX_VALUE;
        double max = -Double.MAX_VALUE;

        double x0 = size * 1.0;
        double x1 = 0.0;
        double x2 = 0.0;
        double x3 = 0.0;
        double x4 = 0.0;
        double y = 0.0;
        double xy = 0.0;
        double xxy = 0.0;

        for (int i = 0; i < size; i++) {
            final double nx = i;
            final double ny = source[i];
            min = Math.min(min, nx);
            max = Math.max(max, nx);
            x4 += Math.pow(nx, 4.0);
            x3 += Math.pow(nx, 3.0);
            x2 += Math.pow(nx, 2.0);
            x1 += nx;
            y += ny;
            xy += nx * ny;
            xxy += Math.pow(nx, 2.0) * ny;
        }
        final double delta = x4 * x2 * x0 + 2 * x3 * x1 * x2 - x2 * x2 * x2 - x1 * x1 * x4 - x3 * x3 * x0;
        final double a = (x4 * x2 * y + x3 * x1 * xxy + x2 * x3 * xy - x2 * x2 * xxy - x1 * xy * x4 - y * x3 * x3) / delta;
        final double b = (x4 * xy * x0 + x3 * y * x2 + x2 * xxy * x1 - x2 * xy * x2 - x4 * y * x1 - x3 * xxy * x0) / delta;
        final double c = (xxy * x2 * x0 + xy * x1 * x2 + y * x3 * x1 - y * x2 * x2 - xxy * x1 * x1 - xy * x3 * x0) / delta;

        final FunctionWithParameters functionWithParameters = new FunctionWithParameters(min, max, new double[]{a, b, c}) {
            @Override
            public double getValue(int x) {
                return getCoefficients()[0] + getCoefficients()[1] * x + getCoefficients()[2] * x * x;
            }
        };
        final double dispersion = RegressionUtils.countDispersion(source, functionWithParameters);

        return new Trend("a+b*t+c*t^2", functionWithParameters, dispersion);
    }
}
