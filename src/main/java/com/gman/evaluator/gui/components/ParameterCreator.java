package com.gman.evaluator.gui.components;

import com.gman.evaluator.engine.Parameter;
import com.gman.evaluator.engine.parameters.Counter;
import com.gman.evaluator.engine.parameters.StringSet;
import com.gman.evaluator.gui.ComponentCreator;
import com.gman.evaluator.gui.MainForm;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

/**
 * @author gman
 * @since 03.12.12 18:05
 */
public class ParameterCreator extends AbstractFromStringCreator implements ComponentCreator<Parameter<?>> {

    public ParameterCreator(MainForm parent) {
        super(parent);
    }

    @Override
    public Parameter<?> create() {
        final String name = askForString("Enter name");
        final String definition = askForString("Enter definition");
        return stringToParameter(name, definition);
    }

    @Override
    public Parameter<?> update(Parameter<?> old) {
        final String name = old.getName();
        final String definition = askForString("Enter definition", parameterToString(old));
        return stringToParameter(name, definition);
    }

    public static Parameter<?> stringToParameter(String name, String source) {
        if (source.contains("-")) {
            final String[] elements = source.split("-");
            final int a = Integer.parseInt(elements[0]);
            final int b = Integer.parseInt(elements[1]);
            return new Counter(name, a, b - a);
        } else {
            final String[] elements = source.split(",");
            return new StringSet(name, new HashSet<String>(Arrays.asList(elements)));
        }
    }

    public static String parameterToString(Parameter<?> source) {
        if (source instanceof Counter) {
            final Counter c = (Counter) source;
            return c.getFrom() + "-" + (c.getFrom() + c.getTo());
        } else if (source instanceof StringSet) {
            final StringBuilder sb = new StringBuilder();
            final Iterator<String> iter = ((StringSet) source).iterator();
            while (iter.hasNext()) {
                sb.append(iter.next());
                if (iter.hasNext()) {
                    sb.append(',');
                }
            }
            return sb.toString();
        } else {
            return "";
        }
    }

}
