package com.gman.evaluator.gui;

/**
 * @author gman
 * @since 30.11.12 23:30
 */
public class TaskCanceledException extends RuntimeException {

    public TaskCanceledException() {
        super("Task canceled");
    }
}
