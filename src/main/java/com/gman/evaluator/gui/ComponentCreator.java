package com.gman.evaluator.gui;

/**
 * @author gman
 * @since 26.11.12 21:42
 */
public interface ComponentCreator<T> {

    T create();

    T update(T old);
}
