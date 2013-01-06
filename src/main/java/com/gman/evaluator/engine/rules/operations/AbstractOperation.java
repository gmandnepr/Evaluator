package com.gman.evaluator.engine.rules.operations;

import com.gman.evaluator.engine.FieldType;
import com.gman.evaluator.engine.rules.Operation;

import java.io.Serializable;

/**
 * @author gman
 * @since 15.12.12 15:31
 */
public abstract class AbstractOperation implements Operation, Serializable {

    private final String name;
    private final Class<? extends FieldType> acceptableType;

    protected AbstractOperation(String name, Class<? extends FieldType> acceptableType) {
        this.name = name;
        this.acceptableType = acceptableType;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean accept(FieldType fieldType) {
        return acceptableType.isAssignableFrom(fieldType.getClass());
    }

    @Override
    public String toString() {
        return name;
    }
}
