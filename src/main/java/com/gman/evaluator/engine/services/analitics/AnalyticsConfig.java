package com.gman.evaluator.engine.services.analitics;

import com.gman.evaluator.engine.Item;
import com.gman.evaluator.engine.services.analitics.evaluation.Evaluator;
import com.gman.evaluator.engine.services.analitics.separator.ItemsSeparator;

/**
 * @author gman
 * @since 04.03.13 22:12
 */
public class AnalyticsConfig {

    private final ItemsSeparator separator;
    private final Evaluator evaluator;
    private final Item sample;

    public AnalyticsConfig(ItemsSeparator separator, Evaluator evaluator, Item sample) {
        this.separator = separator;
        this.evaluator = evaluator;
        this.sample = sample;
    }

    public ItemsSeparator getSeparator() {
        return separator;
    }

    public Evaluator getEvaluator() {
        return evaluator;
    }

    public Item getSample() {
        return sample;
    }
}
