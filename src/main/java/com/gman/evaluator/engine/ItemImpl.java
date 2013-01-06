package com.gman.evaluator.engine;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Date;

/**
 * @author gman
 * @since 01.12.12 22:13
 */
public class ItemImpl implements Item {

    private static final long serialVersionUID = 1L;

    private boolean evaluable = true;

    private String link;
    private String name;
    private double price;
    private Date date;

    private Map<String, Double> properties = new HashMap<String, Double>();

    @Override
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        check(price);
        this.price = price;
    }

    @Override
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int getDeclaredPropertiesCount() {
        return properties.size();
    }

    @Override
    public Set<String> getDeclaredProperties() {
        return properties.keySet();
    }

    @Override
    public double getProperty(String name) {
        final Double value = properties.get(name);
        return value != null ? value : 0.0;
    }

    public void setProperty(String name, double value) {
        check(value);
        properties.put(name, value);
    }

    @Override
    public boolean isEvaluable() {
        return evaluable;
    }

    private void check(double p) {
        if (Double.isNaN(p)) {
            evaluable = false;
        }
    }
}
