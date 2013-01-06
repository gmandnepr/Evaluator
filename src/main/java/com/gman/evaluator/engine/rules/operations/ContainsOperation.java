package com.gman.evaluator.engine.rules.operations;

import com.gman.evaluator.engine.fields.TextFieldType;

/**
 * @author gman
 * @since 15.12.12 15:44
 */
public class ContainsOperation extends AbstractStringOperation {

    public ContainsOperation() {
        super("contains", TextFieldType.class);
    }

    @Override
    public boolean apply(Object fieldValue, Object operand) {
        return ((String) fieldValue).contains((String) operand);
    }
}
