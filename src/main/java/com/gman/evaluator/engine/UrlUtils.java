package com.gman.evaluator.engine;

/**
 * @author gman
 * @since 01.12.12 19:48
 */
public final class UrlUtils {

    private UrlUtils() {
    }

    public static String extractSite(String url) {
        final String prefixToRemove = "http://www.";
        final String source = url.toLowerCase();
        int prefix = 0;
        while (prefix < prefixToRemove.length() &&
                prefixToRemove.charAt(prefix) == source.charAt(prefix)) {
            prefix++;
        }
        int suffix = source.indexOf('/', prefix + 1);
        if (suffix != -1) {
            return source.substring(prefix, suffix);
        } else {
            return source.substring(prefix);
        }
    }

    public static String addGetVariable(String url, String varName, String varValue) {
        final StringBuilder newUrl = new StringBuilder(url);
        newUrl.append(url.contains("?") ? newUrl.append('&') : newUrl.append('?'));
        newUrl.append(varName);
        newUrl.append('=');
        newUrl.append(varValue);
        return newUrl.toString();
    }
}
