package com.gman.evaluator.gui;

import com.gman.evaluator.engine.Item;
import com.gman.evaluator.engine.ItemImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author gman
 * @since 03.03.13 15:03
 */
public class CustomCarInput extends JDialog {

    private Item createdItem = null;

    public CustomCarInput(MainForm owner) {
        super(owner, true);

        engineField = new JTextField();
        distanceField = new JTextField();
        ageField = new JTextField();

        initComponents();
    }

    public Item getCreatedItem() {
        return createdItem;
    }

    private void initComponents() {
        setTitle("Evaluate car");
        setResizable(false);

        getContentPane().setLayout(new GridLayout(4, 2));
        getContentPane().add(new JLabel("Engine"));
        getContentPane().add(engineField);
        getContentPane().add(new JLabel("Distance"));
        getContentPane().add(distanceField);
        getContentPane().add(new JLabel("Age"));
        getContentPane().add(ageField);
        getContentPane().add(ComponentUtils.activeElement(new JButton("OK"), new OkAction()));
        getContentPane().add(ComponentUtils.activeElement(new JButton("Cancel"), new CancelAction()));

        pack();
    }

    private final class OkAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                final ItemImpl item = new ItemImpl();
                item.setProperty("engine", Double.parseDouble(engineField.getText()));
                item.setProperty("distance", Double.parseDouble(distanceField.getText()));
                item.setProperty("age", Double.parseDouble(ageField.getText()));
                CustomCarInput.this.createdItem = item;
            } catch (NumberFormatException nfe) {
                ComponentUtils.showErrorDialog(nfe);
            }
            CustomCarInput.this.setVisible(false);
            CustomCarInput.this.dispose();
        }
    }

    private final class CancelAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            CustomCarInput.this.setVisible(false);
            CustomCarInput.this.dispose();
        }
    }

    private final JTextField engineField;
    private final JTextField distanceField;
    private final JTextField ageField;

}
