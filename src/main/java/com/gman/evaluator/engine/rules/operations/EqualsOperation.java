package com.gman.evaluator.engine.rules.operations;

/**
 * @author gman
 * @since 15.12.12 15:40
 */
public class EqualsOperation extends AbstractNumericOperation {

    public EqualsOperation() {
        super("=");
    }

    @Override
    public boolean apply(Object fieldValue, Object operand) {
        return Double.compare((Double) fieldValue, (Double) operand) == 0;
    }
}
