package com.gman.evaluator.engine.rules.operations;

import com.gman.evaluator.engine.fields.NumericFieldType;

/**
 * @author gman
 * @since 15.12.12 18:03
 */
public abstract class AbstractNumericOperation extends AbstractOperation {

    public AbstractNumericOperation(String name) {
        super(name, NumericFieldType.class);
    }

    @Override
    public Object extractOperand(String operandDefinition) {
        return Double.parseDouble(operandDefinition);
    }
}
