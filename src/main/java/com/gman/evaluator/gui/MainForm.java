package com.gman.evaluator.gui;

import com.gman.evaluator.engine.Currency;
import com.gman.evaluator.engine.DataHolder;
import com.gman.evaluator.engine.Evaluation;
import com.gman.evaluator.engine.Item;
import com.gman.evaluator.engine.Items;
import com.gman.evaluator.engine.Parameter;
import com.gman.evaluator.engine.Parser;
import com.gman.evaluator.engine.ParserFactory;
import com.gman.evaluator.engine.Rule;
import com.gman.evaluator.engine.Rules;
import com.gman.evaluator.engine.parameters.Counter;
import com.gman.evaluator.engine.services.DataLoadingAndParsingService;
import com.gman.evaluator.engine.services.DataReadingService;
import com.gman.evaluator.engine.services.EvaluatingCarService;
import com.gman.evaluator.engine.services.EvaluatingService;
import com.gman.evaluator.engine.services.FilteringService;
import com.gman.evaluator.engine.services.OfferingService;
import com.gman.evaluator.engine.services.analitics.abnomal.DefaultAbnormalRemover;
import com.gman.evaluator.gui.components.ParameterCreator;
import com.gman.evaluator.gui.components.ParserCreator;
import com.gman.evaluator.gui.components.RuleCreator;
import com.gman.evaluator.gui.components.UrlCreator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author gman
 * @since 26.11.12 20:02
 */
public class MainForm extends JFrame {

    //holders
    private final DataHolder<DataLoadingAndParsingService.Config> dataLoadingAndParsingConfigHolder =
            new DataHolder<DataLoadingAndParsingService.Config>(true) {
                @Override
                protected DataLoadingAndParsingService.Config initialValue() {
                    return DataLoadingAndParsingService.Config.create(sources.getData(), parameters.getData(), parsers.getData());
                }
            };
    private final DataHolder<Rules> rulesHolder = new DataHolder<Rules>(true) {
        @Override
        protected Rules initialValue() {
            return new Rules(rules.getData());
        }
    };
    private final DataHolder<Items> allItemsHolder = new DataHolder<Items>() {
        @Override
        protected Items initialValue() {
            try {
                final int option = ComponentUtils.showOptionsDialog("Obtain data", "From disc", "From selected urls", "Restart");
                switch (option) {
                    case 0:
                        return dataReadingService.call();
                    case 1:
                        return dataLoadingAndParsingService.call();
                    default:
                        throw new IllegalArgumentException("Nothing selected!");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        protected void afterUpdate() {
            itemTableModel.setItems(get());
        }
    };
    private final DataHolder<Items> filteredItemsHolder = new DataHolder<Items>() {
        @Override
        protected Items initialValue() {
            try {
                return new DefaultAbnormalRemover().remove(allItemsHolder.get());
            } catch (Exception e) {
                throw  new RuntimeException(e);
            }
        }

        @Override
        protected void afterUpdate() {
            itemTableModel.setItems(get());
        }
    };
    private final DataHolder<Evaluation> evaluationHolder = new DataHolder<Evaluation>() {
        @Override
        protected Evaluation initialValue() {
            try {
                return evaluatingService.call();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        protected void afterUpdate() {
            evaluationsTableModel.setEvaluation(get());
        }
    };
    private final DataHolder<Items> offerItemsHolder = new DataHolder<Items>() {
        @Override
        protected void afterUpdate() {
            offersTableModel.setItems(get());
        }
    };

    private final JProcessableCallbackImpl processMonitor = new JProcessableCallbackImpl(null);

    //services
    private final DataReadingService dataReadingService = new DataReadingService();
    private final DataLoadingAndParsingService dataLoadingAndParsingService = new DataLoadingAndParsingService(processMonitor, dataLoadingAndParsingConfigHolder);
    private final FilteringService filteringService = new FilteringService(allItemsHolder);
    private final EvaluatingService evaluatingService = new EvaluatingService(filteredItemsHolder);
    private final OfferingService offeringService = new OfferingService(filteredItemsHolder, evaluationHolder, rulesHolder);
    private final EvaluatingCarService evaluatingCarService = new EvaluatingCarService(evaluationHolder);

    //forms
    private final CustomCarInput customCarInput = new CustomCarInput(this);
    private final AnalyzeForm analyzeForm = new AnalyzeForm(this);

    //model
    private final JPickListModel<Parser> parsers = new JPickListModel<Parser>();
    private final JPickListModel<String> sources = new JPickListModel<String>();
    private final JPickListModel<Parameter<?>> parameters = new JPickListModel<Parameter<?>>();
    private final ItemTableModel itemTableModel = new ItemTableModel();
    private final EvaluationsTableModel evaluationsTableModel = new EvaluationsTableModel();
    private final JPickListModel<Rule> rules = new JPickListModel<Rule>();
    private final ItemTableModel offersTableModel = new ItemTableModel();

    //actions
    private final LoadFromDiscAction loadFromDiscAction = new LoadFromDiscAction();
    private final LoadFromSourcesAction loadFromSourcesAction = new LoadFromSourcesAction();
    private final FilterAction filterAction = new FilterAction();
    private final EvaluateAction evaluateAction = new EvaluateAction();
    private final OfferAction offerAction = new OfferAction();
    private final AboutAction aboutAction = new AboutAction();
    private final ExitAction exitAction = new ExitAction();
    private final EvaluateCarAction evaluateCarAction = new EvaluateCarAction();
    private final AnalyzePriceAction analyzePriceAction = new AnalyzePriceAction();

    {
        parsers.addItem(ParserFactory.crete(getClass().getClassLoader().getResourceAsStream("auto_ria_ua.properties")));
        parsers.addItem(ParserFactory.crete(getClass().getClassLoader().getResourceAsStream("m_rst_ua.properties")));

        sources.addItem("http://m.rst.ua/oldcars/?make%5B%5D=49&model%5B%5D=142&body%5B%5D=0&body%5B%5D=0&year%5B%5D=0&year%5B%5D=0&price%5B%5D=0&price%5B%5D=0&region%5B%5D=0&engine%5B%5D=0&engine%5B%5D=0&fuel=0&gear=0&drive=&condition=0&task=newresults&from=sform&http://m.rst.ua/oldcars/?make%5B%5D=49&model%5B%5D=142&body%5B%5D=0&body%5B%5D=0&year%5B%5D=0&year%5B%5D=0&price%5B%5D=0&price%5B%5D=0&region%5B%5D=0&engine%5B%5D=0&engine%5B%5D=0&fuel=0&gear=0&drive=&condition=0&task=newresults&from=sform&start=${page}");

        parameters.addItem(new Counter("page", 1, 100));


    }

    public MainForm() {
        super();
        init();
    }

    private void init() {
        setTitle("Evaluator");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        final JMenuBar menu = new JMenuBar();
        menu.add(ComponentUtils.menu("Actions",
                ComponentUtils.activeElement(new JMenuItem("Load from disc"), loadFromDiscAction),
                ComponentUtils.activeElement(new JMenuItem("Load from sources"), loadFromSourcesAction),
                ComponentUtils.activeElement(new JMenuItem("Evaluate"), evaluateAction),
                ComponentUtils.activeElement(new JMenuItem("Offer"), offerAction),
                null,
                ComponentUtils.activeElement(new JMenuItem("Evaluate my car"), evaluateCarAction),
                ComponentUtils.activeElement(new JMenuItem("Analyze prices"), analyzePriceAction),
                null,
                ComponentUtils.activeElement(new JMenuItem("Exit"), exitAction)
        ));
        menu.add(ComponentUtils.menu("Exchange rate",
                ComponentUtils.activeElement(new JMenuItem("for USD"), new ExchangeRateAction(Currency.USD)),
                ComponentUtils.activeElement(new JMenuItem("for EUR"), new ExchangeRateAction(Currency.EUR)),
                ComponentUtils.activeElement(new JMenuItem("for UAH"), new ExchangeRateAction(Currency.UAH))
        ));
        menu.add(ComponentUtils.menu("About",
                ComponentUtils.activeElement(new JMenuItem("About"), aboutAction)
        ));
        setJMenuBar(menu);

        final JTabbedPane pane = new JTabbedPane();
        pane.add("Parsers", new JPickList<Parser>(parsers, new ParserCreator(), new ParserOperation()));
        pane.add("Sources", new JPickList<String>(sources, new UrlCreator(this)));
        pane.add("Params", new JPickList<Parameter<?>>(parameters, new ParameterCreator(this)));
        pane.add("Items", new JItemsViewer(itemTableModel));
        pane.add("Evaluations", ComponentUtils.table(evaluationsTableModel));
        pane.add("Rules", new JPickList<Rule>(rules, new RuleCreator(this)));
        pane.add("Offers", new JItemsViewer(offersTableModel));
        getContentPane().add(pane, BorderLayout.CENTER);

        final JPanel controls = new JPanel(new GridLayout(1, 5));
        controls.add(ComponentUtils.activeElement(new JButton("Load from disc"), loadFromDiscAction));
        controls.add(ComponentUtils.activeElement(new JButton("Load from sources"), loadFromSourcesAction));
        controls.add(ComponentUtils.activeElement(new JButton("Filter"), filterAction));
        controls.add(ComponentUtils.activeElement(new JButton("Evaluate"), evaluateAction));
        controls.add(ComponentUtils.activeElement(new JButton("Offer"), offerAction));
        getContentPane().add(controls, BorderLayout.SOUTH);

        setPreferredSize(new Dimension(900, 600));
        pack();
    }

    public JPickListModel<Parser> getParsers() {
        return parsers;
    }

    public ItemTableModel getItemTableModel() {
        return itemTableModel;
    }

    public DataHolder<Items> getAllItemsHolder() {
        return allItemsHolder;
    }

    private final class LoadFromDiscAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new ComponentUtils.BackgroundProcessable<Items>(dataReadingService) {
                @Override
                public void setResult() {
                    MainForm.this.allItemsHolder.set(getSuccessFullResult());
                }
            }.execute();
        }
    }

    private final class LoadFromSourcesAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new ComponentUtils.BackgroundProcessable<Items>(dataLoadingAndParsingService) {
                @Override
                public void setResult() {
                    MainForm.this.allItemsHolder.set(getSuccessFullResult());
                }
            }.execute();
        }
    }

    private final class FilterAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new ComponentUtils.BackgroundProcessable<Items>(filteringService) {
                @Override
                public void setResult() {
                    MainForm.this.filteredItemsHolder.set(getSuccessFullResult());
                }
            }.execute();
        }
    }

    private final class EvaluateAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new ComponentUtils.BackgroundProcessable<Evaluation>(evaluatingService) {
                @Override
                public void setResult() {
                    MainForm.this.evaluationHolder.set(getSuccessFullResult());
                }
            }.execute();
        }
    }

    private final class OfferAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new ComponentUtils.BackgroundProcessable<Items>(offeringService) {
                @Override
                public void setResult() {
                    MainForm.this.offerItemsHolder.set(getSuccessFullResult());
                }
            }.execute();
        }
    }

    private final class AboutAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ComponentUtils.showMessage("Created by gman. \n mailto : gmandnepr@gmail.com");
        }
    }

    private final class ExitAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            MainForm.this.setVisible(false);
            MainForm.this.dispose();
        }
    }

    private final class EvaluateCarAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            customCarInput.setVisible(true);
            final Item itemToEvaluate = customCarInput.getCreatedItem();
            if (itemToEvaluate != null) {
                evaluatingCarService.setItem(itemToEvaluate);
                try {
                    final long estimatedPrice = Math.round(evaluatingCarService.call());
                    ComponentUtils.showMessage("Estimated price is: " + estimatedPrice);
                } catch (Exception e1) {
                    ComponentUtils.showErrorDialog(e1);
                }
            }
        }
    }

    private final class AnalyzePriceAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            analyzeForm.setVisible(true);
        }
    }

    private final class ParserOperation implements JPickListItemOperation<Parser> {
        @Override
        public void performed(Parser item) {
            Browser.openURL(item.getProperties().getSearch());
        }
    }

    private final class ExchangeRateAction implements ActionListener {

        private final Currency currency;

        private ExchangeRateAction(Currency currency) {
            this.currency = currency;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            final String toParse = JOptionPane.showInputDialog(null,
                    "Current rate is " + currency.getExchangeRate(),
                    "Set exchange rate for " + currency.toString(),
                    JOptionPane.QUESTION_MESSAGE);
            if (toParse != null) {
                try {
                    currency.setExchangeRate(Double.parseDouble(toParse));
                } catch (NumberFormatException ex) {
                    ComponentUtils.showMessage("Failed to parse number");
                }
            }
        }
    }
}
