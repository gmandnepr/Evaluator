package com.gman.evaluator.engine.parameters;

import com.gman.evaluator.engine.Parameter;

import java.io.Serializable;

/**
 * @author gman
 * @since 11/27/12 12:27 PM
 */
public abstract class AbstractParameter<T> implements Parameter<T>, Serializable {

    private static final long serialVersionUID = 1L;

    private final String name;

    protected AbstractParameter(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final AbstractParameter that = (AbstractParameter) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return name + "=";
    }
}
