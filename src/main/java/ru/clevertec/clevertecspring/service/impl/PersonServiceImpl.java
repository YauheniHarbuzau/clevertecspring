package ru.clevertec.clevertecspring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.clevertecspring.dao.entity.House;
import ru.clevertec.clevertecspring.dao.entity.Person;
import ru.clevertec.clevertecspring.dao.repository.HouseRepository;
import ru.clevertec.clevertecspring.dao.repository.PersonRepository;
import ru.clevertec.clevertecspring.exception.EntityNotFoundException;
import ru.clevertec.clevertecspring.service.PersonService;
import ru.clevertec.clevertecspring.service.dto.request.PersonRequest;
import ru.clevertec.clevertecspring.service.dto.response.PersonResponse;
import ru.clevertec.clevertecspring.service.mapper.PersonMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Имплементация сервиса для работы с {@link Person}
 *
 * @see PersonRepository
 * @see HouseRepository
 * @see PersonMapper
 * @see PersonRequest
 * @see PersonResponse
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final HouseRepository houseRepository;
    private final PersonMapper personMapper;

    /**
     * Получение Person по UUID
     *
     * @param uuid идентификатор Person
     * @return PersonResponse (DTO)
     */
    @Override
    public PersonResponse getByUuid(UUID uuid) {
        return personRepository.findPersonByUuid(uuid)
                .map(personMapper::toResponse)
                .orElseThrow(() -> EntityNotFoundException.of(Person.class, uuid));
    }

    /**
     * Получение всех Person
     *
     * @param pageable объект пагинации
     * @return страница с PersonResponse (DTO)
     */
    @Override
    public Page<PersonResponse> getAll(Pageable pageable) {
        return personRepository.findAll(pageable)
                .map(personMapper::toResponse);
    }

    /**
     * Получение всех владельцев (Person) по House UUID
     *
     * @param houseUuid идентификатор House
     * @return список PersonResponse (DTO)
     */
    @Override
    public List<PersonResponse> getHouseOwners(UUID houseUuid) {
        return personRepository.findHouseOwners(houseUuid).stream()
                .map(personMapper::toResponse)
                .toList();
    }

    /**
     * Получение всех владельцев (Person) за все время по House UUID
     *
     * @param houseUuid идентификатор House
     * @return список PersonResponse (DTO)
     */
    @Override
    public List<PersonResponse> getHouseOwnersAllTime(UUID houseUuid) {
        return personRepository.findHouseOwnersAllTime(houseUuid).stream()
                .map(personMapper::toResponse)
                .toList();
    }

    /**
     * Получение всех жильцов (Person) по House UUID
     *
     * @param houseUuid идентификатор House
     * @return список PersonResponse (DTO)
     */
    @Override
    public List<PersonResponse> getHouseResidents(UUID houseUuid) {
        return personRepository.findHouseResidents(houseUuid).stream()
                .map(personMapper::toResponse)
                .toList();
    }

    /**
     * Получение всех жильцов (Person) за все время по House UUID
     *
     * @param houseUuid идентификатор House
     * @return список PersonResponse (DTO)
     */
    @Override
    public List<PersonResponse> getHouseTenantsAllTime(UUID houseUuid) {
        return personRepository.findHouseTenantsAllTime(houseUuid).stream()
                .map(personMapper::toResponse)
                .toList();
    }

    /**
     * Полнотекстовый поиск для Person
     *
     * @param text искомый текст
     * @return список PersonResponse (DTO)
     */
    @Override
    public List<PersonResponse> fullTextSearch(String text) {
        return personRepository.fullTextSearch(text).stream()
                .map(personMapper::toResponse)
                .toList();
    }

    /**
     * Создание нового Person
     *
     * @param personRequest PersonRequest (DTO)
     * @return PersonResponse (DTO)
     * @see #getResidency(PersonRequest)
     */
    @Override
    @Transactional
    public PersonResponse create(PersonRequest personRequest) {
        var personToCreate = personMapper.toPerson(personRequest);
        var createTime = LocalDateTime.now();

        personToCreate.setUuid(UUID.randomUUID());
        personToCreate.setResidency(getResidency(personRequest));
        personToCreate.setCreateDate(createTime);
        personToCreate.setUpdateDate(createTime);

        var personCreated = personRepository.save(personToCreate);
        return personMapper.toResponse(personCreated);
    }

    /**
     * Обновление существующего Person
     *
     * @param uuid          идентификатор Person
     * @param personRequest PersonRequest (DTO)
     * @return PersonResponse (DTO)
     * @see #getResidency(PersonRequest)
     */
    @Override
    @Transactional
    public PersonResponse update(UUID uuid, PersonRequest personRequest) {
        var personInDb = personRepository.findPersonByUuid(uuid)
                .orElseThrow(() -> EntityNotFoundException.of(Person.class, uuid));
        var personToUpdate = personMapper.toPerson(personRequest);

        personToUpdate.setId(personInDb.getId());
        personToUpdate.setUuid(uuid);
        personToUpdate.setResidency(getResidency(personRequest));
        personToUpdate.setCreateDate(personInDb.getCreateDate());
        personToUpdate.setUpdateDate(LocalDateTime.now());

        var personUpdated = personRepository.save(personToUpdate);
        return personMapper.toResponse(personUpdated);
    }

    /**
     * Удаление Person по UUID
     *
     * @param uuid идентификатор Person
     */
    @Override
    @Transactional
    public void deleteByUuid(UUID uuid) {
        personRepository.deletePersonByUuid(uuid);
    }

    /**
     * Вспомогательный метод - получение House из PersonRequest (DTO)
     *
     * @param personRequest PersonRequest (DTO)
     * @return House
     */
    private House getResidency(PersonRequest personRequest) {
        var residencyUuid = personRequest.getResidencyUuid();
        return houseRepository.findHouseByUuid(residencyUuid)
                .orElseThrow(() -> EntityNotFoundException.of(House.class, residencyUuid));
    }
}
