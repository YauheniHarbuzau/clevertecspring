package ru.clevertec.clevertecspring.dao.entity.listener;

import jakarta.persistence.PrePersist;
import ru.clevertec.clevertecspring.dao.entity.House;

import java.time.LocalDateTime;
import java.util.UUID;

public class HouseListener {

    @PrePersist
    public void prePersist(House house) {
        house.setUuid(UUID.randomUUID());
        house.setCreateDate(LocalDateTime.now());
    }
}
