package com.gman.evaluator.engine;

import java.util.Collections;
import java.util.List;

/**
 * @author gman
 * @since 03.03.13 18:18
 */
public class Rules {

    private final List<Rule> rules;

    public Rules(List<Rule> rules) {
        this.rules = Collections.unmodifiableList(rules);
    }

    public boolean satisfyAll(Item item) {
        for (Rule rule : rules) {
            if (!rule.satisfy(item)) {
                return false;
            }
        }
        return true;
    }

    public boolean satisfyAny(Item item) {
        for (Rule rule : rules) {
            if (rule.satisfy(item)) {
                return true;
            }
        }
        return false;
    }
}
