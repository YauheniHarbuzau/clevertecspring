package ru.clevertec.clevertecspring.exception;

import ru.clevertec.clevertecspring.constant.Constant;

import static ru.clevertec.clevertecspring.constant.Constant.UNKNOWN_METHOD_EXCEPTION_MESSAGE_FORMAT;

/**
 * Исключение, генерируемое при обращению к неизвестному методу
 *
 * @see Constant
 */
public class UnknownMethodException extends RuntimeException {

    public UnknownMethodException(String message) {
        super(message);
    }

    public static UnknownMethodException of(String methodName) {
        return new UnknownMethodException(
                String.format(UNKNOWN_METHOD_EXCEPTION_MESSAGE_FORMAT, methodName)
        );
    }
}
