package com.gman.evaluator.engine.rules;

import com.gman.evaluator.engine.FieldType;

/**
 * @author gman
 * @since 15.12.12 15:29
 */
public interface Operation {

    String getName();

    boolean accept(FieldType fieldType);

    boolean apply(Object fieldValue, Object operand);

    Object extractOperand(String operandDefinition);
}
