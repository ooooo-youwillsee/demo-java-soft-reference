package com.ooooo.demo;

/**
 * @author <a href="https://github.com/ooooo-youwillsee">ooooo</a>
 * @since 1.0.0
 */
public interface Cache<K, V> {

    void put(K key, V value);

    V get(K key);
}
