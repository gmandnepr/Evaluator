package com.gman.evaluator.engine;

import java.io.Serializable;

/**
 * @author gman
 * @since 09.12.12 22:57
 */
public class FieldDefinition implements Serializable {

    private final String name;
    private final FieldType fieldType;
    private final String location;

    public FieldDefinition(String name, FieldType fieldType) {
        this(name, fieldType, null);
    }

    public FieldDefinition(String name, FieldType fieldType, String location) {
        this.name = name;
        this.fieldType = fieldType;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final FieldDefinition that = (FieldDefinition) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return getName();
    }
}
