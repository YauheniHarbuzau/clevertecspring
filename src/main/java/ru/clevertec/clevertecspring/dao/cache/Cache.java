package ru.clevertec.clevertecspring.dao.cache;

import java.util.Optional;

/**
 * Интерфейс для алгоритмов кэширования данных
 */
public interface Cache<K, V> {

    void put(K key, V value);

    Optional<V> get(K key);

    void remove(K key);

    int size();
}
