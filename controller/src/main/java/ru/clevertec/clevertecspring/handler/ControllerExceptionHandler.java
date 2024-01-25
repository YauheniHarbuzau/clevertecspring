package ru.clevertec.clevertecspring.handler;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import ru.clevertec.clevertecspring.exception.EntityNotFoundException;

import java.time.LocalDateTime;

/**
 * Класс-обработчик исключений
 */
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleEntityNotFoundException(EntityNotFoundException ex) {
        var httpStatus = HttpStatus.NOT_FOUND;
        return ExceptionResponse.of(LocalDateTime.now(), httpStatus.value(), httpStatus.name(), ex.getMessage(), null);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        var httpStatus = HttpStatus.BAD_REQUEST;
        return ExceptionResponse.of(LocalDateTime.now(), httpStatus.value(), httpStatus.name(), ex.getMessage(), null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        var httpStatus = HttpStatus.BAD_REQUEST;

        var errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        return ExceptionResponse.of(LocalDateTime.now(), httpStatus.value(), httpStatus.name(), ex.getMessage(), errors);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleNoHandlerFoundException(NoHandlerFoundException ex) {
        var httpStatus = HttpStatus.NOT_FOUND;
        return ExceptionResponse.of(LocalDateTime.now(), httpStatus.value(), httpStatus.name(), ex.getMessage(), null);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleRuntimeException(RuntimeException ex) {
        var httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        return ExceptionResponse.of(LocalDateTime.now(), httpStatus.value(), httpStatus.name(), ex.getMessage(), null);
    }
}
