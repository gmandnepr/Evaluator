package com.gman.evaluator.engine.services.analitics;

import com.gman.evaluator.engine.Items;

import java.util.List;

/**
 * @author gman
 * @since 2/18/13 1:57 PM
 */
public interface ItemsSeparator {

    List<Items> separate(Items items);
}
