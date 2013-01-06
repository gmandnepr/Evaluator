package com.gman.evaluator.engine;

import java.util.Map;

/**
 * @author gman
 * @since 01.12.12 22:30
 */
public class Pair<K, V> implements Map.Entry<K, V> {

    private final K key;
    private final V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getFirst() {
        return this.key;
    }

    public V getSecond() {
        return this.value;
    }

    @Override
    public K getKey() {
        return this.key;
    }

    @Override
    public V getValue() {
        return this.value;
    }

    @Override
    public V setValue(V value) {
        return this.value;
    }
}
