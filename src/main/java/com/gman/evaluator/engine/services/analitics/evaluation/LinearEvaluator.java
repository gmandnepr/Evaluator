package com.gman.evaluator.engine.services.analitics.evaluation;

import com.gman.evaluator.engine.Evaluation;
import com.gman.evaluator.engine.Item;
import com.gman.evaluator.engine.Items;
import com.gman.evaluator.engine.Matrix;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author gman
 * @since 06.03.13 19:41
 */
public class LinearEvaluator implements Evaluator {

    @Override
    public Evaluation evaluate(Items items) {
        final List<String> extractedProperties = new ArrayList<String>();
        final Matrix x = createMatrixX(items, extractedProperties);
        final Matrix y = createMatrixY(items);
        final Matrix a = countRegressionCoefficients(x, y);
        return extractEvaluation(a, extractedProperties);
    }

    private Matrix createMatrixX(Items processing, List<String> extractedProperties) {
        final Set<String> combinedProperties = new HashSet<String>();
        for (Item item : processing) {
            combinedProperties.addAll(item.getDeclaredProperties());
        }
        extractedProperties.addAll(combinedProperties);
        final int propertiesNum = extractedProperties.size();
        final Matrix x = new Matrix(processing.size(), 1 + propertiesNum);
        int i = 0;
        for (Item item : processing) {
            x.setElement(i, 0, 1.0);
            for (int p = 0; p < propertiesNum; p++) {
                x.setElement(i, p + 1, item.getProperty(extractedProperties.get(p)));
            }
            i++;
        }
        return x;
    }

    private Matrix createMatrixY(Items processing) {
        final Matrix y = new Matrix(processing.size(), 1);
        int i = 0;
        for (Item item : processing) {
            y.setElement(i, 0, item.getPrice());
            i++;
        }
        return y;
    }

    private Matrix countRegressionCoefficients(Matrix x, Matrix y) {
        final Matrix xt = x.transpose();
        final Matrix xtx = xt.mult(x);
        final Matrix xty = xt.mult(y);
        return xtx.reverse().mult(xty);
    }

    private Evaluation extractEvaluation(Matrix a, List<String> extractedProperties) {
        final List<String> properties = new ArrayList<String>();
        properties.add(Evaluation.BASE);
        properties.addAll(extractedProperties);
        final Evaluation evaluation = new Evaluation() {

            @Override
            public double countPriceFor(Item item) {
                double itemPrice = 0.0;
                for (Map.Entry<String, Double> price : coefficients.entrySet()) {
                    if (price.getKey().equals(BASE)) {
                        itemPrice += price.getValue();
                    } else {
                        itemPrice += item.getProperty(price.getKey()) * price.getValue();
                    }
                }
                return itemPrice;
            }

            @Override
            public double countProfitFor(Item item) {
                double profit = -item.getPrice();
                for (Map.Entry<String, Double> price : coefficients.entrySet()) {
                    if (price.getKey().equals(BASE)) {
                        profit += price.getValue();
                    } else {
                        profit += item.getProperty(price.getKey()) * price.getValue();
                    }
                }
                return profit;
            }
        };
        for (int i = 0; i < properties.size(); i++) {
            evaluation.addPrice(properties.get(i), a.getElement(i, 0));
        }
        return evaluation;
    }
}
