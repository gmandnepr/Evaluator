package com.gman.evaluator.engine.rules.operations;

import com.gman.evaluator.engine.fields.DateFieldType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author gman
 * @since 22.12.12 13:44
 */
public abstract class AbstractDateOperation extends AbstractOperation {

    private static final String FORMAT = DateFieldType.DEFAULT_DATE_FORMAT;

    protected AbstractDateOperation(String name) {
        super(name, DateFieldType.class);
    }

    @Override
    public Object extractOperand(String operandDefinition) {
        final SimpleDateFormat formatter = new SimpleDateFormat(FORMAT);
        try {
            return formatter.parse(operandDefinition);
        } catch (ParseException e) {
            return new Date();
        }
    }
}
