package com.gman.evaluator.engine.fields.mrst;

import com.gman.evaluator.engine.fields.DateFieldType;

/**
 * @author gman
 * @since 23.12.12 12:13
 */
public class FormattedDateFieldType extends DateFieldType {

    private static final String[] PREFIXES = {
            "размещено ",
            "обновлено "
    };
    private static final String SUFFIX = ", премиум";

    @Override
    protected String extractDate(String text) {
        int begin = 0;
        int end = text.length();
        for (final String prefix : PREFIXES) {
            if (text.startsWith(prefix)) {
                begin = prefix.length();
                break;
            }
        }
        if (text.endsWith(SUFFIX)) {
            end -= SUFFIX.length();
        }
        return text.substring(begin, end);
    }
}
