package com.gman.evaluator.gui;

import com.gman.evaluator.engine.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author gman
 * @since 03.03.13 15:03
 */
public class CustomCarInput extends JDialog {

    private final JItemCreator itemCreator;
    private Item createdItem = null;

    public CustomCarInput(MainForm owner) {
        super(owner, true);

        itemCreator = new JItemCreator();

        initComponents();
    }

    public Item getCreatedItem() {
        return createdItem;
    }

    private void initComponents() {
        setTitle("Evaluate car");
        setResizable(false);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(itemCreator, BorderLayout.CENTER);

        final JPanel controlButtons = new JPanel(new GridLayout(1, 2));
        controlButtons.add(ComponentUtils.activeElement(new JButton("OK"), new OkAction()));
        controlButtons.add(ComponentUtils.activeElement(new JButton("Cancel"), new CancelAction()));

        getContentPane().add(controlButtons, BorderLayout.SOUTH);

        pack();
    }

    private final class OkAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                CustomCarInput.this.createdItem = itemCreator.createItem();
                CustomCarInput.this.setVisible(false);
                CustomCarInput.this.dispose();
            } catch (NumberFormatException nfe) {
                ComponentUtils.showErrorDialog(nfe);
            }
        }
    }

    private final class CancelAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            CustomCarInput.this.setVisible(false);
            CustomCarInput.this.dispose();
        }
    }
}
