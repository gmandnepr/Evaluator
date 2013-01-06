package com.gman.evaluator.engine.rules.operations;

import com.gman.evaluator.engine.FieldType;

/**
 * @author gman
 * @since 15.12.12 18:09
 */
public abstract class AbstractNoOperandOperation extends AbstractOperation {

    public AbstractNoOperandOperation(String name, Class<? extends FieldType> acceptableType) {
        super(name, acceptableType);
    }

    @Override
    public Object extractOperand(String operandDefinition) {
        return null;
    }
}
