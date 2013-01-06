package com.gman.evaluator.engine.rules;


import com.gman.evaluator.engine.FieldDefinition;
import com.gman.evaluator.engine.ItemImpl;
import com.gman.evaluator.engine.Rule;
import com.gman.evaluator.engine.fields.NameFieldType;
import com.gman.evaluator.engine.fields.NumericFieldType;
import com.gman.evaluator.engine.fields.PriceFieldType;
import com.gman.evaluator.engine.rules.operations.ContainsOperation;
import com.gman.evaluator.engine.rules.operations.LessOperation;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author gman
 * @since 03.12.12 18:55
 */
public class RuleImplTest {

    private static ItemImpl item1;
    private static ItemImpl item2;


    @BeforeClass
    public static void setUp() {
        item1 = new ItemImpl();
        item1.setName("used car");
        item1.setPrice(1000.0);
        item1.setProperty("distance", 100500);

        item2 = new ItemImpl();
        item2.setName("new car");
        item2.setPrice(10000.0);
        item2.setProperty("distance", 0);
    }

    @Test
    public void testOnDefaultProperty() {
        final Rule rule1 = new RuleImpl(new FieldDefinition("distance", new NumericFieldType()), new LessOperation(), "5000");
        assertFalse(rule1.satisfy(item1));
        assertTrue(rule1.satisfy(item2));
    }

    @Test
    public void testOnPrice() {
        final Rule rule1 = new RuleImpl(new FieldDefinition("price", new PriceFieldType()), new LessOperation(), "5000");
        assertTrue(rule1.satisfy(item1));
        assertFalse(rule1.satisfy(item2));
    }

    @Test
    public void testOnName() {
        final Rule rule = new RuleImpl(new FieldDefinition("name", new NameFieldType()), new ContainsOperation(), "used");
        assertTrue(rule.satisfy(item1));
        assertFalse(rule.satisfy(item2));
    }
}
