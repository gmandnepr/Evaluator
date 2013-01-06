package com.gman.evaluator.engine;

import com.gman.evaluator.engine.fields.AgeFieldType;
import com.gman.evaluator.engine.fields.AttributeFieldType;
import com.gman.evaluator.engine.fields.DateFieldType;
import com.gman.evaluator.engine.fields.EnumFieldType;
import com.gman.evaluator.engine.fields.LinkFieldType;
import com.gman.evaluator.engine.fields.NameFieldType;
import com.gman.evaluator.engine.fields.NumericFieldType;
import com.gman.evaluator.engine.fields.PriceFieldType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gman
 * @since 10.12.12 21:11
 */
public class FieldTypeFactory {

    public static FieldType create(String type, String properties) {
        try {
            final FieldType fieldType = (FieldType) Class.forName(type).newInstance();
            fieldType.setProperties(properties);
            return fieldType;
        } catch (Exception e) {
            throw new IllegalArgumentException("No such type!");
        }
    }
}
