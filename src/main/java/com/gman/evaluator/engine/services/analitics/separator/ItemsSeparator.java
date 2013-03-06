package com.gman.evaluator.engine.services.analitics.separator;

import com.gman.evaluator.engine.Items;
import com.gman.evaluator.engine.services.analitics.Period;

import java.util.List;

/**
 * @author gman
 * @since 2/18/13 1:57 PM
 */
public interface ItemsSeparator {

    List<Period> separate(Items items);
}
