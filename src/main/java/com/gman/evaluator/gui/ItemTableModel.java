package com.gman.evaluator.gui;

import com.gman.evaluator.engine.FieldDefinition;
import com.gman.evaluator.engine.Items;
import com.gman.evaluator.engine.fields.LinkFieldType;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gman
 * @since 26.11.12 20:49
 */
public class ItemTableModel extends AbstractTableModel {

    private Items items = Items.EMPTY;
    private final List<FieldDefinition> fieldDefinitions = new ArrayList<FieldDefinition>();
    private FieldDefinition linkDefinition;

    public void setItems(Items items) {
        this.items = items;
        this.fieldDefinitions.clear();
        this.fieldDefinitions.addAll(items.getRegisteredFields());
        this.linkDefinition = null;
        for (FieldDefinition fieldDefinition : items.getRegisteredFields()) {
            if (fieldDefinition.getFieldType() instanceof LinkFieldType) {
                this.linkDefinition = fieldDefinition;
            }
        }
        fireTableStructureChanged();
    }

    public Items getItems() {
        return items;
    }

    public int getRowCount() {
        return items.size();
    }

    public int getColumnCount() {
        return fieldDefinitions.size();
    }

    @Override
    public String getColumnName(int column) {
        return fieldDefinitions.get(column).getName();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return fieldDefinitions.get(columnIndex).getFieldType().type();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        final FieldDefinition definition = fieldDefinitions.get(columnIndex);
        return definition.getFieldType().retrieve(items.get(rowIndex), definition.getName());
    }

    public String getLink(int rowIndex) {
        return linkDefinition.getFieldType().retrieve(items.get(rowIndex), linkDefinition.getName()).toString();
    }
}
