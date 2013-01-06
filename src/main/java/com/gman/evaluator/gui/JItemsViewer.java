package com.gman.evaluator.gui;

import com.gman.evaluator.engine.Items;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * @author gman
 * @since 11/29/12 9:38 AM
 */
public class JItemsViewer extends JPanel {

    private final ItemTableModel model;
    private final JTable table;

    public JItemsViewer(ItemTableModel model, AbstractButton... customActions) {
        this.model = model;
        this.table = new JTable(model);
        init(customActions);
    }

    private void init(AbstractButton... customActions) {
        setLayout(new BorderLayout());

        final JPanel controls = new JPanel(new GridLayout(2 + customActions.length, 1));
        for (AbstractButton customAction : customActions) {
            controls.add(customAction);
        }
        controls.add(ComponentUtils.activeElement(new JButton("Load items"), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ComponentUtils.openFileOperation(new ComponentUtils.OpenFileOperation() {
                    @Override
                    public void perform(InputStream is) throws IOException {
                        try {
                            model.setItems((Items) new ObjectInputStream(is).readObject());
                        } catch (ClassNotFoundException e1) {
                            throw new IOException(e1);
                        }
                    }
                });
            }
        }));
        controls.add(ComponentUtils.activeElement(new JButton("Save items"), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ComponentUtils.saveFileOperation(new ComponentUtils.SaveFileOperation() {
                    @Override
                    public void perform(OutputStream os) throws IOException {
                        new ObjectOutputStream(os).writeObject(model.getItems());
                    }
                });
            }
        }));

        table.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    final int index = table.convertRowIndexToModel(table.rowAtPoint(e.getPoint()));
                    final String url = model.getLink(index);
                    Browser.openURL(url);
                }
            }
        });
        table.setRowSorter(new TableRowSorter<TableModel>(model));

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(controls, BorderLayout.EAST);
    }
}
