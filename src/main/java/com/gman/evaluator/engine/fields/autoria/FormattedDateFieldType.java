package com.gman.evaluator.engine.fields.autoria;

import com.gman.evaluator.engine.fields.DateFieldType;

/**
 * @author gman
 * @since 23.12.12 11:32
 */
public class FormattedDateFieldType extends DateFieldType {

    private static final int PREFIX_LENGTH = "Объявление добавлено".length();

    @Override
    protected String extractDate(String text) {
        return text.substring(PREFIX_LENGTH);
    }
}
