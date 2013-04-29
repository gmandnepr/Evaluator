package com.gman.evaluator.gui;

import com.gman.evaluator.engine.Evaluation;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author gman
 * @since 26.11.12 21:31
 */
public class EvaluationsTableModel extends AbstractTableModel {

    private final List<Map.Entry<String, Double>> fieldAndPrice = new ArrayList<Map.Entry<String, Double>>();
    private Evaluation evaluation = null;

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
        this.fieldAndPrice.clear();
        this.fieldAndPrice.addAll(evaluation.getFieldsAndCoefficients());
        fireTableDataChanged();
    }

    public Evaluation getEvaluation() {
        return evaluation;
    }

    public int getRowCount() {
        return fieldAndPrice.size();
    }

    public int getColumnCount() {
        return 2;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "field";
            default:
                return "price";
        }
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return fieldAndPrice.get(rowIndex).getKey();
            default:
                return fieldAndPrice.get(rowIndex).getValue();
        }
    }
}
