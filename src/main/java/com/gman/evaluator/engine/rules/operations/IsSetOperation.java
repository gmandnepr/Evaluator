package com.gman.evaluator.engine.rules.operations;

import com.gman.evaluator.engine.fields.AttributeFieldType;

/**
 * @author gman
 * @since 15.12.12 15:46
 */
public class IsSetOperation extends AbstractNoOperandOperation {

    public IsSetOperation() {
        super("is set", AttributeFieldType.class);
    }

    @Override
    public boolean apply(Object fieldValue, Object operand) {
        return (Boolean) fieldValue;
    }
}
