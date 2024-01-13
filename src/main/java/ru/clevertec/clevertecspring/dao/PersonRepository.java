package ru.clevertec.clevertecspring.dao;

import ru.clevertec.clevertecspring.dao.entity.Person;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonRepository {

    Optional<Person> findByUuid(UUID uuid);

    List<Person> findAll(int pageSize, int pageNumber);

    Person create(Person person);

    Person update(Person person);

    void deleteByUuid(UUID uuid);

    List<Person> findHouseResidents(UUID houseUuid);

    List<Person> fullTextSearch(String text);
}
