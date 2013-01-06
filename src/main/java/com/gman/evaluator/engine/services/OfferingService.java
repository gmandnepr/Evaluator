package com.gman.evaluator.engine.services;

import com.gman.evaluator.engine.Evaluation;
import com.gman.evaluator.engine.Item;
import com.gman.evaluator.engine.ItemWithProfit;
import com.gman.evaluator.engine.Items;
import com.gman.evaluator.engine.Rule;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author gman
 * @since 11/30/12 9:40 AM
 */
public class OfferingService extends AbstractService<Items> {

    private static final ProfitComparator PROFIT_COMPARATOR = new ProfitComparator();

    private List<Rule> rules = Collections.emptyList();
    private Evaluation evaluation;
    private Items items;

    public OfferingService() {
        super("Offering service");
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }

    public void setItems(Items items) {
        this.items = items;
    }

    @Override
    public Items call() throws Exception {

        callback.processed("Filtering", 0);

        final Items offer = new Items();
        offer.registerProperties(items.getRegisteredFields());
        offer.registerProperty(ItemWithProfit.PROFIT_DEFINITION);

        for (Item item : items) {
            if (satisfyAll(item)) {
                offer.add(new ItemWithProfit(item, evaluation.countProfitFor(item)));
            }
        }

        callback.processed("Sorting", 50);

        Collections.sort(offer, PROFIT_COMPARATOR);

        callback.processed("Done", 100);

        return offer;
    }

    private boolean satisfyAll(Item item) {
        for (Rule rule : rules) {
            if (!rule.satisfy(item)) {
                return false;
            }
        }
        return true;
    }

    private static class ProfitComparator implements Comparator<Item> {

        @Override
        public int compare(Item o1, Item o2) {
            return -Double.compare(o1.getProperty(ItemWithProfit.PROFIT), o2.getProperty(ItemWithProfit.PROFIT));
        }
    }
}
