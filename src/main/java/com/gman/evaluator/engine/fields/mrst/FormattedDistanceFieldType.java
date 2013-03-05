package com.gman.evaluator.engine.fields.mrst;

import com.gman.evaluator.engine.fields.NumericFieldType;

/**
 * @author gman
 * @since 22.12.12 23:08
 */
public class FormattedDistanceFieldType extends NumericFieldType {

    @Override
    protected double parse(Object value) {
        final boolean containsThousand = value.toString().contains("т");
        double val = Double.parseDouble(value.toString().replace("т", ""));
        if (containsThousand) {
            val *= 1000;
        }
        return val;
    }
}
