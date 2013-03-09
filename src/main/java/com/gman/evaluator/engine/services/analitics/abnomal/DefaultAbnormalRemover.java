package com.gman.evaluator.engine.services.analitics.abnomal;

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

        final Set<String> properties = processing.getDeclaredProperties();

        for (String property : properties) {

            Collections.sort(processing, new PropertyComparator(property));

            final int index = processing.size() / 2;

            int abnormalStartIndex = -1;
            for (int i = index; i > 0; i--) {
                final Item o1 = processing.get(i - 1);
                final Item o2 = processing.get(i);
                if (tooBigDifference(o1, o2, property)) {
                    abnormalStartIndex = i;
                    break;
                }
            }
            if (abnormalStartIndex == -1) {
                abnormalStartIndex = 0;
            }

            int abnormalEndIndex = -1;
            for (int i = index; i < processing.size() - 1; i++) {
                final Item o1 = processing.get(i);
                final Item o2 = processing.get(i + 1);
                if (tooBigDifference(o1, o2, property)) {
                    abnormalEndIndex = i;
                    break;
                }
            }
            if (abnormalEndIndex == -1) {
                abnormalEndIndex = processing.size() - 1;
            }

            if (abnormalStartIndex != 0 || abnormalEndIndex != processing.size() - 1) {
                //abnormal found
                final Items filtered = processing.copyStructure();
                filtered.addAll(processing.subList(abnormalStartIndex, abnormalEndIndex));
                processing = filtered;
            }
        }

        return processing;
    }

    private boolean tooBigDifference(Item o1, Item o2, String propertyName) {
        final double v1 = o1.getProperty(propertyName);
        final double v2 = o2.getProperty(propertyName);

        final double difference = (v2 - v1) / v2;

        final boolean result = !Double.isNaN(difference) && difference > abnormalDifference;

        if (AGE_PROPERTY.equals(propertyName)) {
            return v1 >= 5 && result;
        }

        return result;
    }

    private static final class PropertyComparator implements Comparator<Item> {

        private final String property;

        private PropertyComparator(String property) {
            this.property = property;
        }

        @Override
        public int compare(Item o1, Item o2) {
            return Double.compare(o1.getProperty(property), o2.getProperty(property));
        }
    }
}
