package com.gman.evaluator.gui;

import com.gman.evaluator.engine.ProcessableCallback;

import javax.swing.*;
import java.awt.*;

/**
 * @author gman
 * @since 11/30/12 10:34 AM
 */
public class JProcessableCallbackImpl extends ProgressMonitor implements ProcessableCallback {

    public JProcessableCallbackImpl(Component parent) {
        super(parent, "", "", 0, 100);
        setMillisToPopup(500);
        setMillisToDecideToPopup(500);
    }

    @Override
    public void processed(String status, int done) {
        if (isCanceled()) {
            throw new TaskCanceledException();
        }
        setNote(status);
        setProgress(done);
    }
}
