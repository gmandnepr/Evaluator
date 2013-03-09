package com.gman.evaluator.engine.services.analitics.prediction.regression;

/**
 * @author gman
 * @since 11/13/12 11:32 AM
 */
public class Fourier {

    private final double a0;//A0
    private final double[] ak;//Ak
    private final double[] bk;//Bk
    private final int limit;//koefs size as size/2
    private final double l;//period
    private final int n;//initial row size

    public Fourier(double a0, double[] ak, double[] bk, int limit, double l, int n) {
        this.a0 = a0;
        this.ak = ak;
        this.bk = bk;
        this.limit = limit;
        this.l = l;
        this.n = n;
    }

    public double getA0() {
        return a0;
    }

    public double[] getAk() {
        return ak;
    }

    public double[] getBk() {
        return bk;
    }

    public int getLimit() {
        return limit;
    }

    public double getL() {
        return l;
    }

    public int getN() {
        return n;
    }

    public double count(double t) {
        return count(t, limit);
    }

    public double count(double t, int useCoefficient) {
        double value = a0;
        for (int k = 1; k < useCoefficient; k++) {
            final double arg = 2.0 * Math.PI * k * t / l;
            value += ak[k] * Math.cos(arg);
            value += bk[k] * Math.sin(arg);
        }
        return value;
    }

    public static Fourier countCoefficients(double[] seriesWithoutTrend, double l) {

        final int n = seriesWithoutTrend.length;
        final int limit = n / 2;

        double a0 = 0.0;
        for (int t = 0; t < n; t++) {
            a0 = seriesWithoutTrend[t];
        }
        a0 /= n;

        final double[] ak = new double[limit];
        final double[] bk = new double[limit];
        for (int k = 1; k < limit; k++) {
            final double arg = 2.0 * Math.PI * k / l;
            for (int t = 0; t < n; t++) {
                ak[k] += seriesWithoutTrend[t] * Math.cos(arg * t);
                bk[k] += seriesWithoutTrend[t] * Math.sin(arg * t);
            }
            ak[k] *= (2.0 / n);
            bk[k] *= (2.0 / n);
        }

        return new Fourier(a0, ak, bk, limit, l, n);
    }
}
