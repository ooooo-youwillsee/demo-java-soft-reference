package com.ooooo.demo;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="https://github.com/ooooo-youwillsee">ooooo</a>
 * @since 1.0.0
 */
public class WeakReferenceCache<K, V> implements Cache<K, V> {

    private Map<K, WeakValue<V>> map;

    private ReferenceQueue<V> referenceQueue;

    public WeakReferenceCache() {
        this.map = new HashMap<>();
        this.referenceQueue = new ReferenceQueue<>();
    }

    @Override
    public void put(K key, V value) {
        removeWeakValue();
        map.put(key, new WeakValue<>(key, value, referenceQueue));
    }


    @Override
    public V get(K key) {
        removeWeakValue();
        return map.get(key).getValue();
    }

    private void removeWeakValue() {
        while (true) {
            WeakValue<V> weakValue = (WeakValue<V>) referenceQueue.poll();
            if (weakValue == null) {
                break;
            }
            System.out.println("remove unnecessary softValue: " + weakValue);
            map.remove(weakValue.getKey());
        }
    }

    private class WeakValue<T> extends WeakReference<T> {

        private K key;

        public WeakValue(K key, T value, ReferenceQueue<T> referenceQueue) {
            super(value, referenceQueue);
            this.key = key;
        }

        public K getKey() {
            return key;
        }

        public T getValue() {
            return super.get();
        }

        @Override
        public String toString() {
            return "WeakValue{" + "key=" + key + '}';
        }
    }
}
