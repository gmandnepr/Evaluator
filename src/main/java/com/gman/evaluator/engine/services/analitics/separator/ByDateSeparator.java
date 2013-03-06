package com.gman.evaluator.engine.services.analitics.separator;

import com.gman.evaluator.engine.Item;
import com.gman.evaluator.engine.Items;
import com.gman.evaluator.engine.services.analitics.AbstractItemsSeparator;
import com.gman.evaluator.engine.services.analitics.Period;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author gman
 * @since 2/18/13 1:58 PM
 */
public class ByDateSeparator extends AbstractItemsSeparator {

    @Override
    public List<Period> separate(Items items) {

        final SimpleDateFormat byMonthFormat = new SimpleDateFormat("yyyy-MM");

        final Map<String, Items> separatedItems = new TreeMap<String, Items>();

        for (Item item : items) {
            final String key = byMonthFormat.format(item.getDate());
            if (!separatedItems.containsKey(key)) {
                separatedItems.put(key, items.copyStructure());
            }
            separatedItems.get(key).add(item);
        }

        return toSortedList(separatedItems);
    }

    private List<Period> toSortedList(Map<String, Items> separatedItems) {
        final List<Period> items = new ArrayList<Period>(separatedItems.size());
        for (Map.Entry<String, Items> entry : separatedItems.entrySet()) {
            items.add(extractPeriod(entry.getValue()));
        }
        return items;
    }
}
