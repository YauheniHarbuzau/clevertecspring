package ru.clevertec.clevertecspring.aop.pointcut;

import org.aspectj.lang.annotation.Pointcut;
import ru.clevertec.clevertecspring.repository.HouseRepository;

/**
 * Точки среза для работы АОП с репозиторием
 *
 * @see HouseRepository
 */
public class HouseAspectPointcut {

    @Pointcut("execution(* ru.clevertec.clevertecspring.repository.HouseRepository.findHouseByUuid(..))")
    public static void findHouseByUuidMethodPointcut() {
    }

    @Pointcut("execution(* ru.clevertec.clevertecspring.repository.HouseRepository.save(..))")
    public static void saveMethodPointcut() {
    }
}
