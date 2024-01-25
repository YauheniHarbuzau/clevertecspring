package ru.clevertec.clevertecspring.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.clevertec.clevertecspring.dto.request.PersonRequest;
import ru.clevertec.clevertecspring.dto.response.PersonResponse;
import ru.clevertec.clevertecspring.entity.Person;

import java.util.List;
import java.util.UUID;

/**
 * Сервис для работы с {@link Person}
 */
public interface PersonService {

    PersonResponse getByUuid(UUID uuid);

    Page<PersonResponse> findAll(Pageable pageable);

    List<PersonResponse> getHouseOwners(UUID houseUuid);

    List<PersonResponse> getHouseOwnersAllTime(UUID houseUuid);

    List<PersonResponse> getHouseResidents(UUID houseUuid);

    List<PersonResponse> getHouseResidentsAllTime(UUID houseUuid);

    List<PersonResponse> fullTextSearch(String text);

    PersonResponse create(PersonRequest personRequest);

    PersonResponse update(UUID uuid, PersonRequest personRequest);

    void deleteByUuid(UUID uuid);
}
