package ru.clevertec.clevertecspring.exception;

import ru.clevertec.clevertecspring.constant.Constant;

import static ru.clevertec.clevertecspring.constant.Constant.CACHE_ALGORITHM_EXCEPTION_MESSAGE_FORMAT;

/**
 * Исключение, генерируемое при неизвестном алгоритме кеширования данных
 *
 * @see Constant
 */
public class CacheAlgorithmException extends RuntimeException {

    public CacheAlgorithmException(String message) {
        super(message);
    }

    public static CacheAlgorithmException of(String cacheType) {
        return new CacheAlgorithmException(
                String.format(CACHE_ALGORITHM_EXCEPTION_MESSAGE_FORMAT, cacheType)
        );
    }
}
