package com.gman.evaluator.engine.services.analitics;

import com.gman.evaluator.engine.DataHolder;
import com.gman.evaluator.engine.Evaluation;
import com.gman.evaluator.engine.Items;
import com.gman.evaluator.engine.ProcessableCallback;
import com.gman.evaluator.engine.services.AbstractService;

import java.util.List;


/**
 * @author gman
 * @since 2/18/13 2:34 PM
 */
public class AnalyticsService extends AbstractService<AnalyticsResult> {

    private final DataHolder<Items> itemsHolder;
    private final DataHolder<AnalyticsConfig> analyticsConfigHolder;

    public AnalyticsService(ProcessableCallback callback, DataHolder<Items> itemsHolder, DataHolder<AnalyticsConfig> analyticsConfigHolder) {
        super("Analytics", callback);
        this.itemsHolder = itemsHolder;
        this.analyticsConfigHolder = analyticsConfigHolder;
    }

    @Override
    public AnalyticsResult call() {

        final AnalyticsConfig config = analyticsConfigHolder.get();
        final Items processingItems = itemsHolder.get();

        final List<Period> periods = analyticsConfigHolder.get().getSeparator().separate(processingItems);

        for (Period period : periods) {
            final Evaluation evaluation = config.getEvaluator().evaluate(period.getItems());
            final double price = evaluation.countPriceFor(config.getSample());

            period.setEvaluation(evaluation);
            period.setSamplePrice(price);
        }

        return new AnalyticsResult(periods);
    }
}
