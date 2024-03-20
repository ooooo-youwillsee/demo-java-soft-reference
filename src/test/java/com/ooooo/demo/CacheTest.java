package com.ooooo.demo;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author <a href="https://github.com/ooooo-youwillsee">ooooo</a>
 * @since 1.0.0
 */
public class CacheTest {

    @Test
    void testSoftReferenceCache() {
        Cache<String, String> cache = new SoftReferenceCache<>();
        for (int i = 0; i < 1_000_000; i++) {
            System.gc();
            cache.put("key" + i, "value" + i);
        }
        for (int i = 0; i < 10; i++) {
            cache.get("key" + i);
        }
    }

    @Test
    void testWeakReferenceCache() {
        Cache<String, String> cache = new WeakReferenceCache<>();
        for (int i = 0; i < 1_000_000; i++) {
            System.gc();
            cache.put("key" + i, "value" + i);
        }
        for (int i = 0; i < 10; i++) {
            cache.get("key" + i);
        }
    }
}
