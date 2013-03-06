package com.gman.evaluator.gui;

import com.gman.evaluator.engine.Item;
import com.gman.evaluator.engine.ItemImpl;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * @author gman
 * @since 06.03.13 18:56
 */
public class JItemCreator extends JPanel {

    private final JTextField engineField;
    private final JTextField distanceField;
    private final JTextField ageField;

    public JItemCreator() {
        super();

        engineField = new JTextField("1.6");
        distanceField = new JTextField("50000");
        ageField = new JTextField("2");

        initComponents();
    }

    private void initComponents() {
        setLayout(new GridLayout(3, 2));
        setBorder(new TitledBorder("Items"));

        add(new JLabel("Engine"));
        add(engineField);
        add(new JLabel("Distance"));
        add(distanceField);
        add(new JLabel("Age"));
        add(ageField);
    }

    public Item createItem() {
        final ItemImpl item = new ItemImpl();
        item.setProperty("engine", Double.parseDouble(engineField.getText()));
        item.setProperty("distance", Double.parseDouble(distanceField.getText()));
        item.setProperty("age", Double.parseDouble(ageField.getText()));
        return item;
    }
}
