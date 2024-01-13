package ru.clevertec.clevertecspring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.clevertec.clevertecspring.dao.HouseRepository;
import ru.clevertec.clevertecspring.dao.PersonRepository;
import ru.clevertec.clevertecspring.dao.entity.House;
import ru.clevertec.clevertecspring.dao.entity.Person;
import ru.clevertec.clevertecspring.exception.EntityNotFoundException;
import ru.clevertec.clevertecspring.service.PersonService;
import ru.clevertec.clevertecspring.service.dto.request.PersonRequest;
import ru.clevertec.clevertecspring.service.dto.response.PersonResponse;
import ru.clevertec.clevertecspring.service.mapper.PersonMapper;

import java.util.List;
import java.util.UUID;

/**
 * Сервис для работы с {@link Person}, {@link PersonRequest}, {@link PersonResponse}
 */
@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final HouseRepository houseRepository;
    private final PersonMapper mapper;

    @Value("${pagination.page-size}")
    private int pageSize;

    @Value("${pagination.page-number}")
    private int pageNumber;

    @Override
    public PersonResponse getByUuid(UUID uuid) {
        return personRepository.findByUuid(uuid)
                .map(mapper::toResponse)
                .orElseThrow(() -> EntityNotFoundException.of(Person.class, uuid));
    }

    @Override
    public List<PersonResponse> getAll() {
        return personRepository.findAll(pageSize, pageNumber).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public PersonResponse create(PersonRequest personRequest) {
        var personToCreate = mapper.toPerson(personRequest);

        var residency = getResidency(personRequest);
        personToCreate.setResidency(residency);

        var personCreated = personRepository.create(personToCreate);
        return mapper.toResponse(personCreated);
    }

    @Override
    public PersonResponse update(UUID uuid, PersonRequest personRequest) {
        var personInDb = personRepository.findByUuid(uuid)
                .orElseThrow(() -> EntityNotFoundException.of(Person.class, uuid));
        var personToUpdate = mapper.toPerson(personRequest);
        var residency = getResidency(personRequest);

        personToUpdate.setId(personInDb.getId());
        personToUpdate.setUuid(uuid);
        personToUpdate.setResidency(residency);
        personToUpdate.setCreateDate(personInDb.getCreateDate());

        var personUpdated = personRepository.update(personToUpdate);
        return mapper.toResponse(personUpdated);
    }

    @Override
    public void deleteByUuid(UUID uuid) {
        personRepository.deleteByUuid(uuid);
    }

    private House getResidency(PersonRequest personRequest) {
        var residencyUuid = personRequest.getResidencyUuid();
        return houseRepository.findByUuid(residencyUuid)
                .orElseThrow(() -> EntityNotFoundException.of(House.class, residencyUuid));
    }

    @Override
    public List<PersonResponse> getHouseResidents(UUID houseUuid) {
        return personRepository.findHouseResidents(houseUuid).stream()
                .map(mapper::toResponse)
                .peek(p -> p.setResidencyUuid(houseUuid))
                .toList();
    }

    @Override
    public List<PersonResponse> fullTextSearch(String text) {
        return personRepository.fullTextSearch(text).stream()
                .map(mapper::toResponse)
                .toList();
    }
}
