package com.gman.evaluator.engine.fields;

import com.gman.evaluator.engine.Item;
import com.gman.evaluator.engine.ItemImpl;

import java.util.Calendar;

/**
 * @author gman
 * @since 10.12.12 21:03
 */
public class AgeFieldType extends NumericFieldType {

    private static final int CURRENT_YEAR = Calendar.getInstance().get(Calendar.YEAR);
    private static final String AGE = "age";

    @Override
    public Object retrieve(Item item, String propertyName) {
        return item.getProperty(AGE);
    }

    @Override
    public void inject(ItemImpl item, String propertyName, Object value) {
        final int age = CURRENT_YEAR - Integer.parseInt(value.toString());
        item.setProperty(AGE, age);
    }
}
