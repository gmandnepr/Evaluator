package com.gman.evaluator.engine.services;

import com.gman.evaluator.engine.Processable;
import com.gman.evaluator.engine.ProcessableCallback;

/**
 * @author gman
 * @since 11/30/12 9:41 AM
 */
public abstract class AbstractService<T> implements Processable<T> {

    protected final String processName;
    protected final ProcessableCallback callback;

    protected AbstractService() {
        this("No name");
    }

    protected AbstractService(String processName) {
        this(processName, ProcessableCallback.EMPTY);
    }

    protected AbstractService(String processName, ProcessableCallback callback) {
        this.processName = processName;
        this.callback = callback;
    }

    @Override
    public String getProcessName() {
        return processName;
    }

    protected int countPercentage(int current, int total) {
        return 100 * current / total;
    }
}
