package com.gman.evaluator.engine.rules.operations;

import com.gman.evaluator.engine.FieldType;

import java.util.Date;

/**
 * @author gman
 * @since 22.12.12 13:44
 */
public class BeforeOperation extends AbstractDateOperation {

    public BeforeOperation() {
        super("Before");
    }

    @Override
    public boolean apply(Object fieldValue, Object operand) {
        return ((Date) fieldValue).compareTo((Date) operand) < 0;
    }
}
