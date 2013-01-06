package com.gman.evaluator.engine.services;

import com.gman.evaluator.engine.Processable;
import com.gman.evaluator.engine.ProcessableCallback;

/**
 * @author gman
 * @since 11/30/12 9:41 AM
 */
public abstract class AbstractService<T> implements Processable<T> {

    protected ProcessableCallback callback = ProcessableCallback.EMPTY;
    protected String processName;


    protected AbstractService() {
        this("No name");
    }

    protected AbstractService(String processName) {
        this.processName = processName;
    }

    @Override
    public String getProcessName() {
        return processName;
    }

    @Override
    public void setCallback(ProcessableCallback callback) {
        this.callback = callback;
    }

    protected int countPercentage(int current, int total) {
        return 100 * current / total;
    }
}
