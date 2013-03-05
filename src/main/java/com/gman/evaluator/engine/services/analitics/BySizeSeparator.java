package com.gman.evaluator.engine.services.analitics;

import com.gman.evaluator.engine.Item;
import com.gman.evaluator.engine.Items;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author gman
 * @since 2/18/13 1:58 PM
 */
public class BySizeSeparator extends AbstractItemsSeparator {

    private static final int DEFAULT_SIZE = 100;

    private final int size;

    public BySizeSeparator() {
        this(DEFAULT_SIZE);
    }

    public BySizeSeparator(int size) {
        this.size = size;
    }

    @Override
    public List<Period> separate(Items items) {

        final Items copy = items.copyAll();
        Collections.sort(copy, new ByDateComparator());

        final List<Items> separatedItems = new ArrayList<Items>();
        boolean limitReached;
        int position = 0;
        do {
            final Items current = items.copyStructure();
            limitReached = copy(copy, current, position, size);
            position += size;
            separatedItems.add(current);
        } while (!limitReached);

        return toSortedList(separatedItems);
    }

    private List<Period> toSortedList(List<Items> separatedItems) {
        final List<Period> items = new ArrayList<Period>(separatedItems.size());
        for (Items entry : separatedItems) {
            items.add(extractPeriod(entry));
        }
        return items;
    }

    private boolean copy(Items source, Items target, int startPosition, int limit) {
        for (int i = startPosition; i < startPosition + limit && i < source.size(); i++) {
            target.add(source.get(i));
        }
        return startPosition + limit >= source.size();//limit reached
    }

    private static class ByDateComparator implements Comparator<Item> {

        @Override
        public int compare(Item o1, Item o2) {
            return o1.getDate().compareTo(o2.getDate());
        }
    }
}
