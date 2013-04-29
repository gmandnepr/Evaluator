package com.gman.evaluator.engine;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author gman
 * @since 01.12.12 20:11
 */
public abstract class Evaluation {

    public static final String BASE = "base";

    protected final Map<String, Double> coefficients = new HashMap<String, Double>();

    public void addPrice(String field, double coefficient) {
        this.coefficients.put(field, coefficient);
    }

    public Double getCoefficient(String field) {
        return this.coefficients.get(field);
    }

    public Set<Map.Entry<String, Double>> getFieldsAndCoefficients() {
        return this.coefficients.entrySet();
    }

    public abstract double countPriceFor(Item item);

    public abstract double countProfitFor(Item item);

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Double> entry : getFieldsAndCoefficients()) {
            sb.append(entry.getKey()).append(": ").append(String.format("%.2f", entry.getValue())).append('\n');
        }
        return sb.toString();
    }
}
