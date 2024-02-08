package ru.clevertec.clevertecspring.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import ru.clevertec.clevertecspring.aop.pointcut.PersonServiceAspectPointcut;
import ru.clevertec.clevertecspring.constant.Constant;
import ru.clevertec.clevertecspring.exception.UnknownMethodException;
import ru.clevertec.clevertecspring.service.PersonService;
import ru.clevertec.clevertecspring.service.dto.request.PersonRequest;
import ru.clevertec.clevertecspring.service.dto.response.PersonResponse;
import ru.clevertec.clevertecspring.util.LoggerUtil;

import java.util.UUID;

import static ru.clevertec.clevertecspring.constant.Constant.CREATE;
import static ru.clevertec.clevertecspring.constant.Constant.DELETE_BY_UUID;
import static ru.clevertec.clevertecspring.constant.Constant.FULL_TEXT_SEARCH;
import static ru.clevertec.clevertecspring.constant.Constant.GET_ALL;
import static ru.clevertec.clevertecspring.constant.Constant.GET_BY_UUID;
import static ru.clevertec.clevertecspring.constant.Constant.GET_HOUSE_OWNERS;
import static ru.clevertec.clevertecspring.constant.Constant.GET_HOUSE_OWNERS_ALL_TIME;
import static ru.clevertec.clevertecspring.constant.Constant.GET_HOUSE_RESIDENTS;
import static ru.clevertec.clevertecspring.constant.Constant.GET_HOUSE_TENANTS_ALL_TIME;
import static ru.clevertec.clevertecspring.constant.Constant.LOG_AFTER_RETURNING_PERSON_CREATE;
import static ru.clevertec.clevertecspring.constant.Constant.LOG_AFTER_RETURNING_PERSON_UPDATE;
import static ru.clevertec.clevertecspring.constant.Constant.LOG_AFTER_THROWING;
import static ru.clevertec.clevertecspring.constant.Constant.LOG_BEFORE_PERSON_CREATE;
import static ru.clevertec.clevertecspring.constant.Constant.LOG_BEFORE_PERSON_DELETE_BY_UUID;
import static ru.clevertec.clevertecspring.constant.Constant.LOG_BEFORE_PERSON_FULL_TEXT_SEARCH;
import static ru.clevertec.clevertecspring.constant.Constant.LOG_BEFORE_PERSON_GET_ALL;
import static ru.clevertec.clevertecspring.constant.Constant.LOG_BEFORE_PERSON_GET_BY_UUID;
import static ru.clevertec.clevertecspring.constant.Constant.LOG_BEFORE_PERSON_GET_HOUSE_OWNERS;
import static ru.clevertec.clevertecspring.constant.Constant.LOG_BEFORE_PERSON_GET_HOUSE_OWNERS_ALL_TIME;
import static ru.clevertec.clevertecspring.constant.Constant.LOG_BEFORE_PERSON_GET_HOUSE_RESIDENTS;
import static ru.clevertec.clevertecspring.constant.Constant.LOG_BEFORE_PERSON_GET_HOUSE_TENANTS_ALL_TIME;
import static ru.clevertec.clevertecspring.constant.Constant.LOG_BEFORE_PERSON_UPDATE;
import static ru.clevertec.clevertecspring.constant.Constant.UPDATE;

/**
 * Реализация АОП для логирования (на уровне сервиса)
 *
 * @see PersonService
 * @see PersonServiceAspectPointcut
 * @see LoggerUtil
 * @see Constant
 */
@Aspect
@Component
public class PersonServiceAspect {

    private static final String PERSON_SERVICE_POINTCUT = "ru.clevertec.clevertecspring.aop.pointcut.PersonServiceAspectPointcut.personServiceMethodPointcut()";

    @Before(PERSON_SERVICE_POINTCUT)
    public void beforePersonServiceMethod(JoinPoint joinPoint) {
        var methodName = getMethodName(joinPoint);
        switch (methodName) {
            case GET_BY_UUID -> beforeGetByUuidMethod(joinPoint, methodName);
            case GET_ALL -> beforeGetAllMethod(joinPoint, methodName);
            case GET_HOUSE_OWNERS -> beforeGetHouseOwnersMethod(joinPoint, methodName);
            case GET_HOUSE_OWNERS_ALL_TIME -> beforeGetHouseOwnersAllTimeMethod(joinPoint, methodName);
            case GET_HOUSE_RESIDENTS -> beforeGetHouseResidentsMethod(joinPoint, methodName);
            case GET_HOUSE_TENANTS_ALL_TIME -> beforeGetHouseTenantsAllTimeMethod(joinPoint, methodName);
            case FULL_TEXT_SEARCH -> beforeFullTextSearchMethod(joinPoint, methodName);
            case CREATE -> beforeCreateMethod(joinPoint, methodName);
            case UPDATE -> beforeUpdateMethod(joinPoint, methodName);
            case DELETE_BY_UUID -> beforeDeleteByUuidMethod(joinPoint, methodName);
            default -> throw UnknownMethodException.of(methodName);
        }
    }

    @AfterReturning(pointcut = PERSON_SERVICE_POINTCUT, returning = "personResponse")
    public void afterReturningPersonServiceMethod(JoinPoint joinPoint, PersonResponse personResponse) {
        var methodName = getMethodName(joinPoint);
        switch (methodName) {
            case CREATE -> afterReturningCreateMethod(joinPoint, methodName, personResponse);
            case UPDATE -> afterReturningUpdateMethod(joinPoint, methodName, personResponse);
        }
    }

    @AfterThrowing(pointcut = PERSON_SERVICE_POINTCUT, throwing = "error")
    public void afterThrowingPersonServiceMethod(JoinPoint joinPoint, Throwable error) {
        var methodName = getMethodName(joinPoint);
        switch (methodName) {
            case GET_BY_UUID -> afterThrowingGetByUuidMethod(joinPoint, methodName, error);
            case CREATE -> afterThrowingCreateMethod(joinPoint, methodName, error);
            case UPDATE -> afterThrowingUpdateMethod(joinPoint, methodName, error);
        }
    }

    private void beforeGetByUuidMethod(JoinPoint joinPoint, String methodName) {
        var uuid = (UUID) joinPoint.getArgs()[0];
        LoggerUtil.addLogger(joinPoint)
                .info(LOG_BEFORE_PERSON_GET_BY_UUID, methodName, uuid);
    }

    private void beforeGetAllMethod(JoinPoint joinPoint, String methodName) {
        var pageable = (Pageable) joinPoint.getArgs()[0];
        LoggerUtil.addLogger(joinPoint)
                .info(LOG_BEFORE_PERSON_GET_ALL, methodName, pageable.getPageNumber(), pageable.getPageSize());
    }

    private void beforeGetHouseOwnersMethod(JoinPoint joinPoint, String methodName) {
        var houseUuid = (UUID) joinPoint.getArgs()[0];
        LoggerUtil.addLogger(joinPoint)
                .info(LOG_BEFORE_PERSON_GET_HOUSE_OWNERS, methodName, houseUuid);
    }

    private void beforeGetHouseOwnersAllTimeMethod(JoinPoint joinPoint, String methodName) {
        var houseUuid = (UUID) joinPoint.getArgs()[0];
        LoggerUtil.addLogger(joinPoint)
                .info(LOG_BEFORE_PERSON_GET_HOUSE_OWNERS_ALL_TIME, methodName, houseUuid);
    }

    private void beforeGetHouseResidentsMethod(JoinPoint joinPoint, String methodName) {
        var houseUuid = (UUID) joinPoint.getArgs()[0];
        LoggerUtil.addLogger(joinPoint)
                .info(LOG_BEFORE_PERSON_GET_HOUSE_RESIDENTS, methodName, houseUuid);
    }

    private void beforeGetHouseTenantsAllTimeMethod(JoinPoint joinPoint, String methodName) {
        var houseUuid = (UUID) joinPoint.getArgs()[0];
        LoggerUtil.addLogger(joinPoint)
                .info(LOG_BEFORE_PERSON_GET_HOUSE_TENANTS_ALL_TIME, methodName, houseUuid);
    }

    private void beforeFullTextSearchMethod(JoinPoint joinPoint, String methodName) {
        var text = (String) joinPoint.getArgs()[0];
        LoggerUtil.addLogger(joinPoint)
                .info(LOG_BEFORE_PERSON_FULL_TEXT_SEARCH, methodName, text);
    }

    private void beforeCreateMethod(JoinPoint joinPoint, String methodName) {
        var personRequest = getPersonRequest(joinPoint, methodName);
        LoggerUtil.addLogger(joinPoint)
                .info(LOG_BEFORE_PERSON_CREATE, methodName, personRequest);
    }

    private void beforeUpdateMethod(JoinPoint joinPoint, String methodName) {
        var uuid = (UUID) joinPoint.getArgs()[0];
        var personRequest = getPersonRequest(joinPoint, methodName);
        LoggerUtil.addLogger(joinPoint)
                .info(LOG_BEFORE_PERSON_UPDATE, methodName, uuid, personRequest);
    }

    private void beforeDeleteByUuidMethod(JoinPoint joinPoint, String methodName) {
        var uuid = (UUID) joinPoint.getArgs()[0];
        LoggerUtil.addLogger(joinPoint)
                .info(LOG_BEFORE_PERSON_DELETE_BY_UUID, methodName, uuid);
    }

    private void afterReturningCreateMethod(JoinPoint joinPoint, String methodName, PersonResponse personResponse) {
        LoggerUtil.addLogger(joinPoint)
                .info(LOG_AFTER_RETURNING_PERSON_CREATE, methodName, personResponse);
    }

    private void afterReturningUpdateMethod(JoinPoint joinPoint, String methodName, PersonResponse personResponse) {
        LoggerUtil.addLogger(joinPoint)
                .info(LOG_AFTER_RETURNING_PERSON_UPDATE, methodName, personResponse);
    }

    private void afterThrowingGetByUuidMethod(JoinPoint joinPoint, String methodName, Throwable error) {
        LoggerUtil.addLogger(joinPoint)
                .error(LOG_AFTER_THROWING, methodName, error.getMessage());
    }

    private void afterThrowingCreateMethod(JoinPoint joinPoint, String methodName, Throwable error) {
        LoggerUtil.addLogger(joinPoint)
                .error(LOG_AFTER_THROWING, methodName, error.getMessage());
    }

    private void afterThrowingUpdateMethod(JoinPoint joinPoint, String methodName, Throwable error) {
        LoggerUtil.addLogger(joinPoint)
                .error(LOG_AFTER_THROWING, methodName, error.getMessage());
    }

    private String getMethodName(JoinPoint joinPoint) {
        return joinPoint.getSignature().getName();
    }

    private PersonRequest getPersonRequest(JoinPoint joinPoint, String methodName) {
        PersonRequest personRequest = null;

        if (methodName.equals(getMethodName(joinPoint))) {
            Object[] arguments = joinPoint.getArgs();

            for (Object args : arguments) {
                if (args instanceof PersonRequest) {
                    personRequest = (PersonRequest) args;
                }
            }
        }
        return personRequest;
    }
}
