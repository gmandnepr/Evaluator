package com.gman.evaluator.gui;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author gman
 * @since 11/27/12 9:04 AM
 */
public class JPickListModel<T> extends AbstractTableModel {

    private final List<T> items = new ArrayList<T>();

    public void setData(List<T> items) {
        this.items.clear();
        this.items.addAll(items);
        fireTableDataChanged();
    }

    public void addItem(T item) {
        this.items.add(item);
        fireTableRowsInserted(this.items.size() - 1, this.items.size() - 1);
    }

    public void editItem(int index, T item) {
        this.items.set(index, item);
        fireTableRowsUpdated(index, index);
    }

    public void removeItem(int index) {
        this.items.remove(index);
        fireTableRowsDeleted(index, index);
    }

    public void swapItems(int i1, int i2) {
        final T tmp = this.items.get(i1);
        this.items.set(i1, this.items.get(i2));
        this.items.set(i2, tmp);
        fireTableRowsUpdated(i1, i2);
    }

    public List<T> getData() {
        return Collections.unmodifiableList(items);
    }

    public int getRowCount() {
        return items.size();
    }

    public int getColumnCount() {
        return 2;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "index";
            default:
                return "value";
        }
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return rowIndex;
            default:
                return items.get(rowIndex).toString();
        }
    }
}
