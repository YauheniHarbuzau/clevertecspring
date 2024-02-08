package ru.clevertec.clevertecspring.aop.pointcut;

import org.aspectj.lang.annotation.Pointcut;
import ru.clevertec.clevertecspring.service.HouseService;

/**
 * Точки среза для работы АОП с {@link HouseService}
 */
public class HouseServiceAspectPointcut {

    @Pointcut("execution(* ru.clevertec.clevertecspring.service.HouseService.*(..))")
    public static void houseServiceMethodPointcut() {
    }
}
