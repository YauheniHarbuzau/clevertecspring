package ru.clevertec.clevertecspring.cache.impl;

import ru.clevertec.clevertecspring.cache.Cache;
import ru.clevertec.clevertecspring.cache.CacheFactory;
import ru.clevertec.clevertecspring.exception.CacheAlgorithmException;

/**
 * Имплементация фабрики для инициализации алгоритма кеширования данных
 */
public class CacheFactoryImpl<K, V> implements CacheFactory<K, V> {

    @Override
    public Cache<K, V> initCache(String cacheType, int cacheCapacity) {
        return switch (cacheType) {
            case "LRU" -> new LRUCache<>(cacheCapacity);
            case "LFU" -> new LFUCache<>(cacheCapacity);
            default -> throw CacheAlgorithmException.of(cacheType);
        };
    }
}
