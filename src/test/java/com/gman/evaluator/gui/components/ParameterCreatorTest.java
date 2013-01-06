package com.gman.evaluator.gui.components;

import com.gman.evaluator.engine.Parameter;
import com.gman.evaluator.engine.parameters.Counter;
import com.gman.evaluator.engine.parameters.StringSet;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * @author gman
 * @since 03.12.12 20:05
 */
public class ParameterCreatorTest {

    @Test
    public void testCounter() {
        final Counter original = new Counter("c", 1, 10);
        final String str = ParameterCreator.parameterToString(original);
        final Parameter<?> recreated = ParameterCreator.stringToParameter("c", str);

        assertTrue(recreated instanceof Counter);
        assertEquals(original.getFrom(), ((Counter) recreated).getFrom());
        assertEquals(original.getTo(), ((Counter) recreated).getTo());
    }

    @Test
    public void testStringSet() {
        final StringSet original = new StringSet("c", new HashSet<String>(Arrays.asList("a", "b", "c")));
        final String str = ParameterCreator.parameterToString(original);
        final Parameter<?> recreated = ParameterCreator.stringToParameter("c", str);

        assertTrue(recreated instanceof StringSet);
        final Set<String> set = ((StringSet) recreated).getStrings();
        assertEquals(3, set.size());
        assertTrue(set.contains("a"));
        assertTrue(set.contains("b"));
        assertTrue(set.contains("c"));
    }
}
