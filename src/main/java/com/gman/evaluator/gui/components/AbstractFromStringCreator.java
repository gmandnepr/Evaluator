package com.gman.evaluator.gui.components;

import com.gman.evaluator.gui.MainForm;

import javax.swing.*;

/**
 * @author gman
 * @since 03.12.12 18:07
 */
public abstract class AbstractFromStringCreator {

    private final MainForm parent;

    protected AbstractFromStringCreator(MainForm parent) {
        this.parent = parent;
    }

    public MainForm getParent() {
        return parent;
    }

    public String askForString() {
        return askForString("Input element");
    }

    public String askForString(String text) {
        return askForString(text, null);

    }

    public String askForString(String text, String suggestion) {
        return (String) JOptionPane.showInputDialog(
                null,
                text,
                "Input dialog",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                suggestion);
    }
}
