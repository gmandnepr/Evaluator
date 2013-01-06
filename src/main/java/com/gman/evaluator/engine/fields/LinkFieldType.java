package com.gman.evaluator.engine.fields;

import com.gman.evaluator.engine.Item;
import com.gman.evaluator.engine.ItemImpl;

/**
 * @author gman
 * @since 10.12.12 21:00
 */
public class LinkFieldType extends TextFieldType {

    private String link;

    public LinkFieldType() {
        this("");
    }

    public LinkFieldType(String link) {
        this.link = link;
    }

    @Override
    public void setProperties(String properties) {
        this.link = properties;
    }

    @Override
    public Object retrieve(Item item, String propertyName) {
        return item.getLink();
    }

    @Override
    public void inject(ItemImpl item, String propertyName, Object value) {
        String link = extractString(value);
        if (link.startsWith("href=\"")) {
            link = link.substring(6);
        }
        if (link.endsWith("\"")) {
            link = link.substring(0, link.length() - 1);
        }
        item.setLink(this.link + link);
    }

}
