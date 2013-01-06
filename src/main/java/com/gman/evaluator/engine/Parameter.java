package com.gman.evaluator.engine;

/**
 * @author gman
 * @since 26.11.12 21:19
 */
public interface Parameter<T> extends Iterable<T> {

    String getName();

    int items();
}
