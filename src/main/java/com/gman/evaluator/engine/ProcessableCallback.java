package com.gman.evaluator.engine;

/**
 * @author gman
 * @since 26.11.12 21:50
 */
public interface ProcessableCallback {

    void processed(String status, int done);

    ProcessableCallback EMPTY = new ProcessableCallback() {
        @Override
        public void processed(String status, int done) {
        }
    };
}
