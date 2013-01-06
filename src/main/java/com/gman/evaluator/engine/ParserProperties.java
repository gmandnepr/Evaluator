package com.gman.evaluator.engine;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * @author gman
 * @since 11/29/12 9:45 AM
 */
public class ParserProperties extends Properties {

    private static final String PREFIX = "parser.";
    private static final String LOCATION = ".location";
    private static final String TYPE = ".type";
    private static final String PROPERTIES = ".properties";

    private static final String TARGET_PROPERTY = PREFIX + "target";
    private static final String SEARCH_PROPERTY = PREFIX + "search";
    private static final String PAGINATION_PROPERTY = PREFIX + "pagination";
    private static final String TYPE_PROPERTY = PREFIX + "type";
    private static final String FIELDS_PROPERTY = PREFIX + "fields";
    private static final String FIELDS_SEPARATOR = ",";

    private final Set<String> fields = new HashSet<String>();
    private final Set<FieldDefinition> fieldDefinitions = new HashSet<FieldDefinition>();

    public String getType() {
        return getProperty(TYPE_PROPERTY);
    }

    public String getTarget() {
        return getProperty(TARGET_PROPERTY);
    }

    public String getSearch() {
        return getProperty(SEARCH_PROPERTY);
    }

    public String getPagination() {
        return getProperty(PAGINATION_PROPERTY);
    }

    public Set<String> getFields() {
        synchronized (fields) {
            if (fields.isEmpty()) {
                final String fieldsProperty = getProperty(FIELDS_PROPERTY);
                fields.addAll(Arrays.asList(fieldsProperty.split(FIELDS_SEPARATOR)));
            }
        }
        return fields;
    }

    public Set<FieldDefinition> getFieldDefinitions() {
        synchronized (fieldDefinitions) {
            if (fieldDefinitions.isEmpty()) {
                for (String name : getFields()) {
                    final FieldType fieldType = FieldTypeFactory.create(
                            getProperty(PREFIX + name + TYPE),
                            getProperty(PREFIX + name + PROPERTIES));
                    final String location = getProperty(PREFIX + name + LOCATION);
                    fieldDefinitions.add(new FieldDefinition(name, fieldType, location));
                }
            }
        }
        return fieldDefinitions;
    }

}
