package com.gman.evaluator.engine.fields;

import com.gman.evaluator.engine.FieldType;

import java.io.Serializable;

/**
 * @author gman
 * @since 10.12.12 20:58
 */
public abstract class AbstractFieldType implements FieldType, Serializable {

    @Override
    public void setProperties(String properties) {
    }
}
