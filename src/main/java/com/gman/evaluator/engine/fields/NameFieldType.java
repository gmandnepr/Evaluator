package com.gman.evaluator.engine.fields;

import com.gman.evaluator.engine.Item;
import com.gman.evaluator.engine.ItemImpl;

/**
 * @author gman
 * @since 10.12.12 20:59
 */
public class NameFieldType extends TextFieldType {

    @Override
    public Object retrieve(Item item, String propertyName) {
        return item.getName();
    }

    @Override
    public void inject(ItemImpl item, String propertyName, Object value) {
        item.setName(extractString(value));
    }
}
