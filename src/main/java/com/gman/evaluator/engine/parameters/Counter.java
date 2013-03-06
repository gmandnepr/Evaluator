package com.gman.evaluator.engine.parameters;

import java.util.Iterator;

/**
 * @author gman
 * @since 26.11.12 21:19
 */
public class Counter extends AbstractParameter<Integer> {

    private final int from;
    private final int to;

    public Counter(String name) {
        this(name, 0, Integer.MAX_VALUE);
    }

    public Counter(String name, int from, int to) {
        super(name);
        this.from = from;
        this.to = to;
    }

    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {

            private int current = from - 1;

            public boolean hasNext() {
                return current < to;
            }

            public Integer next() {
                current++;
                return current;
            }

            public void remove() {
            }
        };
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    @Override
    public int items() {
        return to - from + 1;
    }

    @Override
    public String toString() {
        return super.toString() + "[" + from + ".." + to + "]";
    }
}
