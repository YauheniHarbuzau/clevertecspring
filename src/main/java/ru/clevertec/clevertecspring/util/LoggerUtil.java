package ru.clevertec.clevertecspring.util;

import lombok.experimental.UtilityClass;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import ru.clevertec.clevertecspring.aop.HouseServiceAspect;
import ru.clevertec.clevertecspring.aop.PersonServiceAspect;

import static org.apache.logging.log4j.LogManager.getLogger;

/**
 * Утилитарный класс, предоставляющий логгер для АОП
 *
 * @see PersonServiceAspect
 * @see HouseServiceAspect
 */
@UtilityClass
public class LoggerUtil {

    public Logger addLogger(JoinPoint joinPoint) {
        return getLogger(joinPoint.getTarget().getClass());
    }
}
