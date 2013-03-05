package com.gman.evaluator.gui;

import com.gman.evaluator.engine.ProcessableCallback;

import javax.swing.*;

/**
 * @author gman
 * @since 05.03.13 18:31
 */
public class JProgressBarProcessableCallbackImpl extends JProgressBar implements ProcessableCallback {

    public JProgressBarProcessableCallbackImpl() {
        super(0, 100);
        setVisible(false);
        setStringPainted(true);
    }

    @Override
    public void processed(String status, int done) {
        setVisible(done < 100);
        setString(status);
        setValue(done);
    }
}
