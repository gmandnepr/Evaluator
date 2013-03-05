package com.gman.evaluator.engine.fields.autoria;

import com.gman.evaluator.engine.fields.NumericFieldType;

/**
 * @author gman
 * @since 22.12.12 23:08
 */
public class FormattedDistanceFieldType extends NumericFieldType {

    private static final int SUFFIX_SIZE = " тыс.км".length();

    @Override
    protected double parse(Object value) {
        final String trimmed = value.toString().trim();
        return Double.parseDouble(trimmed.substring(0, trimmed.length() - SUFFIX_SIZE)) * 1000.0;
    }
}
