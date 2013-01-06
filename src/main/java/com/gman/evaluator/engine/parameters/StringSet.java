package com.gman.evaluator.engine.parameters;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

/**
 * @author gman
 * @since 26.11.12 21:21
 */
public class StringSet extends AbstractParameter<String> {

    private final Set<String> strings;

    public StringSet(String name, Set<String> strings) {
        super(name);
        this.strings = strings;
    }

    public Iterator<String> iterator() {
        return strings.iterator();
    }

    public Set<String> getStrings() {
        return Collections.unmodifiableSet(strings);
    }

    @Override
    public int items() {
        return strings.size();
    }

    @Override
    public String toString() {
        return super.toString() + "{" + strings.toString() + "}";
    }
}
