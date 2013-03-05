package com.gman.evaluator.gui;

import com.gman.evaluator.engine.services.analitics.Period;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gman
 * @since 04.03.13 22:24
 */
public class SeparatedItemsTableModel extends AbstractTableModel {

    private final List<Period> periods = new ArrayList<Period>();

    public void setPeriods(List<Period> periods) {
        this.periods.clear();
        this.periods.addAll(periods);
        fireTableDataChanged();
    }

    public void clear() {
        this.periods.clear();
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return periods.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "From";
            case 1:
                return "To";
            default:
                return "Offers";
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        final Period period = periods.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return period.getFrom();
            case 1:
                return period.getTo();
            default:
                return period.getItems().size();
        }
    }
}
