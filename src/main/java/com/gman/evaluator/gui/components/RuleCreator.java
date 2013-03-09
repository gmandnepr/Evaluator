package com.gman.evaluator.gui.components;

import com.gman.evaluator.engine.FieldDefinition;
import com.gman.evaluator.engine.Rule;
import com.gman.evaluator.engine.rules.Operation;
import com.gman.evaluator.engine.rules.Operations;
import com.gman.evaluator.engine.rules.RuleImpl;
import com.gman.evaluator.gui.ComponentCreator;
import com.gman.evaluator.gui.ComponentUtils;
import com.gman.evaluator.gui.MainForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * @author gman
 * @since 15.12.12 14:50
 */
public class RuleCreator extends JDialog implements ComponentCreator<Rule> {

    private MainForm parent;
    private Rule rule = null;

    private final JComboBox<FieldDefinition> fields;
    private final JComboBox<Operation> operations;
    private final JTextField operand;

    private final java.util.List<FieldDefinition> fieldDefinitions = new ArrayList<FieldDefinition>();
    private final java.util.List<Operation> fieldOperations = new ArrayList<Operation>();

    public RuleCreator(MainForm parent) {
        super(parent, true);
        this.parent = parent;
        this.fields = new JComboBox<FieldDefinition>(new DefaultComboBoxModel<FieldDefinition>() {

            @Override
            public int getSize() {
                return fieldDefinitions.size();
            }

            @Override
            public FieldDefinition getElementAt(int index) {
                return fieldDefinitions.get(index);
            }
        });
        this.fields.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateOperations(true);
            }
        });
        this.operations = new JComboBox<Operation>(new DefaultComboBoxModel<Operation>() {
            @Override
            public int getSize() {
                return fieldOperations.size();
            }

            @Override
            public Operation getElementAt(int index) {
                return fieldOperations.get(index);
            }
        });
        this.operand = new JTextField();
        init();
    }

    private void init() {
        setTitle("Rule creator");
        setLayout(new BorderLayout());
        setSize(300, 150);
        setResizable(false);

        final JPanel elements = new JPanel(new GridLayout(4, 2));

        elements.add(new JLabel("Field"));
        elements.add(fields);
        elements.add(new JLabel("Operation"));
        elements.add(operations);
        elements.add(new JLabel("Operand"));
        elements.add(operand);
        elements.add(ComponentUtils.activeElement(new JButton("OK"), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rule = createRule();
                RuleCreator.this.setVisible(false);
                dispose();
            }
        }));
        elements.add(ComponentUtils.activeElement(new JButton("Cancel"), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rule = null;
                RuleCreator.this.setVisible(false);
                dispose();
            }
        }));

        add(elements, BorderLayout.CENTER);
    }

    @Override
    public Rule create() {
        prepareDialog();
        //freezes hear
        return rule;
    }

    @Override
    public Rule update(Rule old) {
        prepareDialog((RuleImpl) old);
        //freezes hear
        return rule;
    }

    private void prepareDialog() {
        updateFields();
        updateOperations(false);
        updateOperand();
        setVisible(true);
    }

    private void prepareDialog(RuleImpl rule) {
        fields.setSelectedItem(rule.getField());
        operations.setSelectedItem(rule.getOperation());
        operand.setText(rule.getOperand());
        setVisible(true);
    }

    private void updateFields() {
        fieldDefinitions.clear();
        fieldDefinitions.addAll(parent.getItemTableModel().getItems().getRegisteredFields());
        fields.updateUI();
    }

    private void updateOperations(boolean fieldSet) {
        if (fieldSet) {
            final FieldDefinition selected = (FieldDefinition) fields.getSelectedItem();
            fieldOperations.clear();
            for (final Operation o : Operations.OPERATIONS) {
                if (o.accept(selected.getFieldType())) {
                    fieldOperations.add(o);
                }
            }
        } else {
            fieldOperations.clear();
        }
        operations.updateUI();
    }

    private void updateOperand() {
        operand.setText("");
    }

    private Rule createRule() {
        return new RuleImpl((FieldDefinition) fields.getSelectedItem(), (Operation) operations.getSelectedItem(), operand.getText());
    }
}
