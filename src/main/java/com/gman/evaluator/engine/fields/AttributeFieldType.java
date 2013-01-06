package com.gman.evaluator.engine.fields;

import com.gman.evaluator.engine.Item;
import com.gman.evaluator.engine.ItemImpl;

/**
 * @author gman
 * @since 10.12.12 21:06
 */
public class AttributeFieldType extends AbstractFieldType {

    public static final double ATTRIBUTE_SET = 1.0;
    public static final double ATTRIBUTE_NOT_SET = 0.0;

    private String textToFind = "";

    @Override
    public void setProperties(String properties) {
        textToFind = properties.toLowerCase();
    }

    @Override
    public Class<?> type() {
        return Boolean.class;
    }

    @Override
    public Object retrieve(Item item, String propertyName) {
        return item.getProperty(propertyName) == ATTRIBUTE_SET;
    }

    @Override
    public void inject(ItemImpl item, String propertyName, Object value) {
        item.setProperty(propertyName, value.toString().toLowerCase().contains(textToFind) ? ATTRIBUTE_SET : ATTRIBUTE_NOT_SET);
    }
}
