package com.gman.evaluator.engine;

/**
 * @author gman
 * @since 10.12.12 20:56
 */
public interface FieldType {

    void setProperties(String properties);

    Class<?> type();

    Object retrieve(Item item, String propertyName);

    void inject(ItemImpl item, String propertyName, Object value);
}
