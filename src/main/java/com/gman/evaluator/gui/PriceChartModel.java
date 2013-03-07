package com.gman.evaluator.gui;

import com.gman.evaluator.engine.services.analitics.Period;
import org.jfree.data.xy.AbstractXYDataset;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gman
 * @since 07.03.13 22:19
 */
public class PriceChartModel extends AbstractXYDataset {

    private final List<Period> periods = new ArrayList<Period>();

    public void setPeriods(List<Period> periods) {
        this.periods.clear();
        this.periods.addAll(periods);
        fireDatasetChanged();
    }

    public void clear() {
        this.periods.clear();
        fireDatasetChanged();
    }

    @Override
    public int getSeriesCount() {
        return 1;
    }

    @Override
    public Comparable getSeriesKey(int series) {
        return "price";
    }

    @Override
    public int getItemCount(int series) {
        return this.periods.size();
    }

    @Override
    public Number getX(int series, int item) {
        return item;
    }

    @Override
    public Number getY(int series, int item) {
        return this.periods.get(item).getSamplePrice();
    }
}
