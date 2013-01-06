package com.gman.evaluator.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author gman
 * @since 11/27/12 12:31 PM
 */
public class UrlGenerator implements Iterable<String> {

    private static final String VARIABLE_START = "${";
    private static final String VARIABLE_END = "}";

    private final String urlPattern;
    private final List<String> urlParts = new ArrayList<String>();
    private final List<Parameter<?>> orderedParams = new ArrayList<Parameter<?>>();

    public UrlGenerator(String urlPattern) {
        this(urlPattern, Collections.<Parameter<?>>emptyList());
    }

    public UrlGenerator(String urlPattern, List<Parameter<?>> params) {
        this.urlPattern = urlPattern.toLowerCase();
        analyze(params);
    }

    public String getUrlPattern() {
        return urlPattern;
    }

    public String getSite() {
        return UrlUtils.extractSite(urlPattern);
    }

    public int urlsCount() {
        int count = 1;
        for (Parameter<?> p : orderedParams) {
            count *= p.items();
        }
        return count;
    }

    private void analyze(List<Parameter<?>> params) {
        final Map<String, Parameter<?>> paramsIndex = new HashMap<String, Parameter<?>>();
        for (final Parameter<?> p : params) {
            paramsIndex.put(p.getName(), p);
        }

        int previousVarEndPos = -1;
        int varStartPos = urlPattern.indexOf(VARIABLE_START);
        while (true) {
            if (varStartPos == -1) {
                final String urlEndPart = urlPattern.substring(previousVarEndPos + 1, urlPattern.length());
                urlParts.add(urlEndPart);
                break;
            }
            final int varEndPos = urlPattern.indexOf(VARIABLE_END, varStartPos);
            final String paramName = urlPattern.substring(varStartPos + VARIABLE_START.length(), varEndPos);
            final String urlPart = urlPattern.substring(previousVarEndPos + VARIABLE_END.length(), varStartPos);
            urlParts.add(urlPart);
            final Parameter<?> param = paramsIndex.get(paramName);
            if (param == null) {
                throw new IllegalStateException("Param `" + paramName + "` is not defined!");
            }
            orderedParams.add(param);
            previousVarEndPos = varEndPos;
            varStartPos = urlPattern.indexOf(VARIABLE_START, varStartPos + 1);
        }
    }

    @Override
    public Iterator<String> iterator() {
        return new Iterator<String>() {

            boolean hasNext = true;
            private final int paramsNumber;
            private final List<Iterator<?>> iterators = new ArrayList<Iterator<?>>(orderedParams.size());
            private final List<String> values = new ArrayList<String>(orderedParams.size());

            {
                paramsNumber = orderedParams.size();
                for (int i = 0; i < paramsNumber; i++) {
                    final Iterator<?> paramIterator = orderedParams.get(i).iterator();
                    iterators.add(paramIterator);
                    values.add(paramIterator.next().toString());
                }
            }

            private String buildUrl() {
                final StringBuilder sb = new StringBuilder();
                for (int i = 0; i < paramsNumber; i++) {
                    sb.append(urlParts.get(i));
                    sb.append(values.get(i));
                }
                sb.append(urlParts.get(paramsNumber));
                return sb.toString();
            }

            private void generateNext() {
                int valueToIncrement = paramsNumber - 1;
                while (valueToIncrement > -1 && !iterators.get(valueToIncrement).hasNext()) {
                    valueToIncrement--;
                }
                if (valueToIncrement > -1) {
                    values.set(valueToIncrement, iterators.get(valueToIncrement).next().toString());
                    for (int i = valueToIncrement + 1; i < paramsNumber; i++) {
                        final Iterator<?> paramIterator = orderedParams.get(i).iterator();
                        iterators.set(i, paramIterator);
                        values.set(i, paramIterator.next().toString());
                    }
                } else {
                    hasNext = false;
                }
            }

            @Override
            public boolean hasNext() {
                return hasNext;
            }

            @Override
            public String next() {

                final String url = buildUrl();
                generateNext();
                return url;
            }

            @Override
            public void remove() {
            }
        };
    }
}
