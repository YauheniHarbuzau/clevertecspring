package ru.clevertec.clevertecspring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.clevertecspring.dto.request.HouseRequest;
import ru.clevertec.clevertecspring.dto.response.HouseResponse;
import ru.clevertec.clevertecspring.entity.House;
import ru.clevertec.clevertecspring.exception.EntityNotFoundException;
import ru.clevertec.clevertecspring.mapper.HouseMapper;
import ru.clevertec.clevertecspring.repository.HouseRepository;
import ru.clevertec.clevertecspring.service.HouseService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Имплементация сервиса для работы с {@link House}
 *
 * @see HouseRepository
 * @see HouseMapper
 * @see HouseRequest
 * @see HouseResponse
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HouseServiceImpl implements HouseService {

    private final HouseRepository houseRepository;
    private final HouseMapper houseMapper;

    /**
     * Получение House по UUID
     *
     * @param uuid идентификатор House
     * @return HouseResponse (DTO)
     */
    @Override
    public HouseResponse getByUuid(UUID uuid) {
        return houseRepository.findHouseByUuid(uuid)
                .map(houseMapper::toResponse)
                .orElseThrow(() -> EntityNotFoundException.of(House.class, uuid));
    }

    /**
     * Получение всех House
     *
     * @param pageable объект пагинации
     * @return страница с HouseResponse (DTO)
     */
    @Override
    public Page<HouseResponse> findAll(Pageable pageable) {
        return houseRepository.findAll(pageable)
                .map(houseMapper::toResponse);
    }

    /**
     * Получение всех House, которыми владел Person за все время (по Person UUID)
     *
     * @param personUuid идентификатор Person
     * @return список HouseResponse (DTO)
     */
    @Override
    public List<HouseResponse> getOwnerHousesAllTime(UUID personUuid) {
        return houseRepository.findOwnerHousesAllTime(personUuid).stream()
                .map(houseMapper::toResponse)
                .toList();
    }

    /**
     * Получение всех House, в которых проживал Person за все время (по Person UUID)
     *
     * @param personUuid идентификатор Person
     * @return список HouseResponse (DTO)
     */
    @Override
    public List<HouseResponse> getResidentHousesAllTime(UUID personUuid) {
        return houseRepository.findResidentHousesAllTime(personUuid).stream()
                .map(houseMapper::toResponse)
                .toList();
    }

    /**
     * Полнотекстовый поиск для House
     *
     * @param text искомый текст
     * @return список HouseResponse (DTO)
     */
    @Override
    public List<HouseResponse> fullTextSearch(String text) {
        return houseRepository.fullTextSearch(text).stream()
                .map(houseMapper::toResponse)
                .toList();
    }

    /**
     * Создание нового House
     *
     * @param houseRequest HouseRequest (DTO)
     * @return HouseResponse (DTO)
     */
    @Override
    @Transactional
    public HouseResponse create(HouseRequest houseRequest) {
        var houseToCreate = houseMapper.toHouse(houseRequest);

        houseToCreate.setUuid(UUID.randomUUID());
        houseToCreate.setCreateDate(LocalDateTime.now());

        var houseCreated = houseRepository.save(houseToCreate);
        return houseMapper.toResponse(houseCreated);
    }

    /**
     * Обновление существующего House
     *
     * @param uuid         идентификатор House
     * @param houseRequest HouseRequest (DTO)
     * @return HouseResponse (DTO)
     */
    @Override
    @Transactional
    public HouseResponse update(UUID uuid, HouseRequest houseRequest) {
        var houseInDb = houseRepository.findHouseByUuid(uuid)
                .orElseThrow(() -> EntityNotFoundException.of(House.class, uuid));
        var houseToUpdate = houseMapper.toHouse(houseRequest);

        houseToUpdate.setId(houseInDb.getId());
        houseToUpdate.setUuid(uuid);
        houseToUpdate.setCreateDate(houseInDb.getCreateDate());

        var houseUpdated = houseRepository.save(houseToUpdate);
        return houseMapper.toResponse(houseUpdated);
    }

    /**
     * Удаление House по UUID
     *
     * @param uuid идентификатор House
     */
    @Override
    @Transactional
    public void deleteByUuid(UUID uuid) {
        houseRepository.deleteHouseByUuid(uuid);
    }
}
