package com.gman.evaluator.engine.services.analitics;

import com.gman.evaluator.engine.Evaluation;
import com.gman.evaluator.engine.Items;

import java.util.Date;

/**
 * @author gman
 * @since 04.03.13 21:39
 */
public class Period {

    private final Date from;
    private final Date to;
    private final Items items;
    private Evaluation evaluation = null;
    private double samplePrice = 0;

    public Period(Date from, Date to, Items items) {
        this.from = new Date(from.getTime());
        this.to = new Date(to.getTime());
        this.items = items;
    }

    public Date getFrom() {
        return from;
    }

    public Date getTo() {
        return to;
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
