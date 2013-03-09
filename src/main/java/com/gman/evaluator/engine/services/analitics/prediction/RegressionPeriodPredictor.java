package com.gman.evaluator.engine.services.analitics.prediction;

import com.gman.evaluator.engine.Items;
import com.gman.evaluator.engine.services.analitics.Period;
import com.gman.evaluator.engine.services.analitics.prediction.regression.RegressionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gman
 * @since 09.03.13 11:06
 */
public class RegressionPeriodPredictor implements PeriodPredictor {

    @Override
    public List<Period> predict(List<Period> history) {

        final int size = history.size() - 1;
        final double[] price = new double[size];
        final double[] offers = new double[size];
        final double[] starts = new double[size];
        final double[] ends = new double[size];
        for (int i = 0; i < size; i++) {
            starts[i] = history.get(i).getStartTime();
            ends[i] = history.get(i).getEndTime();
            price[i] = history.get(i).getSamplePrice();
            offers[i] = history.get(i).getOffers();
        }

        final double[] predictedStarts = RegressionUtils.predict(starts, PERIOD_TO_PREDICT);
        final double[] predictedEnds = RegressionUtils.predict(ends, PERIOD_TO_PREDICT);
        final double[] predictedPrice = RegressionUtils.predict(price, PERIOD_TO_PREDICT);
        final double[] predictedOffers = RegressionUtils.predict(offers, PERIOD_TO_PREDICT);


        final List<Period> prediction = new ArrayList<Period>();
        for (int i = 0; i < PERIOD_TO_PREDICT; i++) {
            final Period period = new Period(Math.round(predictedStarts[i]), Math.round(predictedEnds[i]),
                    Items.EMPTY, (int) Math.round(predictedOffers[i]));
            period.setSamplePrice(predictedPrice[i]);
            period.setOffers((int) Math.round(predictedOffers[i]));
            prediction.add(period);
        }
        return prediction;
    }
}
