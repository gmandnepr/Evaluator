package com.gman.evaluator.engine.fields;

/**
 * @author gman
 * @since 15.12.12 15:44
 */
public abstract class TextFieldType extends AbstractFieldType {

    @Override
    public Class<?> type() {
        return String.class;
    }

    protected String extractString(Object value) {
        return value.toString().trim();
    }
}
