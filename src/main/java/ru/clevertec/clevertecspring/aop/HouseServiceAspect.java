package ru.clevertec.clevertecspring.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import ru.clevertec.clevertecspring.aop.pointcut.HouseServiceAspectPointcut;
import ru.clevertec.clevertecspring.constant.Constant;
import ru.clevertec.clevertecspring.exception.UnknownMethodException;
import ru.clevertec.clevertecspring.service.HouseService;
import ru.clevertec.clevertecspring.service.dto.request.HouseRequest;
import ru.clevertec.clevertecspring.service.dto.response.HouseResponse;
import ru.clevertec.clevertecspring.util.LoggerUtil;

import java.util.UUID;

import static ru.clevertec.clevertecspring.constant.Constant.CREATE;
import static ru.clevertec.clevertecspring.constant.Constant.DELETE_BY_UUID;
import static ru.clevertec.clevertecspring.constant.Constant.FULL_TEXT_SEARCH;
import static ru.clevertec.clevertecspring.constant.Constant.GET_ALL;
import static ru.clevertec.clevertecspring.constant.Constant.GET_BY_UUID;
import static ru.clevertec.clevertecspring.constant.Constant.GET_OWNER_HOUSES_ALL_TIME;
import static ru.clevertec.clevertecspring.constant.Constant.GET_TENANT_HOUSES_ALL_TIME;
import static ru.clevertec.clevertecspring.constant.Constant.LOG_AFTER_RETURNING_HOUSE_CREATE;
import static ru.clevertec.clevertecspring.constant.Constant.LOG_AFTER_RETURNING_HOUSE_UPDATE;
import static ru.clevertec.clevertecspring.constant.Constant.LOG_AFTER_THROWING;
import static ru.clevertec.clevertecspring.constant.Constant.LOG_BEFORE_HOUSE_CREATE;
import static ru.clevertec.clevertecspring.constant.Constant.LOG_BEFORE_HOUSE_DELETE_BY_UUID;
import static ru.clevertec.clevertecspring.constant.Constant.LOG_BEFORE_HOUSE_FULL_TEXT_SEARCH;
import static ru.clevertec.clevertecspring.constant.Constant.LOG_BEFORE_HOUSE_GET_ALL;
import static ru.clevertec.clevertecspring.constant.Constant.LOG_BEFORE_HOUSE_GET_BY_UUID;
import static ru.clevertec.clevertecspring.constant.Constant.LOG_BEFORE_HOUSE_GET_OWNER_HOUSES_ALL_TIME;
import static ru.clevertec.clevertecspring.constant.Constant.LOG_BEFORE_HOUSE_GET_TENANT_HOUSES_ALL_TIME;
import static ru.clevertec.clevertecspring.constant.Constant.LOG_BEFORE_HOUSE_UPDATE;
import static ru.clevertec.clevertecspring.constant.Constant.UPDATE;

/**
 * Реализация АОП для логирования (на уровне сервиса)
 *
 * @see HouseService
 * @see HouseServiceAspectPointcut
 * @see LoggerUtil
 * @see Constant
 */
@Aspect
@Component
public class HouseServiceAspect {

    private static final String HOUSE_SERVICE_POINTCUT = "ru.clevertec.clevertecspring.aop.pointcut.HouseServiceAspectPointcut.houseServiceMethodPointcut()";

    @Before(HOUSE_SERVICE_POINTCUT)
    public void beforeHouseServiceMethod(JoinPoint joinPoint) {
        var methodName = getMethodName(joinPoint);
        switch (methodName) {
            case GET_BY_UUID -> beforeGetByUuidMethod(joinPoint, methodName);
            case GET_ALL -> beforeGetAllMethod(joinPoint, methodName);
            case GET_OWNER_HOUSES_ALL_TIME -> beforeGetOwnerHousesAllTimeMethod(joinPoint, methodName);
            case GET_TENANT_HOUSES_ALL_TIME -> beforeGetTenantHousesAllTimeMethod(joinPoint, methodName);
            case FULL_TEXT_SEARCH -> beforeFullTextSearchMethod(joinPoint, methodName);
            case CREATE -> beforeCreateMethod(joinPoint, methodName);
            case UPDATE -> beforeUpdateMethod(joinPoint, methodName);
            case DELETE_BY_UUID -> beforeDeleteByUuidMethod(joinPoint, methodName);
            default -> throw UnknownMethodException.of(methodName);
        }
    }

    @AfterReturning(pointcut = HOUSE_SERVICE_POINTCUT, returning = "houseResponse")
    public void afterReturningHouseServiceMethod(JoinPoint joinPoint, HouseResponse houseResponse) {
        var methodName = getMethodName(joinPoint);
        switch (methodName) {
            case CREATE -> afterReturningCreateMethod(joinPoint, methodName, houseResponse);
            case UPDATE -> afterReturningUpdateMethod(joinPoint, methodName, houseResponse);
        }
    }

    @AfterThrowing(pointcut = HOUSE_SERVICE_POINTCUT, throwing = "error")
    public void afterThrowingHouseServiceMethod(JoinPoint joinPoint, Throwable error) {
        var methodName = getMethodName(joinPoint);
        switch (methodName) {
            case GET_BY_UUID -> afterThrowingGetByUuidMethod(joinPoint, methodName, error);
            case UPDATE -> afterThrowingUpdateMethod(joinPoint, methodName, error);
        }
    }

    private void beforeGetByUuidMethod(JoinPoint joinPoint, String methodName) {
        var uuid = (UUID) joinPoint.getArgs()[0];
        LoggerUtil.addLogger(joinPoint)
                .info(LOG_BEFORE_HOUSE_GET_BY_UUID, methodName, uuid);
    }

    private void beforeGetAllMethod(JoinPoint joinPoint, String methodName) {
        var pageable = (Pageable) joinPoint.getArgs()[0];
        LoggerUtil.addLogger(joinPoint)
                .info(LOG_BEFORE_HOUSE_GET_ALL, methodName, pageable.getPageNumber(), pageable.getPageSize());
    }

    private void beforeGetOwnerHousesAllTimeMethod(JoinPoint joinPoint, String methodName) {
        var personUuid = (UUID) joinPoint.getArgs()[0];
        LoggerUtil.addLogger(joinPoint)
                .info(LOG_BEFORE_HOUSE_GET_OWNER_HOUSES_ALL_TIME, methodName, personUuid);
    }

    private void beforeGetTenantHousesAllTimeMethod(JoinPoint joinPoint, String methodName) {
        var personUuid = (UUID) joinPoint.getArgs()[0];
        LoggerUtil.addLogger(joinPoint)
                .info(LOG_BEFORE_HOUSE_GET_TENANT_HOUSES_ALL_TIME, methodName, personUuid);
    }

    private void beforeFullTextSearchMethod(JoinPoint joinPoint, String methodName) {
        var text = (String) joinPoint.getArgs()[0];
        LoggerUtil.addLogger(joinPoint)
                .info(LOG_BEFORE_HOUSE_FULL_TEXT_SEARCH, methodName, text);
    }

    private void beforeCreateMethod(JoinPoint joinPoint, String methodName) {
        var houseRequest = getHouseRequest(joinPoint, methodName);
        LoggerUtil.addLogger(joinPoint)
                .info(LOG_BEFORE_HOUSE_CREATE, methodName, houseRequest);
    }

    private void beforeUpdateMethod(JoinPoint joinPoint, String methodName) {
        var uuid = (UUID) joinPoint.getArgs()[0];
        var houseRequest = getHouseRequest(joinPoint, methodName);
        LoggerUtil.addLogger(joinPoint)
                .info(LOG_BEFORE_HOUSE_UPDATE, methodName, uuid, houseRequest);
    }

    private void beforeDeleteByUuidMethod(JoinPoint joinPoint, String methodName) {
        var uuid = (UUID) joinPoint.getArgs()[0];
        LoggerUtil.addLogger(joinPoint)
                .info(LOG_BEFORE_HOUSE_DELETE_BY_UUID, methodName, uuid);
    }

    private void afterReturningCreateMethod(JoinPoint joinPoint, String methodName, HouseResponse houseResponse) {
        LoggerUtil.addLogger(joinPoint)
                .info(LOG_AFTER_RETURNING_HOUSE_CREATE, methodName, houseResponse);
    }

    private void afterReturningUpdateMethod(JoinPoint joinPoint, String methodName, HouseResponse houseResponse) {
        LoggerUtil.addLogger(joinPoint)
                .info(LOG_AFTER_RETURNING_HOUSE_UPDATE, methodName, houseResponse);
    }

    private void afterThrowingGetByUuidMethod(JoinPoint joinPoint, String methodName, Throwable error) {
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

    private HouseRequest getHouseRequest(JoinPoint joinPoint, String methodName) {
        HouseRequest houseRequest = null;

        if (methodName.equals(getMethodName(joinPoint))) {
            Object[] arguments = joinPoint.getArgs();

            for (Object args : arguments) {
                if (args instanceof HouseRequest) {
                    houseRequest = (HouseRequest) args;
                }
            }
        }
        return houseRequest;
    }
}
