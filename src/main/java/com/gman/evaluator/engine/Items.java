package com.gman.evaluator.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author gman
 * @since 01.12.12 19:10
 */
public class Items extends ArrayList<Item> {

    private static final long serialVersionUID = 1L;

    private final Set<FieldDefinition> fieldDefinitions = new HashSet<FieldDefinition>();

    public void registerProperty(FieldDefinition fieldDefinition) {
        this.fieldDefinitions.add(fieldDefinition);
    }

    public void registerProperties(Set<FieldDefinition> fieldDefinitions) {
        this.fieldDefinitions.addAll(fieldDefinitions);
    }

    public Set<FieldDefinition> getRegisteredFields() {
        return Collections.unmodifiableSet(fieldDefinitions);
    }

    public void clearRegisteredProperties() {
        fieldDefinitions.clear();
    }

    public Set<String> getDeclaredProperties() {
        return !isEmpty() ? get(0).getDeclaredProperties() : Collections.<String>emptySet();
    }

    public Items copyStructure() {
        final Items copy = new Items();
        copy.registerProperties(getRegisteredFields());
        return copy;
    }

    public Items copyAll() {
        final Items copy = copyStructure();
        copy.addAll(this);
        return copy;
    }

    public static final Items EMPTY = new Items() {

        @Override
        public void registerProperty(FieldDefinition fieldDefinition) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void registerProperties(Set<FieldDefinition> fieldDefinitions) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean add(Item item) {
            throw new UnsupportedOperationException();
        }
    };
}
