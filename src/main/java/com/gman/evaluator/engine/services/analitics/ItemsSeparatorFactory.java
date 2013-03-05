package com.gman.evaluator.engine.services.analitics;

/**
 * @author gman
 * @since 04.03.13 22:21
 */
public enum ItemsSeparatorFactory {

    BY_DATE(ByDateSeparator.class),
    BY_SIZE(BySizeSeparator.class);

    private final Class<? extends ItemsSeparator> clazz;

    private ItemsSeparatorFactory(Class<? extends ItemsSeparator> clazz) {
        this.clazz = clazz;
    }

    public ItemsSeparator create() {
        try {
            return this.clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
