package ru.clevertec.clevertecspring.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.clevertec.clevertecspring.aop.pointcut.HouseRepositoryAspectPointcut;
import ru.clevertec.clevertecspring.dao.cache.Cache;
import ru.clevertec.clevertecspring.dao.cache.CacheFactory;
import ru.clevertec.clevertecspring.dao.cache.impl.CacheFactoryImpl;
import ru.clevertec.clevertecspring.dao.entity.House;
import ru.clevertec.clevertecspring.dao.repository.HouseRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Реализация АОП для кэширования данных (на уровне репозитория)
 *
 * @see HouseRepository
 * @see HouseRepositoryAspectPointcut
 */
@Aspect
@Component
public class HouseRepositoryAspect {

    private final CacheFactory<UUID, House> cacheFactory;
    private final Cache<UUID, House> cache;

    public HouseRepositoryAspect(@Value("${cache.type}") String cacheType,
                                 @Value("${cache.capacity}") int cacheCapacity) {
        this.cacheFactory = new CacheFactoryImpl<>();
        this.cache = cacheFactory.initCache(cacheType, cacheCapacity);
    }

    @Around("ru.clevertec.clevertecspring.aop.pointcut.HouseRepositoryAspectPointcut.findHouseByUuidMethodPointcut()")
    public Optional<House> aroundFindHouseByUuidMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        var uuid = (UUID) joinPoint.getArgs()[0];

        Optional<House> house;
        if (cache.get(uuid).isPresent()) {
            house = cache.get(uuid);
        } else {
            house = (Optional<House>) joinPoint.proceed();
            house.ifPresent(p -> cache.put(p.getUuid(), p));
        }

        return house;
    }

    @Around("ru.clevertec.clevertecspring.aop.pointcut.HouseRepositoryAspectPointcut.saveMethodPointcut()")
    public House aroundSaveMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        var house = (House) joinPoint.proceed();
        cache.put(house.getUuid(), house);
        return house;
    }

    @After("ru.clevertec.clevertecspring.aop.pointcut.HouseRepositoryAspectPointcut.deleteHouseByUuidMethodPointcut()")
    public void afterDeleteHouseByUuidMethod(JoinPoint joinPoint) {
        var uuid = (UUID) joinPoint.getArgs()[0];

        if (cache.get(uuid).isPresent()) {
            cache.remove(uuid);
        }
    }
}
