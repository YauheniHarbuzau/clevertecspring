package ru.clevertec.clevertecspring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.clevertec.clevertecspring.aop.pointcut.HouseAspectPointcut;
import ru.clevertec.clevertecspring.cache.Cache;
import ru.clevertec.clevertecspring.cache.CacheFactory;
import ru.clevertec.clevertecspring.cache.impl.CacheFactoryImpl;
import ru.clevertec.clevertecspring.entity.House;
import ru.clevertec.clevertecspring.repository.HouseRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Реализация АОП для работы с репозиторием
 *
 * @see HouseRepository
 * @see HouseAspectPointcut
 */
@Aspect
@Component
public class HouseAspect {

    private final CacheFactory<UUID, House> cacheFactory;
    private final Cache<UUID, House> cache;

    public HouseAspect(@Value("${cache.type}") String cacheType,
                       @Value("${cache.capacity}") int cacheCapacity) {
        this.cacheFactory = new CacheFactoryImpl<>();
        this.cache = cacheFactory.initCache(cacheType, cacheCapacity);
    }

    @Around("ru.clevertec.clevertecspring.aop.pointcut.HouseAspectPointcut.findHouseByUuidMethodPointcut()")
    public Optional<House> aroundFindByUuidMethod(ProceedingJoinPoint joinPoint) throws Throwable {
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

    @Around("ru.clevertec.clevertecspring.aop.pointcut.HouseAspectPointcut.saveMethodPointcut()")
    public House aroundCreateMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        var house = (House) joinPoint.proceed();
        cache.put(house.getUuid(), house);
        return house;
    }
}
