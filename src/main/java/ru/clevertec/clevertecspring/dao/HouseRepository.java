package ru.clevertec.clevertecspring.dao;

import ru.clevertec.clevertecspring.dao.entity.House;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HouseRepository {

    Optional<House> findByUuid(UUID uuid);

    List<House> findAll(int pageSize, int pageNumber);

    House create(House house);

    House update(House house);

    void deleteByUuid(UUID uuid);

    List<House> fullTextSearch(String text);
}
