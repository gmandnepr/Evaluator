package com.gman.evaluator.engine.rules.operations;

/**
 * @author gman
 * @since 15.12.12 15:36
 */
public class LessOperation extends AbstractNumericOperation {

    public LessOperation() {
        super("<");
    }

    @Override
    public boolean apply(Object fieldValue, Object operand) {
        return Double.compare((Double) fieldValue, (Double) operand) < 0;
    }
}
