package com.gman.evaluator.gui;

import com.gman.evaluator.engine.Processable;
import com.gman.evaluator.engine.ProcessableCallback;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author gman
 * @since 11.11.12 15:39
 */
public final class ComponentUtils {

    private ComponentUtils() {
    }

    public static JComponent table(AbstractTableModel model) {
        return new JScrollPane(new JTable(model));
    }

    public static JComponent splitView(JComponent comp1, JComponent comp2, int ratio) {
        final JSplitPane pane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, false, comp1, comp2);
        pane.setOneTouchExpandable(true);
        pane.setDividerLocation(ratio);
        return pane;
    }

    public static JMenu menu(String name, JMenuItem... items) {
        final JMenu menu = new JMenu(name);
        for (JMenuItem item : items) {
            menu.add(item);
        }
        return menu;
    }

    public static <T extends AbstractButton> T activeElement(T element, ActionListener listener) {
        element.addActionListener(listener);
        return element;
    }

    public static void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "info", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showErrorDialog(Exception e) {
        final StringBuilder sb = new StringBuilder();
        for (StackTraceElement ste : e.getStackTrace()) {
            sb.append(ste).append('\n');
        }
        showErrorDialog(null, e.getMessage(), sb.toString());
    }

    public static void showErrorDialog(JPanel parent, String title, String message) {
        JOptionPane.showMessageDialog(parent, message, title, JOptionPane.ERROR_MESSAGE);
    }

    public static <T> void executeWithProgressMonitor(JFrame parent, BackgroundProcessable<T> task) {
        final JProcessableCallbackImpl progressBar = new JProcessableCallbackImpl(parent, task.getProcessable().getProcessName());
        task.getProcessable().setCallback(progressBar);
        task.execute();
    }

    public static abstract class BackgroundProcessable<T> extends SwingWorker<Void, Void> {

        private final Processable<T> processable;
        private T result;
        private Exception e;

        protected BackgroundProcessable(Processable<T> processable) {
            this.processable = processable;
        }

        @Override
        protected Void doInBackground() throws Exception {
            try {
                result = processable.call();
            } catch (Exception e) {
                this.e = e;
            }
            processable.setCallback(ProcessableCallback.EMPTY);
            return null;
        }

        @Override
        protected void done() {
            setResult();
        }

        public T getResult() throws Exception {
            if (e != null) {
                throw e;
            }
            return result;
        }

        public Processable<T> getProcessable() {
            return processable;
        }

        public abstract void setResult();
    }

    public static void openFileOperation(OpenFileOperation operation) {
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            InputStream is = null;
            try {
                is = new BufferedInputStream(new FileInputStream(chooser.getSelectedFile()));
                operation.perform(is);
            } catch (IOException e) {
                showErrorDialog(e);
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        showErrorDialog(e);
                    }
                }
            }
        }
    }

    public static void saveFileOperation(SaveFileOperation operation) {
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            OutputStream os = null;
            try {
                os = new BufferedOutputStream(new FileOutputStream(chooser.getSelectedFile()));
                operation.perform(os);
            } catch (IOException e) {
                showErrorDialog(e);
            } finally {
                if (os != null) {
                    try {
                        os.close();
                    } catch (IOException e) {
                        showErrorDialog(e);
                    }
                }
            }
        }
    }

    public static interface OpenFileOperation {

        void perform(InputStream is) throws IOException;
    }

    public static interface SaveFileOperation {

        void perform(OutputStream os) throws IOException;
    }

    private static final JFileChooser chooser = new JFileChooser();
}
