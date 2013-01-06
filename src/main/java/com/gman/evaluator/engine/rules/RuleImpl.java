package com.gman.evaluator.engine.rules;

import com.gman.evaluator.engine.FieldDefinition;
import com.gman.evaluator.engine.Item;
import com.gman.evaluator.engine.Rule;

import java.io.Serializable;

/**
 * @author gman
 * @since 15.12.12 18:00
 */
public class RuleImpl implements Rule, Serializable {

    private final FieldDefinition field;
    private final Operation operation;
    private final String operand;
    private final Object convertedOperand;

    public RuleImpl(FieldDefinition field, Operation operation, String operand) {
        this.field = field;
        this.operation = operation;
        this.operand = operand;
        this.convertedOperand = operation.extractOperand(operand);
    }

    public FieldDefinition getField() {
        return field;
    }

    public Operation getOperation() {
        return operation;
    }

    public String getOperand() {
        return operand;
    }

    public Object getConvertedOperand() {
        return convertedOperand;
    }

    @Override
    public boolean satisfy(Item item) {
        final Object fieldValue = field.getFieldType().retrieve(item, field.getName());
        return operation.apply(fieldValue, convertedOperand);
    }

    @Override
    public String toString() {
        return new StringBuilder().
                append(field.getName()).
                append(' ').
                append(operation.getName()).
                append(' ').
                append(operand).toString();
    }
}
