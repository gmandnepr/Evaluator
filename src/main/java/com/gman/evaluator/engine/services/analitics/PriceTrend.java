package com.gman.evaluator.engine.services.analitics;

import com.gman.evaluator.engine.Evaluation;
import com.gman.evaluator.engine.Item;

import java.util.List;

/**
 * @author gman
 * @since 2/18/13 2:38 PM
 */
public class PriceTrend {

    private final double[] prices;

    public PriceTrend(double[] prices) {
        this.prices = prices;
    }

    public double getPrice(int i) {
        return prices[i];
    }

    public int getSize() {
        return prices.length;
    }

    public static PriceTrend create(Item sample, List<Evaluation> evaluations) {
        final double[] prices = new double[evaluations.size()];

        for (int i=0; i<prices.length; i++) {
            prices[i] = evaluations.get(i).countPriceFor(sample);
        }

        return new PriceTrend(prices);
    }
}
