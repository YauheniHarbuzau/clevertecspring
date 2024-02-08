package ru.clevertec.clevertecspring.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.clevertec.clevertecspring.dao.entity.House;
import ru.clevertec.clevertecspring.service.dto.request.HouseRequest;
import ru.clevertec.clevertecspring.service.dto.response.HouseResponse;

import java.util.List;
import java.util.UUID;

/**
 * Сервис для работы с {@link House}
 */
public interface HouseService {

    HouseResponse getByUuid(UUID uuid);

    Page<HouseResponse> getAll(Pageable pageable);

    List<HouseResponse> getOwnerHousesAllTime(UUID personUuid);

    List<HouseResponse> getTenantHousesAllTime(UUID personUuid);

    List<HouseResponse> fullTextSearch(String text);

    HouseResponse create(HouseRequest houseRequest);

    HouseResponse update(UUID uuid, HouseRequest houseRequest);

    void deleteByUuid(UUID uuid);
}
