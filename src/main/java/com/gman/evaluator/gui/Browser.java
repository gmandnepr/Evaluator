package com.gman.evaluator.gui;

import java.net.URI;

public final class Browser {

    private Browser() {
    }

    public static void openURL(String url) {
        try {
            java.awt.Desktop.getDesktop().browse(new URI("http://" + url));
        } catch (Exception e) {
            ComponentUtils.showErrorDialog(e);
        }
    }
}