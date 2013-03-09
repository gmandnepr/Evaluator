package com.gman.evaluator.gui;

import com.gman.evaluator.engine.services.analitics.Period;
import org.jfree.data.xy.AbstractXYDataset;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gman
 * @since 07.03.13 22:18
 */
public class OffersChartModel extends AbstractXYDataset {

    private final List<Period> periods = new ArrayList<Period>();
    private final List<Period> predictions = new ArrayList<Period>();

    public void setPeriods(List<Period> periods, List<Period> predictions) {
        this.periods.clear();
        this.periods.addAll(periods);
        this.predictions.clear();
        this.predictions.addAll(predictions);
        fireDatasetChanged();
    }

    public void clear() {
        this.periods.clear();
        this.predictions.clear();
        fireDatasetChanged();
    }

    @Override
    public int getSeriesCount() {
        return 2;
    }

    @Override
    public Comparable getSeriesKey(int series) {
        switch (series) {
            case 0:
                return "offers";
            default:
                return "predicted";
        }
    }

    @Override
    public int getItemCount(int series) {
        switch (series) {
            case 0:
                return periods.size();
            default:
                return predictions.size();
        }
    }

    @Override
    public Number getX(int series, int item) {
        switch (series) {
            case 0:
                return periods.get(item).getMiddleTime();
            default:
                return predictions.get(item).getMiddleTime();
        }
    }

    @Override
    public Number getY(int series, int item) {
        switch (series) {
            case 0:
                return periods.get(item).getOffers();
            default:
                return predictions.get(item).getOffers();
        }
    }
}
