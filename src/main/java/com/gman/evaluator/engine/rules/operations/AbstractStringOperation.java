package com.gman.evaluator.engine.rules.operations;

import com.gman.evaluator.engine.FieldType;

/**
 * @author gman
 * @since 15.12.12 18:07
 */
public abstract class AbstractStringOperation extends AbstractOperation {

    public AbstractStringOperation(String name, Class<? extends FieldType> acceptableType) {
        super(name, acceptableType);
    }

    @Override
    public Object extractOperand(String operandDefinition) {
        return operandDefinition;
    }
}
