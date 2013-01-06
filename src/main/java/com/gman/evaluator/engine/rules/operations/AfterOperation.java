package com.gman.evaluator.engine.rules.operations;

import java.util.Date;

/**
 * @author gman
 * @since 22.12.12 13:44
 */
public class AfterOperation extends AbstractDateOperation {

    public AfterOperation() {
        super("After");
    }

    @Override
    public boolean apply(Object fieldValue, Object operand) {
        return ((Date) fieldValue).compareTo((Date) operand) > 0;
    }
}
