package ru.clevertec.clevertecspring.aop.pointcut;

import org.aspectj.lang.annotation.Pointcut;
import ru.clevertec.clevertecspring.service.PersonService;

/**
 * Точки среза для работы АОП с {@link PersonService}
 */
public class PersonServiceAspectPointcut {

    @Pointcut("execution(* ru.clevertec.clevertecspring.service.PersonService.*(..))")
    public static void personServiceMethodPointcut() {
    }
}
