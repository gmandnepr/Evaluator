package com.gman.evaluator.gui.components;

import com.gman.evaluator.engine.Parser;
import com.gman.evaluator.engine.UrlUtils;
import com.gman.evaluator.gui.ComponentCreator;
import com.gman.evaluator.gui.MainForm;

/**
 * @author gman
 * @since 11/27/12 10:06 AM
 */
public class UrlCreator extends AbstractFromStringCreator implements ComponentCreator<String> {

    public UrlCreator(MainForm parent) {
        super(parent);
    }

    @Override
    public String create() {
        return addPagination(askForString("Enter url"));
    }

    @Override
    public String update(String old) {
        return addPagination(askForString("Enter url", old));
    }

    private String addPagination(String url) {
        final Parser parser = findParser(url);
        if (parser != null) {
            final String paginationParameter = parser.getProperties().getPagination();
            if (!paginationParameter.isEmpty() && !url.contains(paginationParameter)) {
                url = UrlUtils.addGetVariable(url, paginationParameter, "${page}");
            }
        }
        return url;
    }

    private Parser findParser(String url) {
        final String site = UrlUtils.extractSite(url);
        for (final Parser parser : getParent().getParsers().getData()) {
            if (site.equalsIgnoreCase(parser.getProperties().getTarget())) {
                return parser;
            }
        }
        return null;
    }
}
