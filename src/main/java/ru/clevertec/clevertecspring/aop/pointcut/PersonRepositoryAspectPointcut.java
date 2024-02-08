package ru.clevertec.clevertecspring.aop.pointcut;

import org.aspectj.lang.annotation.Pointcut;
import ru.clevertec.clevertecspring.dao.repository.PersonRepository;

/**
 * Точки среза для работы АОП с {@link PersonRepository}
 */
public class PersonRepositoryAspectPointcut {

    @Pointcut("execution(* ru.clevertec.clevertecspring.dao.repository.PersonRepository.findPersonByUuid(..))")
    public static void findPersonByUuidMethodPointcut() {
    }

    @Pointcut("execution(* ru.clevertec.clevertecspring.dao.repository.PersonRepository.save(..))")
    public static void saveMethodPointcut() {
    }

    @Pointcut("execution(* ru.clevertec.clevertecspring.dao.repository.PersonRepository.deletePersonByUuid(..))")
    public static void deletePersonByUuidMethodPointcut() {
    }
}
