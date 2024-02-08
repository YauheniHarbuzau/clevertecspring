package ru.clevertec.clevertecspring.dao.cache;

/**
 * Фабрика для инициализации алгоритма кэширования данных
 */
public interface CacheFactory<K, V> {

    Cache<K, V> initCache(String cacheType, int cacheCapacity);
}
