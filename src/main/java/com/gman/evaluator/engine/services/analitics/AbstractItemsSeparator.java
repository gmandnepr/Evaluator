package com.gman.evaluator.engine.services.analitics;

import com.gman.evaluator.engine.Items;

import java.util.Date;

/**
 * @author gman
 * @since 04.03.13 21:48
 */
public abstract class AbstractItemsSeparator implements ItemsSeparator {

    protected Period extractPeriod(Items items) {

        Date first = items.get(0).getDate();
        Date last = items.get(0).getDate();

        for (int i = 1; i < items.size(); i++) {
            final Date d = items.get(i).getDate();
            if (first.after(d)) {
                first = d;
            }
            if (last.before(d)) {
                last = d;
            }
        }

        return new Period(first, last, items);
    }
}
