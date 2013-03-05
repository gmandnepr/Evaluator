package com.gman.evaluator.engine.services.analitics;

/**
 * @author gman
 * @since 04.03.13 22:12
 */
public class AnalyticsConfig {

    private ItemsSeparator separator;


    public AnalyticsConfig(ItemsSeparator separator) {
        this.separator = separator;
    }

    public ItemsSeparator getSeparator() {
        return separator;
    }
}
