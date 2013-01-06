package com.gman.evaluator.engine.fields;

import com.gman.evaluator.engine.Item;
import com.gman.evaluator.engine.ItemImpl;

/**
 * @author gman
 * @since 10.12.12 21:04
 */
public class NumericFieldType extends AbstractFieldType {

    @Override
    public Class<?> type() {
        return Double.class;
    }

    @Override
    public Object retrieve(Item item, String propertyName) {
        return item.getProperty(propertyName);
    }

    @Override
    public void inject(ItemImpl item, String propertyName, Object value) {
        try {
            item.setProperty(propertyName, parse(value));
        } catch (NumberFormatException e) {
            item.setProperty(propertyName, Double.NaN);
        }
    }

    protected double parse(Object value) {
        return Double.parseDouble(value.toString());
    }
}
