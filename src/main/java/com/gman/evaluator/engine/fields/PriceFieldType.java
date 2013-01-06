package com.gman.evaluator.engine.fields;

import com.gman.evaluator.engine.Currency;
import com.gman.evaluator.engine.Item;
import com.gman.evaluator.engine.ItemImpl;

/**
 * @author gman
 * @since 10.12.12 21:03
 */
public class PriceFieldType extends NumericFieldType {

    private Currency currency;

    @Override
    public void setProperties(String properties) {
        if (properties != null) {
            this.currency = Currency.valueOf(properties);
        } else {
            this.currency = Currency.USD;
        }
    }

    @Override
    public Object retrieve(Item item, String propertyName) {
        return item.getPrice();
    }

    @Override
    public void inject(ItemImpl item, String propertyName, Object value) {
        final String clearPrice = clearPrice(value.toString());
        try {
            item.setPrice(Double.parseDouble(clearPrice) * currency.getExchangeRate());
        } catch (NumberFormatException e) {
            item.setPrice(Double.NaN);
        }
    }

    private String clearPrice(String price) {
        return price.replace("$", "").replace("'", "").replace(" ", "");
    }
}
