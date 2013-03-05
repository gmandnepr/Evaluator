package com.gman.evaluator.engine;

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
