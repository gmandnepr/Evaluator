package com.gman.evaluator.gui;

import com.gman.evaluator.engine.DataHolder;
import com.gman.evaluator.engine.Items;
import com.gman.evaluator.engine.ProcessableCallback;
import com.gman.evaluator.engine.services.analitics.AnalyticsConfig;
import com.gman.evaluator.engine.services.analitics.AnalyticsResult;
import com.gman.evaluator.engine.services.analitics.AnalyticsService;
import com.gman.evaluator.engine.services.analitics.ItemsSeparatorFactory;

import javax.swing.*;
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
            final ItemsSeparatorFactory factory = (ItemsSeparatorFactory) dataSeparatorWays.getSelectedItem();

            return new AnalyticsConfig(factory.create());
        }
    };
    private final DataHolder<Items> itemsHolder;

    private final AnalyticsService analyticsService;

    private final JComboBox<ItemsSeparatorFactory> dataSeparatorWays = new JComboBox<ItemsSeparatorFactory>(ItemsSeparatorFactory.values());
    private final SeparatedItemsTableModel separatedItemsTableModel = new SeparatedItemsTableModel();

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

        final JPanel configurationPanel = new JPanel(new GridLayout(10, 2));
        configurationPanel.add(new JLabel("Data separation"));
        configurationPanel.add(dataSeparatorWays);
        for (int i = 2; i < 20; i++) {
            configurationPanel.add(new JLabel("" + i));
        }


        final JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Configuration", configurationPanel);
        tabbedPane.addTab("Separated items", ComponentUtils.table(separatedItemsTableModel));
        getContentPane().add(tabbedPane, BorderLayout.CENTER);

        final JPanel controls = new JPanel(new GridLayout(1, 4));
        controls.add(ComponentUtils.activeElement(new JButton("Analyze"), analyticsAction));
        controls.add(ComponentUtils.activeElement(new JButton("Reset"), resetAction));
        getContentPane().add(controls, BorderLayout.SOUTH);

        setPreferredSize(new Dimension(900, 600));
        pack();
    }


    private final class AnalyticsAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new ComponentUtils.BackgroundProcessable<AnalyticsResult>(analyticsService) {
                @Override
                public void setResult() {
                    final AnalyticsResult result = getSuccessFullResult();
                    separatedItemsTableModel.setPeriods(result.getPeriods());
                }
            }.execute();
        }
    }

    private final class ResetAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            separatedItemsTableModel.clear();
        }
    }


}
