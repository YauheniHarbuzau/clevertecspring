package ru.clevertec.clevertecspring.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.clevertec.clevertecspring.dao.entity.Passport;
import ru.clevertec.clevertecspring.dao.entity.Person;
import ru.clevertec.clevertecspring.dao.entity.Sex;

import java.util.UUID;

/**
 * DTO для {@link Person}
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonResponse {

    private UUID uuid;
    private String name;
    private String surname;
    private Sex sex;
    private Passport passport;
    private UUID residencyUuid;
    private String createDate;
    private String updateDate;
}
