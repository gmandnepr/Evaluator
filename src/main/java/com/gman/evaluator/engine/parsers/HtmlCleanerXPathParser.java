package com.gman.evaluator.engine.parsers;

import com.gman.evaluator.engine.FieldDefinition;
import com.gman.evaluator.engine.Item;
import com.gman.evaluator.engine.ItemImpl;
import com.gman.evaluator.engine.Parser;
import com.gman.evaluator.engine.ParserProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

/**
 * @author gman
 * @since 09.12.12 22:12
 */
public class HtmlCleanerXPathParser implements Parser {

    private final HtmlCleaner cleaner = new HtmlCleaner();
    private final Set<FieldDefinition> fieldDefinitions = new HashSet<FieldDefinition>();
    private ParserProperties properties;


    @Override
    public void init(ParserProperties properties) {
        this.properties = properties;
        this.fieldDefinitions.clear();
        this.fieldDefinitions.addAll(properties.getFieldDefinitions());
    }

    @Override
    public ParserProperties getProperties() {
        return properties;
    }

    @Override
    public List<Item> parse(String content) {

        final List<Item> items = new ArrayList<Item>();

        final TagNode node = cleaner.clean(content);

        for (int i = 1; i < Integer.MAX_VALUE; i++) {
            try {
                final ItemImpl item = new ItemImpl();

                for (FieldDefinition fieldDefinition : fieldDefinitions) {

                    final String xpath = String.format(fieldDefinition.getLocation(), i, i);
                    final Object[] values = node.evaluateXPath(xpath);
                    if (values.length != 1) {
                        continue;
                    }

                    fieldDefinition.getFieldType().inject(item, fieldDefinition.getName(), values[0]);

                    if (!item.isEvaluable()) {
                        break;
                    }
                }
                if (item.getDeclaredPropertiesCount() == 0) {
                    break;
                }

                if (item.isEvaluable()) {
                    items.add(item);
                }
            } catch (XPatherException e) {
                Logger.getLogger(HtmlCleanerXPathParser.class.getName()).warning(e.getMessage());
            }

        }

        return items;
    }

    @Override
    public String toString() {
        return properties.getTarget();
    }
}
