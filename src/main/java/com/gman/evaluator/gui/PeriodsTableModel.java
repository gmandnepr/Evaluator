package com.gman.evaluator.gui;

import com.gman.evaluator.engine.services.analitics.Period;

import javax.swing.table.AbstractTableModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author gman
 * @since 04.03.13 22:24
 */
public class PeriodsTableModel extends AbstractTableModel {

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
        return 4;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
            case 1:
                return Date.class;
            case 2:
                return Integer.class;
            default:
                return Double.class;
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "From";
            case 1:
                return "To";
            case 2:
                return "Offers";
            default:
                return "Sample price";
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
            case 2:
                return period.getItems().size();
            default:
                return period.getSamplePrice();
        }
    }
}
