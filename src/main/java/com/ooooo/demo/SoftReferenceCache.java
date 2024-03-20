package com.ooooo.demo;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="https://github.com/ooooo-youwillsee">ooooo</a>
 * @since 1.0.0
 */
public class SoftReferenceCache<K, V> implements Cache<K, V> {

    private Map<K, SoftValue<V>> map;

    private ReferenceQueue<V> referenceQueue;

    public SoftReferenceCache() {
        this.map = new HashMap<>();
        this.referenceQueue = new ReferenceQueue<>();
    }

    @Override
    public void put(K key, V value) {
        removeSoftValue();
        this.map.put(key, new SoftValue<>(key, value, referenceQueue));
    }

    @Override
    public V get(K key) {
        removeSoftValue();
        SoftValue<V> softValue = this.map.get(key);
        return softValue.getValue();
    }

    protected void removeSoftValue() {
        while (true) {
            SoftValue<V> softValue = (SoftValue<V>) referenceQueue.poll();
            if (softValue == null) {
                break;
            }
            System.out.println("remove unnecessary softValue: " + softValue);
            map.remove(softValue.getKey());
        }
    }

    private class SoftValue<T> extends SoftReference<T> {

        private K key;

        public SoftValue(K key, T value, ReferenceQueue<T> referenceQueue) {
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
            return "SoftValue{" +
                "key=" + key +
                '}';
        }
    }

}
