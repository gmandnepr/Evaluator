package com.gman.evaluator.engine;

import java.util.concurrent.Callable;

/**
 * @author gman
 * @since 11/30/12 9:04 AM
 */
public interface Processable<V> extends Callable<V> {

    String getProcessName();
}
