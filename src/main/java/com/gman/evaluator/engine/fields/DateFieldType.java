package com.gman.evaluator.engine.fields;

import com.gman.evaluator.engine.Item;
import com.gman.evaluator.engine.ItemImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author gman
 * @since 22.12.12 13:36
 */
public class DateFieldType extends AbstractFieldType {

    public static final String DEFAULT_DATE_FORMAT = "dd.MM.yyyy";

    private String format;

    @Override
    public void setProperties(String properties) {
        if (properties != null && !properties.isEmpty()) {
            this.format = properties;
        } else {
            this.format = DEFAULT_DATE_FORMAT;
        }
    }

    @Override
    public Class<?> type() {
        return Date.class;
    }

    @Override
    public Object retrieve(Item item, String propertyName) {
        return item.getDate();
    }

    @Override
    public void inject(ItemImpl item, String propertyName, Object value) {
        try {
            item.setDate(parseDate(value));
        } catch (ParseException e) {
            item.setDate(new Date());
        }
    }

    protected Date parseSpecialDate(String text) {
        return checkForSpecialDates(text);
    }

    protected String extractDate(String text) {
        return text;
    }

    protected Date parseDate(Object value) throws ParseException {
        final String text = value.toString();
        final Date specialDate = parseSpecialDate(text);
        if (specialDate != null) {
            return specialDate;
        } else {
            final SimpleDateFormat formatter = new SimpleDateFormat(format);
            return formatter.parse(extractDate(text));
        }
    }

    private static final String TODAY = "сегодня";
    private static final String YESTERDAY = "вчера";

    private static final Date TODAY_DATE = new Date(System.currentTimeMillis());
    private static final Date YESTERDAY_DATE = new Date(System.currentTimeMillis() - 24 * 3600 * 1000);

    protected static Date getToday() {
        return new Date(TODAY_DATE.getTime());
    }

    protected static Date getYesterday() {
        return new Date(YESTERDAY_DATE.getTime());
    }

    protected static boolean isToday(String text) {
        return text.toLowerCase().contains(TODAY);
    }

    protected static boolean isYesterday(String text) {
        return text.toLowerCase().contains(YESTERDAY);
    }

    protected static Date checkForSpecialDates(String text) {
        if (isToday(text)) {
            return getToday();
        }
        if (isYesterday(text)) {
            return getYesterday();
        }
        return null;
    }
}
