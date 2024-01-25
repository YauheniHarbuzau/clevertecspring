package ru.clevertec.clevertecspring.aop.pointcut;

import org.aspectj.lang.annotation.Pointcut;
import ru.clevertec.clevertecspring.repository.PersonRepository;

/**
 * Точки среза для работы АОП с репозиторием
 *
 * @see PersonRepository
 */
public class PersonAspectPointcut {

    @Pointcut("execution(* ru.clevertec.clevertecspring.repository.PersonRepository.findPersonByUuid(..))")
    public static void findPersonByUuidMethodPointcut() {
    }

    @Pointcut("execution(* ru.clevertec.clevertecspring.repository.PersonRepository.save(..))")
    public static void saveMethodPointcut() {
    }
}
