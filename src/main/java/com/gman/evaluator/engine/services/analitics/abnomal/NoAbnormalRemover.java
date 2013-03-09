package com.gman.evaluator.engine.services.analitics.abnomal;

import com.gman.evaluator.engine.Items;

/**
 * @author gman
 * @since 09.03.13 14:13
 */
public class NoAbnormalRemover implements AbnormalRemover {

    @Override
    public Items remove(Items items) {
        return items;
    }
}
