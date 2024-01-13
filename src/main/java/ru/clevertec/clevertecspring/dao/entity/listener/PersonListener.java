package ru.clevertec.clevertecspring.dao.entity.listener;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import ru.clevertec.clevertecspring.dao.entity.Person;

import java.time.LocalDateTime;
import java.util.UUID;

public class PersonListener {

    @PrePersist
    public void prePersist(Person person) {
        person.setUuid(UUID.randomUUID());

        LocalDateTime now = LocalDateTime.now();
        person.setCreateDate(now);
        person.setUpdateDate(now);
    }

    @PreUpdate
    public void preUpdate(Person person) {
        person.setUpdateDate(LocalDateTime.now());
    }
}
