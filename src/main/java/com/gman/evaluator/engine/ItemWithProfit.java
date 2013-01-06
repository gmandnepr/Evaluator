package com.gman.evaluator.engine;

import com.gman.evaluator.engine.fields.NumericFieldType;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author gman
 * @since 01.12.12 22:07
 */
public class ItemWithProfit implements Item {

    private static final long serialVersionUID = 1L;

    public static final String PROFIT = "profit";
    public static final FieldDefinition PROFIT_DEFINITION = new FieldDefinition(PROFIT, new NumericFieldType());

    private final Item item;
    private final double profit;

    public ItemWithProfit(Item item, double profit) {
        this.item = item;
        this.profit = profit;
    }

    @Override
    public String getLink() {
        return item.getLink();
    }

    @Override
    public String getName() {
        return item.getName();
    }

    @Override
    public double getPrice() {
        return item.getPrice();
    }

    @Override
    public Date getDate() {
        return item.getDate();
    }

    @Override
    public int getDeclaredPropertiesCount() {
        return item.getDeclaredPropertiesCount() + 1;
    }

    @Override
    public Set<String> getDeclaredProperties() {
        final Set<String> properties = new HashSet<String>(item.getDeclaredProperties());
        properties.add(PROFIT);
        return Collections.unmodifiableSet(properties);
    }

    @Override
    public double getProperty(String name) {
        if (PROFIT.equals(name)) {
            return profit;
        } else {
            return item.getProperty(name);
        }
    }

    @Override
    public boolean isEvaluable() {
        return item.isEvaluable();
    }
}
