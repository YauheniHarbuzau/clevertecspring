package ru.clevertec.clevertecspring.dao.aop.pointcut;

import org.aspectj.lang.annotation.Pointcut;
import ru.clevertec.clevertecspring.dao.repository.PersonRepository;

/**
 * Точки среза для работы АОП с репозиторием
 *
 * @see PersonRepository
 */
public class PersonAspectPointcut {

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
