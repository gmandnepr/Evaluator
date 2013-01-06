package com.gman.evaluator.engine;

/**
 * @author gman
 * @since 26.11.12 21:36
 */
public interface Rule {

    boolean satisfy(Item item);
}
