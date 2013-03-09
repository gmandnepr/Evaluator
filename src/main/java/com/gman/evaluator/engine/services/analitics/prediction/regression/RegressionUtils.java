package com.gman.evaluator.engine.services.analitics.prediction.regression;

import java.util.Arrays;
import java.util.List;

/**
 * @author gman
 * @since 09.03.13 16:00
 */
public final class RegressionUtils {

    private RegressionUtils() {
    }

    public static double countDispersion(double[] source, FunctionWithParameters func) {
        double dispersion = 0.0;
        for (int i = 0; i < source.length; i++) {
            dispersion += Math.pow(source[i] - func.getValue(i), 2);
        }
        return dispersion;
    }

    public static Trend findBestTrend(List<Trend> trends) {
        Trend best = null;
        for (Trend trend : trends) {
            if (best == null || trend.getDispersion() < best.getDispersion()) {
                best = trend;
            }
        }
        return best;
    }

    public static Trend findBestTrend(Trend... trends) {
        return findBestTrend(Arrays.<Trend>asList(trends));
    }

    public static double[] removeTrend(double[] source, Trend trend) {
        final double[] sourceNoTrend = new double[source.length];
        for (int i = 0; i < sourceNoTrend.length; i++) {
            sourceNoTrend[i] = source[i] - trend.getFunction().getValue(i);
        }
        return sourceNoTrend;
    }

    public static double countTrend(Trend trend, int step) {
        return trend.getFunction().getValue(step);
    }

    public static double countSeasonality(Fourier fourier, int step) {
        return fourier.count(step, 3);
    }

    public static double[] predict(double[] source, int periodToPredict) {

        final Trend linear = LINEAR.find(source);
        final Trend exp = EXP.find(source);
        final Trend square = SQUARE.find(source);
        final Trend selected = RegressionUtils.findBestTrend(linear, exp, square);

        final double[] noTrendSource = RegressionUtils.removeTrend(source, selected);

        final Fourier fourier = Fourier.countCoefficients(noTrendSource, SEASON_PERIOD);

        final double[] prediction = new double[periodToPredict];

        for (int i = 0; i < periodToPredict; i++) {
            final int next = source.length + i;
            prediction[i] = RegressionUtils.countTrend(selected, next) + RegressionUtils.countSeasonality(fourier, next);
        }

        return prediction;
    }

    private static final int SEASON_PERIOD = 12;

    private static final TrendFinder LINEAR = new LinearTrendFinder();
    private static final TrendFinder EXP = new ExprTrendFinder();
    private static final TrendFinder SQUARE = new SquareTrendFinder();
}
