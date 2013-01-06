package com.gman.evaluator.engine.rules.operations;

import com.gman.evaluator.engine.fields.EnumFieldType;

/**
 * @author gman
 * @since 15.12.12 15:46
 */
public class IsOperation extends AbstractStringOperation {

    public IsOperation() {
        super("is", EnumFieldType.class);
    }

    @Override
    public boolean apply(Object fieldValue, Object operand) {
        return ((String) fieldValue).equalsIgnoreCase((String) operand);
    }
}
