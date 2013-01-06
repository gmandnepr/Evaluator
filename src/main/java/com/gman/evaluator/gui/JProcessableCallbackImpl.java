package com.gman.evaluator.gui;

import com.gman.evaluator.engine.ProcessableCallback;

import javax.swing.*;

/**
 * @author gman
 * @since 11/30/12 10:34 AM
 */
public class JProcessableCallbackImpl implements ProcessableCallback {

    private final ProgressMonitor progressMonitor;

    public JProcessableCallbackImpl(JFrame parent, String name) {
        progressMonitor = new ProgressMonitor(parent, name, "", 0, 100);
        progressMonitor.setMillisToPopup(500);
        progressMonitor.setMillisToDecideToPopup(500);
    }

    public JProcessableCallbackImpl(ProgressMonitor progressMonitor) {
        this.progressMonitor = progressMonitor;
    }

    @Override
    public void processed(String status, int done) {
        if (progressMonitor.isCanceled()) {
            throw new TaskCanceledException();
        }
        progressMonitor.setNote(status);
        progressMonitor.setProgress(done);
    }
}
