package ru.clevertec.clevertecspring.dao.cache;

/**
 * Фабрика для инициализации алгоритма кеширования данных
 */
public interface CacheFactory<K, V> {

    Cache<K, V> initCache(String cacheType, int cacheCapacity);
}
