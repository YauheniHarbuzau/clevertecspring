package ru.clevertec.clevertecspring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.clevertec.clevertecspring.dao.HouseRepository;
import ru.clevertec.clevertecspring.dao.entity.House;
import ru.clevertec.clevertecspring.exception.EntityNotFoundException;
import ru.clevertec.clevertecspring.service.HouseService;
import ru.clevertec.clevertecspring.service.dto.request.HouseRequest;
import ru.clevertec.clevertecspring.service.dto.response.HouseResponse;
import ru.clevertec.clevertecspring.service.mapper.HouseMapper;

import java.util.List;
import java.util.UUID;

/**
 * Сервис для работы с {@link House}, {@link HouseRequest}, {@link HouseResponse}
 */
@Service
@RequiredArgsConstructor
public class HouseServiceImpl implements HouseService {

    private final HouseRepository repository;
    private final HouseMapper mapper;

    @Value("${pagination.page-size}")
    private int pageSize;

    @Value("${pagination.page-number}")
    private int pageNumber;

    @Override
    public HouseResponse getByUuid(UUID uuid) {
        return repository.findByUuid(uuid)
                .map(mapper::toResponse)
                .orElseThrow(() -> EntityNotFoundException.of(House.class, uuid));
    }

    @Override
    public List<HouseResponse> getAll() {
        return repository.findAll(pageSize, pageNumber).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public HouseResponse create(HouseRequest houseRequest) {
        var houseToCreate = mapper.toHouse(houseRequest);
        var houseCreated = repository.create(houseToCreate);
        return mapper.toResponse(houseCreated);
    }

    @Override
    public HouseResponse update(UUID uuid, HouseRequest houseRequest) {
        var houseInDb = repository.findByUuid(uuid)
                .orElseThrow(() -> EntityNotFoundException.of(House.class, uuid));
        var houseToUpdate = mapper.toHouse(houseRequest);

        houseToUpdate.setId(houseInDb.getId());
        houseToUpdate.setUuid(uuid);
        houseToUpdate.setCreateDate(houseInDb.getCreateDate());

        var houseUpdated = repository.update(houseToUpdate);
        return mapper.toResponse(houseUpdated);
    }

    @Override
    public void deleteByUuid(UUID uuid) {
        repository.deleteByUuid(uuid);
    }

    @Override
    public List<HouseResponse> fullTextSearch(String text) {
        return repository.fullTextSearch(text).stream()
                .map(mapper::toResponse)
                .toList();
    }
}
