package ru.job4j.cache;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractCache<K, V> {
    protected final Map<K, SoftReference<V>> cache = new HashMap<>();

    public void put(K key, V value) {
        cache.put(key, new SoftReference<V>(value));
    }

    public V get(K key) {
        if (!cache.containsKey(key)) {
            V loadFile = load(key);
            put(key, loadFile);
        }
        V value = cache.get(key).get();
        System.out.println(value);
        if (value == null) {
            V loadFile = load(key);
            put(key, loadFile);
        }
        return value;
    }

    protected abstract V load(K key);
}
