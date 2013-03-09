package com.gman.evaluator.engine.services.analitics.separator;

/**
 * @author gman
 * @since 04.03.13 22:21
 */
public enum ItemsSeparatorFactory {

    BY_DATE(new ByDateSeparator()),
    BY_SIZE_25(new BySizeSeparator(25)),
    BY_SIZE_50(new BySizeSeparator(50)),
    BY_SIZE_100(new BySizeSeparator(100)),
    BY_SIZE_250(new BySizeSeparator(250));

    private final ItemsSeparator impl;

    private ItemsSeparatorFactory(ItemsSeparator impl) {
        this.impl = impl;
    }

    public ItemsSeparator create() {
        return impl;
    }
}
