package com.gman.evaluator.engine.fields.autoria;

import com.gman.evaluator.engine.fields.NumericFieldType;

/**
 * @author gman
 * @since 22.12.12 23:16
 */
public class FormattedEngine extends NumericFieldType {

    private static final String PREFIX = "&nbsp;";
    private static final int SUFFIX_SIZE = " л".length();

    @Override
    protected double parse(Object value) {
        final String trimmed = value.toString().trim();
        final int start = trimmed.indexOf(PREFIX) + PREFIX.length();
        final int end = trimmed.length() - SUFFIX_SIZE;
        if (start < end) {
            return Double.parseDouble(trimmed.substring(start, end));
        } else {
            return 0.0;
        }
    }
}
