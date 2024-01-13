package ru.clevertec.clevertecspring.exception;

import java.util.UUID;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message) {
        super(message);
    }

    public static EntityNotFoundException of(Class<?> entityClass, UUID uuid) {
        return new EntityNotFoundException(
                String.format("%s with uuid %s not found", entityClass.getSimpleName(), uuid)
        );
    }
}
