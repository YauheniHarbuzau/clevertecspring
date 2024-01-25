package ru.clevertec.clevertecspring.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.clevertec.clevertecspring.dto.request.HouseRequest;
import ru.clevertec.clevertecspring.dto.response.HouseResponse;
import ru.clevertec.clevertecspring.entity.House;

import java.util.List;
import java.util.UUID;

/**
 * Сервис для работы с {@link House}
 */
public interface HouseService {

    HouseResponse getByUuid(UUID uuid);

    Page<HouseResponse> findAll(Pageable pageable);

    List<HouseResponse> getOwnerHousesAllTime(UUID personUuid);

    List<HouseResponse> getResidentHousesAllTime(UUID personUuid);

    List<HouseResponse> fullTextSearch(String text);

    HouseResponse create(HouseRequest houseRequest);

    HouseResponse update(UUID uuid, HouseRequest houseRequest);

    void deleteByUuid(UUID uuid);
}
