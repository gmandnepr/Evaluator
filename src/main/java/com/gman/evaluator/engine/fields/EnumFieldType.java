package com.gman.evaluator.engine.fields;

import com.gman.evaluator.engine.Item;
import com.gman.evaluator.engine.ItemImpl;

/**
 * @author gman
 * @since 10.12.12 21:05
 */
public class EnumFieldType extends AbstractFieldType {

    private static final double ATTRIBUTE_SET = 1.0;

    @Override
    public Class<?> type() {
        return String.class;
    }

    @Override
    public Object retrieve(Item item, String propertyName) {
        for (final String p : item.getDeclaredProperties()) {
            if (p.startsWith(propertyName) && item.getProperty(p) == ATTRIBUTE_SET) {
                return p.substring(p.indexOf('_') + 1);
            }
        }
        return "";
    }

    @Override
    public void inject(ItemImpl item, String propertyName, Object value) {
        final String attributeName = propertyName + '_' + value.toString();
        item.setProperty(attributeName, ATTRIBUTE_SET);
    }
}
