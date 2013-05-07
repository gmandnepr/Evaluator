package com.gman.evaluator.engine.services.analitics.evaluation;

import com.gman.evaluator.engine.Evaluation;
import com.gman.evaluator.engine.Item;
import com.gman.evaluator.engine.Items;
import com.gman.evaluator.engine.Matrix;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author gman
 * @since 02.05.13 20:30
 */
public class PolinomEvaluator implements Evaluator {

    private final Map<String, Double> propertyStrength = new LinkedHashMap<String, Double>();

    public PolinomEvaluator() {
    }

    public PolinomEvaluator(String property0, double strength0,
                            String property1, double strength1,
                            String property2, double strength2) {
        propertyStrength.put(property0, strength0);
        propertyStrength.put(property1, strength1);
        propertyStrength.put(property2, strength2);
    }

    @Override
    public Evaluation evaluate(Items items) {
        final List<String> extractedProperties = extractProperties(items);
        checkAcceptableModel(extractedProperties);
        final Matrix x = createMatrixX(items, extractedProperties);
        final Matrix y = createMatrixY(items);
        final Matrix a = countRegressionCoefficients(x, y);
        return extractEvaluation(a, extractedProperties);
    }

    public Map<String, Double> getPropertyStrength() {
        return propertyStrength;
    }

    private void checkAcceptableModel(List<String> extractedProperties) {

        if (!propertyStrength.keySet().containsAll(extractedProperties)) {
            throw new IllegalArgumentException("Wrong model");
        }
    }

    private List<String> extractProperties(Items processing) {
        final Set<String> combinedProperties = new HashSet<String>();
        for (Item item : processing) {
            combinedProperties.addAll(item.getDeclaredProperties());
        }
        return new ArrayList<String>(combinedProperties);
    }

    private Matrix createMatrixX(Items processing, List<String> extractedProperties) {

        final int propertiesNum = extractedProperties.size();
        final Matrix x = new Matrix(processing.size(), 1 + propertiesNum);
        int i = 0;
        for (Item item : processing) {
            x.setElement(i, 0, 1.0);
            for (int p = 0; p < propertiesNum; p++) {
                final String property = extractedProperties.get(p);
                final double value = item.getProperty(property);
                final double strength = propertyStrength.get(property);

                x.setElement(i, p + 1, Math.pow(value, strength));
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
                        final String property = price.getKey();
                        final double value = item.getProperty(property);
                        final double strength = propertyStrength.get(property);

                        itemPrice += Math.pow(value, strength) * price.getValue();
                    }
                }
                return itemPrice;
            }
        };
        for (int i = 0; i < properties.size(); i++) {
            evaluation.addPrice(properties.get(i), a.getElement(i, 0));
        }
        return evaluation;
    }

    @Override
    public String toString() {
        return PolinomEvaluator.class.getName() + ": " + propertyStrength.toString();
    }
}
