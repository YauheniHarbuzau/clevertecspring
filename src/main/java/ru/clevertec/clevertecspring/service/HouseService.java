package ru.clevertec.clevertecspring.service;

import ru.clevertec.clevertecspring.service.dto.request.HouseRequest;
import ru.clevertec.clevertecspring.service.dto.response.HouseResponse;

import java.util.List;
import java.util.UUID;

public interface HouseService {

    HouseResponse getByUuid(UUID uuid);

    List<HouseResponse> getAll();

    HouseResponse create(HouseRequest houseRequest);

    HouseResponse update(UUID uuid, HouseRequest houseRequest);

    void deleteByUuid(UUID uuid);

    List<HouseResponse> fullTextSearch(String text);
}
