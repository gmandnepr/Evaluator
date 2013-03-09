package com.gman.evaluator.engine.services.analitics.abnomal;

import com.gman.evaluator.engine.FieldDefinition;
import com.gman.evaluator.engine.FieldType;
import com.gman.evaluator.engine.Item;
import com.gman.evaluator.engine.Items;

import java.util.Collections;
import java.util.Comparator;
import java.util.Set;

/**
 * @author gman
 * @since 09.03.13 11:02
 */
public class DefaultAbnormalRemover implements AbnormalRemover {

    private static final String AGE_PROPERTY = "age";

    private static final double DEFAULT_ABNORMAL_DIFFERENCE = 0.25;

    private final double abnormalDifference;

    public DefaultAbnormalRemover() {
        this(DEFAULT_ABNORMAL_DIFFERENCE);
    }

    public DefaultAbnormalRemover(double abnormalDifference) {
        this.abnormalDifference = abnormalDifference;
    }

    @Override
    public Items remove(Items items) {

        Items processing = items.copyAll();

        final Set<FieldDefinition> fields = items.getRegisteredFields();

        for (FieldDefinition field : fields) {

            final FieldType fieldType = field.getFieldType();

            if (fieldType.type().equals(Double.class)) {
                final String property = field.getName();

                Collections.sort(processing, new PropertyComparator(fieldType, property));

                final int abnormalStartIndex = findStartAbnormalIndex(processing, fieldType, property);
                final int abnormalEndIndex = findEndAbnormalIndex(processing, fieldType, property);

                if (abnormalStartIndex != 0 || abnormalEndIndex != processing.size() - 1) {
                    //abnormal found
                    final Items filtered = processing.copyStructure();
                    filtered.addAll(processing.subList(abnormalStartIndex, abnormalEndIndex));
                    processing = filtered;
                }
            }
        }

        return processing;
    }

    private int findStartAbnormalIndex(Items processing, FieldType fieldType, String property) {
        for (int i = processing.size() / 2; i > 0; i--) {
            final Item o1 = processing.get(i - 1);
            final Item o2 = processing.get(i);
            if (tooBigDifference(o1, o2, fieldType, property)) {
                return i;
            }
        }
        return 0;
    }

    private int findEndAbnormalIndex(Items processing, FieldType fieldType, String property) {
        for (int i = processing.size() / 2; i < processing.size() - 1; i++) {
            final Item o1 = processing.get(i);
            final Item o2 = processing.get(i + 1);
            if (tooBigDifference(o1, o2, fieldType, property)) {
                return i;
            }
        }
        return processing.size() - 1;
    }

    private boolean tooBigDifference(Item o1, Item o2, FieldType fieldType, String propertyName) {
        final double v1 = (Double) fieldType.retrieve(o1, propertyName);
        final double v2 = (Double) fieldType.retrieve(o2, propertyName);

        final double difference = (v2 - v1) / v2;

        final boolean result = !Double.isNaN(difference) && difference > abnormalDifference;

        if (AGE_PROPERTY.equals(propertyName)) {
            return v1 >= 5 && result;
        }

        return result;
    }

    private static final class PropertyComparator implements Comparator<Item> {

        private final FieldType fieldType;
        private final String property;

        private PropertyComparator(FieldType fieldType, String property) {
            this.fieldType = fieldType;
            this.property = property;
        }

        @Override
        public int compare(Item o1, Item o2) {
            final double v1 = (Double) fieldType.retrieve(o1, property);
            final double v2 = (Double) fieldType.retrieve(o2, property);

            return Double.compare(v1, v2);
        }
    }
}
