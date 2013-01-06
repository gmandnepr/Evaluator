package com.gman.evaluator.gui;

import javax.swing.*;
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
 * @since 11/27/12 9:07 AM
 */
public class JPickList<T> extends JPanel {

    private final ComponentCreator<T> creator;
    private final JPickListModel<T> model;
    private final JPickListItemOperation<T> operation;
    private final JTable table;

    public JPickList(JPickListModel<T> model, ComponentCreator<T> creator, AbstractButton... customActions) {
        this(model, creator, new JPickListItemOperation<T>() {
            @Override
            public void performed(T item) {
            }
        }, customActions);
    }

    public JPickList(JPickListModel<T> model, ComponentCreator<T> creator, JPickListItemOperation<T> operation, AbstractButton... customActions) {
        this.creator = creator;
        this.model = model;
        this.table = new JTable(model);
        this.operation = operation;
        init(customActions);
    }

    private void init(AbstractButton... customActions) {
        setLayout(new BorderLayout());

        final JPanel controls = new JPanel(new GridLayout(7 + customActions.length, 1));
        for (AbstractButton customAction : customActions) {
            controls.add(customAction);
        }
        controls.add(ComponentUtils.activeElement(new JButton("Add"), new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                final T toAdd = creator.create();
                if (toAdd != null) {
                    model.addItem(toAdd);
                }
            }
        }));
        controls.add(ComponentUtils.activeElement(new JButton("Edit"), new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                final int toEdit = table.getSelectedRow();
                if (toEdit != -1) {
                    final T update = creator.update(model.getData().get(toEdit));
                    if (update != null) {
                        model.editItem(toEdit, update);
                    }
                }
            }
        }));
        controls.add(ComponentUtils.activeElement(new JButton("Remove"), new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                final int toRemove = table.getSelectedRow();
                if (toRemove != -1) {
                    model.removeItem(toRemove);
                }
            }
        }));
        controls.add(ComponentUtils.activeElement(new JButton("Move up"), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final int toMove = table.getSelectedRow();
                if (toMove >= 1) {
                    model.swapItems(toMove - 1, toMove);
                }
            }
        }));
        controls.add(ComponentUtils.activeElement(new JButton("Move down"), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final int toMove = table.getSelectedRow();
                if (toMove <= model.getData().size() - 2) {
                    model.swapItems(toMove, toMove + 1);
                }
            }
        }));
        controls.add(ComponentUtils.activeElement(new JButton("Load"), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ComponentUtils.openFileOperation(new ComponentUtils.OpenFileOperation() {
                    @Override
                    public void perform(InputStream is) throws IOException {
                        try {
                            model.setData((java.util.List<T>) new ObjectInputStream(is).readObject());
                        } catch (ClassNotFoundException e1) {
                            throw new IOException(e1);
                        }
                    }
                });
            }
        }));
        controls.add(ComponentUtils.activeElement(new JButton("Save"), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ComponentUtils.saveFileOperation(new ComponentUtils.SaveFileOperation() {
                    @Override
                    public void perform(OutputStream os) throws IOException {
                        new ObjectOutputStream(os).writeObject(model.getData());
                    }
                });
            }
        }));

        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        table.getColumnModel().getColumn(0).setMaxWidth(50);
        table.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    final int index = table.convertRowIndexToModel(table.rowAtPoint(e.getPoint()));
                    final T item = model.getData().get(index);
                    operation.performed(item);
                }
            }
        });

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(controls, BorderLayout.EAST);
    }
}
