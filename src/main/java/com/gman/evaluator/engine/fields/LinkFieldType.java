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
        String theLink = extractString(value);
        if (theLink.startsWith("href=\"")) {
            theLink = theLink.substring(6);
        }
        if (theLink.endsWith("\"")) {
            theLink = theLink.substring(0, theLink.length() - 1);
        }
        item.setLink(this.link + theLink);
    }

}
