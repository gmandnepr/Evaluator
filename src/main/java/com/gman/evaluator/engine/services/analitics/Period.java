package com.gman.evaluator.engine.services.analitics;

import com.gman.evaluator.engine.Evaluation;
import com.gman.evaluator.engine.Items;

/**
 * @author gman
 * @since 04.03.13 21:39
 */
public class Period {

    private final long startTime;
    private final long endTime;
    private final Items items;
    private int offers;
    private Evaluation evaluation;
    private double samplePrice;

    public Period(long startTime, long endTime, Items items, int offers) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.items = items;
        this.offers = offers;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public long getMiddleTime() {
        return startTime / 2 + endTime / 2;
    }

    public int getOffers() {
        return offers;
    }

    public void setOffers(int offers) {
        this.offers = offers;
    }

    public Items getItems() {
        return items;
    }

    public Evaluation getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }

    public double getSamplePrice() {
        return samplePrice;
    }

    public void setSamplePrice(double samplePrice) {
        this.samplePrice = samplePrice;
    }
}
