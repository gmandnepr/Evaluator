package com.gman.evaluator;

import com.gman.evaluator.gui.MainForm;

/**
 * @author gman
 * @since 26.11.12 20:01
 */
public class Runner {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainForm().setVisible(true);
            }
        });
    }
}
