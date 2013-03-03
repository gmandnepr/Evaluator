package com.gman.evaluator.engine;

/**
 * @author gman
 * @since 03.03.13 17:25
 */
public class DataHolder<T> {

    private final boolean autoGenerated;
    private T ref = null;

    public DataHolder() {
        this(false);
    }

    public DataHolder(boolean autoGenerated) {
        this.autoGenerated = autoGenerated;
    }

    protected T initialValue() {
        return null;
    }

    protected void afterUpdate() {

    }

    public T get() {
        if (autoGenerated || ref == null) {
            set(initialValue());
        }
        return ref;
    }

    public void set(T ref) {
        this.ref = ref;
        afterUpdate();
    }
}
