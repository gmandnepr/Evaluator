package com.gman.evaluator.engine.services.analitics;

import com.gman.evaluator.engine.Item;
import com.gman.evaluator.engine.Items;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author gman
 * @since 2/18/13 1:58 PM
 */
public class ByDateSeparator implements ItemsSeparator {

    @Override
    public List<Items> separate(Items items) {

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

    private List<Items> toSortedList(Map<String, Items> separatedItems) {
        final List<Items> items = new ArrayList<Items>(separatedItems.size());
        for (Map.Entry<String, Items> entry : separatedItems.entrySet()) {
            items.add(entry.getValue());
        }
        return items;
    }
}
