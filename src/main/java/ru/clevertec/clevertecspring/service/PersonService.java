package ru.clevertec.clevertecspring.service;

import ru.clevertec.clevertecspring.service.dto.request.PersonRequest;
import ru.clevertec.clevertecspring.service.dto.response.PersonResponse;

import java.util.List;
import java.util.UUID;

public interface PersonService {

    PersonResponse getByUuid(UUID uuid);

    List<PersonResponse> getAll();

    PersonResponse create(PersonRequest personRequest);

    PersonResponse update(UUID uuid, PersonRequest personRequest);

    void deleteByUuid(UUID uuid);

    List<PersonResponse> getHouseResidents(UUID houseUuid);

    List<PersonResponse> fullTextSearch(String text);
}
