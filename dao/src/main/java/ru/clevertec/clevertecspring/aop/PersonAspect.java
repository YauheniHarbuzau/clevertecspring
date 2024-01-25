package ru.clevertec.clevertecspring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.clevertec.clevertecspring.aop.pointcut.PersonAspectPointcut;
import ru.clevertec.clevertecspring.cache.Cache;
import ru.clevertec.clevertecspring.cache.CacheFactory;
import ru.clevertec.clevertecspring.cache.impl.CacheFactoryImpl;
import ru.clevertec.clevertecspring.entity.Person;
import ru.clevertec.clevertecspring.repository.PersonRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Реализация АОП для работы с репозиторием
 *
 * @see PersonRepository
 * @see PersonAspectPointcut
 */
@Aspect
@Component
public class PersonAspect {

    private final CacheFactory<UUID, Person> cacheFactory;
    private final Cache<UUID, Person> cache;

    public PersonAspect(@Value("${cache.type}") String cacheType,
                        @Value("${cache.capacity}") int cacheCapacity) {
        this.cacheFactory = new CacheFactoryImpl<>();
        this.cache = cacheFactory.initCache(cacheType, cacheCapacity);
    }

    @Around("ru.clevertec.clevertecspring.aop.pointcut.PersonAspectPointcut.findPersonByUuidMethodPointcut()")
    public Optional<Person> aroundFindByUuidMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        var uuid = (UUID) joinPoint.getArgs()[0];

        Optional<Person> person;
        if (cache.get(uuid).isPresent()) {
            person = cache.get(uuid);
        } else {
            person = (Optional<Person>) joinPoint.proceed();
            person.ifPresent(p -> cache.put(p.getUuid(), p));
        }

        return person;
    }

    @Around("ru.clevertec.clevertecspring.aop.pointcut.PersonAspectPointcut.saveMethodPointcut()")
    public Person aroundCreateMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        var person = (Person) joinPoint.proceed();
        cache.put(person.getUuid(), person);
        return person;
    }
}
