package com.gman.evaluator.gui;

import com.gman.evaluator.engine.DataHolder;
import com.gman.evaluator.engine.Items;
import com.gman.evaluator.engine.ProcessableCallback;
import com.gman.evaluator.engine.services.analitics.AnalyticsConfig;
import com.gman.evaluator.engine.services.analitics.AnalyticsResult;
import com.gman.evaluator.engine.services.analitics.AnalyticsService;
import com.gman.evaluator.engine.services.analitics.evaluation.EvaluatorFactory;
import com.gman.evaluator.engine.services.analitics.separator.ItemsSeparatorFactory;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author gman
 * @since 04.03.13 22:08
 */
public class AnalyzeForm extends JDialog {

    private final DataHolder<AnalyticsConfig> analyticsConfigHolder = new DataHolder<AnalyticsConfig>(true) {
        @Override
        protected AnalyticsConfig initialValue() {
            final ItemsSeparatorFactory separatorFactory = (ItemsSeparatorFactory) dataSeparatorWays.getSelectedItem();
            final EvaluatorFactory evaluatorFactory = (EvaluatorFactory) dataEvaluationWays.getSelectedItem();

            return new AnalyticsConfig(separatorFactory.create(), evaluatorFactory.create(), itemCreator.createItem());
        }
    };
    private final DataHolder<Items> itemsHolder;

    private final AnalyticsService analyticsService;

    private final JComboBox<ItemsSeparatorFactory> dataSeparatorWays = new JComboBox<ItemsSeparatorFactory>(ItemsSeparatorFactory.values());
    private final JComboBox<EvaluatorFactory> dataEvaluationWays = new JComboBox<EvaluatorFactory>(EvaluatorFactory.values());
    private final JItemCreator itemCreator = new JItemCreator();
    private final PeriodsTableModel periodsTableModel = new PeriodsTableModel();
    private final OffersChartModel offersChartModel = new OffersChartModel();
    private final PriceChartModel priceChartModel = new PriceChartModel();

    private final AnalyticsAction analyticsAction = new AnalyticsAction();
    private final ResetAction resetAction = new ResetAction();

    public AnalyzeForm(MainForm owner) {
        super(owner, true);

        itemsHolder = owner.getAllItemsHolder();
        analyticsService = new AnalyticsService(ProcessableCallback.EMPTY, itemsHolder, analyticsConfigHolder);

        initComponents();
    }

    private void initComponents() {
        setTitle("Analyzer");
        getContentPane().setLayout(new BorderLayout());

        dataSeparatorWays.setBorder(new TitledBorder("Data separation"));
        dataEvaluationWays.setBorder(new TitledBorder("Data evaluation"));

        final JPanel configuration = new JPanel(new GridLayout(3, 1));
        configuration.add(dataSeparatorWays);
        configuration.add(dataEvaluationWays);
        configuration.add(itemCreator);

        final JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Configuration", configuration);
        tabbedPane.addTab("Separated items", ComponentUtils.table(periodsTableModel));
        tabbedPane.addTab("Offers", ComponentUtils.chart(offersChartModel));
        tabbedPane.addTab("Price", ComponentUtils.chart(priceChartModel));
        getContentPane().add(tabbedPane, BorderLayout.CENTER);

        final JPanel controls = new JPanel(new GridLayout(1, 4));
        controls.add(ComponentUtils.activeElement(new JButton("Analyze"), analyticsAction));
        controls.add(ComponentUtils.activeElement(new JButton("Reset"), resetAction));
        getContentPane().add(controls, BorderLayout.SOUTH);

        setPreferredSize(new Dimension(600, 400));
        pack();
    }


    private final class AnalyticsAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new ComponentUtils.BackgroundProcessable<AnalyticsResult>(analyticsService) {
                @Override
                public void setResult() {
                    final AnalyticsResult result = getSuccessFullResult();
                    periodsTableModel.setPeriods(result.getPeriods());
                    offersChartModel.setPeriods(result.getPeriods());
                    priceChartModel.setPeriods(result.getPeriods());
                }
            }.execute();
        }
    }

    private final class ResetAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            periodsTableModel.clear();
            offersChartModel.clear();
            priceChartModel.clear();
        }
    }


}
