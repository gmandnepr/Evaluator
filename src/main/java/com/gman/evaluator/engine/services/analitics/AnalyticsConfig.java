package com.gman.evaluator.engine.services.analitics;

import com.gman.evaluator.engine.Item;
import com.gman.evaluator.engine.services.analitics.abnomal.AbnormalRemover;
import com.gman.evaluator.engine.services.analitics.evaluation.Evaluator;
import com.gman.evaluator.engine.services.analitics.prediction.PeriodPredictor;
import com.gman.evaluator.engine.services.analitics.separator.ItemsSeparator;

/**
 * @author gman
 * @since 04.03.13 22:12
 */
public class AnalyticsConfig {

    private final ItemsSeparator separator;
    private final AbnormalRemover abnormalRemover;
    private final Evaluator evaluator;
    private final PeriodPredictor periodPredictor;
    private final Item sample;

    public AnalyticsConfig(ItemsSeparator separator, AbnormalRemover abnormalRemover, Evaluator evaluator, PeriodPredictor periodPredictor, Item sample) {
        this.separator = separator;
        this.abnormalRemover = abnormalRemover;
        this.evaluator = evaluator;
        this.periodPredictor = periodPredictor;
        this.sample = sample;
    }

    public ItemsSeparator getSeparator() {
        return separator;
    }

    public AbnormalRemover getAbnormalRemover() {
        return abnormalRemover;
    }

    public Evaluator getEvaluator() {
        return evaluator;
    }

    public PeriodPredictor getPeriodPredictor() {
        return periodPredictor;
    }

    public Item getSample() {
        return sample;
    }
}
