package com.gman.evaluator.engine;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author gman
 * @since 01.12.12 20:11
 */
public class Evaluation {

    public static final String BASE = "base";

    private final Map<String, Double> prices = new HashMap<String, Double>();

    public void addPrice(String field, double price) {
        this.prices.put(field, price);
    }

    public Double getPrice(String field) {
        return this.prices.get(field);
    }

    public Set<Map.Entry<String, Double>> getFieldsAndPrices() {
        return this.prices.entrySet();
    }

    public double countPriceFor(Item item) {
        double itemPrice = 0.0;
        for (Map.Entry<String, Double> price : prices.entrySet()) {
            if (price.getKey().equals(BASE)) {
                itemPrice += price.getValue();
            } else {
                itemPrice += item.getProperty(price.getKey()) * price.getValue();
            }
        }
        return itemPrice;
    }

    public double countProfitFor(Item item) {
        double profit = -item.getPrice();
        for (Map.Entry<String, Double> price : prices.entrySet()) {
            if (price.getKey().equals(BASE)) {
                profit += price.getValue();
            } else {
                profit += item.getProperty(price.getKey()) * price.getValue();
            }
        }
        return profit;
    }
}
