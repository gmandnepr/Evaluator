package com.gman.evaluator.engine.services;

import com.gman.evaluator.engine.DataHolder;
import com.gman.evaluator.engine.Evaluation;
import com.gman.evaluator.engine.Item;
import com.gman.evaluator.engine.ItemWithProfit;
import com.gman.evaluator.engine.Items;
import com.gman.evaluator.engine.Rules;

import java.util.Collections;
import java.util.Comparator;

/**
 * @author gman
 * @since 11/30/12 9:40 AM
 */
public class OfferingService extends AbstractService<Items> {

    private static final ProfitComparator PROFIT_COMPARATOR = new ProfitComparator();

    private final DataHolder<Items> items;
    private final DataHolder<Evaluation> evaluation;
    private final DataHolder<Rules> rules;

    public OfferingService(DataHolder<Items> items, DataHolder<Evaluation> evaluation, DataHolder<Rules> rules) {
        super("Offering service");
        this.items = items;
        this.evaluation = evaluation;
        this.rules = rules;
    }

    @Override
    public Items call() {

        final Items processingItems = items.get();
        final Evaluation usedEvaluation = evaluation.get();
        final Rules usedRules = rules.get();

        callback.processed("Filtering", 0);

        final Items offer = new Items();
        offer.registerProperties(processingItems.getRegisteredFields());
        offer.registerProperty(ItemWithProfit.PROFIT_DEFINITION);

        for (Item item : processingItems) {
            if (usedRules.satisfyAll(item)) {
                offer.add(new ItemWithProfit(item, usedEvaluation.countProfitFor(item)));
            }
        }

        callback.processed("Sorting", 50);

        Collections.sort(offer, PROFIT_COMPARATOR);

        callback.processed("Done", 100);

        return offer;
    }

    private static class ProfitComparator implements Comparator<Item> {

        @Override
        public int compare(Item o1, Item o2) {
            return -Double.compare(o1.getProperty(ItemWithProfit.PROFIT), o2.getProperty(ItemWithProfit.PROFIT));
        }
    }
}
