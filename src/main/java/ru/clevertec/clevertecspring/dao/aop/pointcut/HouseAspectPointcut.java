package ru.clevertec.clevertecspring.dao.aop.pointcut;

import org.aspectj.lang.annotation.Pointcut;
import ru.clevertec.clevertecspring.dao.repository.HouseRepository;

/**
 * Точки среза для работы АОП с репозиторием
 *
 * @see HouseRepository
 */
public class HouseAspectPointcut {

    @Pointcut("execution(* ru.clevertec.clevertecspring.dao.repository.HouseRepository.findHouseByUuid(..))")
    public static void findHouseByUuidMethodPointcut() {
    }

    @Pointcut("execution(* ru.clevertec.clevertecspring.dao.repository.HouseRepository.save(..))")
    public static void saveMethodPointcut() {
    }

    @Pointcut("execution(* ru.clevertec.clevertecspring.dao.repository.HouseRepository.deleteHouseByUuid(..))")
    public static void deleteHouseByUuidMethodPointcut() {
    }
}
