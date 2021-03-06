package com.gman.evaluator.engine.rules.operations;

/**
 * @author gman
 * @since 15.12.12 15:42
 */
public class LessOrEqualsOperation extends AbstractNumericOperation {

    public LessOrEqualsOperation() {
        super("<=");
    }

    @Override
    public boolean apply(Object fieldValue, Object operand) {
        return Double.compare((Double) fieldValue, (Double) operand) <= 0;
    }
}
