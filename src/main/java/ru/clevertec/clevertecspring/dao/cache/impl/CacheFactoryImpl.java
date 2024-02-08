package ru.clevertec.clevertecspring.dao.cache.impl;

import ru.clevertec.clevertecspring.constant.Constant;
import ru.clevertec.clevertecspring.dao.cache.Cache;
import ru.clevertec.clevertecspring.dao.cache.CacheFactory;
import ru.clevertec.clevertecspring.exception.CacheAlgorithmException;

import static ru.clevertec.clevertecspring.constant.Constant.LFU;
import static ru.clevertec.clevertecspring.constant.Constant.LRU;

/**
 * Имплементация фабрики для инициализации алгоритма кэширования данных
 *
 * @see Constant
 */
public class CacheFactoryImpl<K, V> implements CacheFactory<K, V> {

    @Override
    public Cache<K, V> initCache(String cacheType, int cacheCapacity) {
        return switch (cacheType) {
            case LRU -> new LRUCache<>(cacheCapacity);
            case LFU -> new LFUCache<>(cacheCapacity);
            default -> throw CacheAlgorithmException.of(cacheType);
        };
    }
}
