package ru.clevertec.clevertecspring.service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import ru.clevertec.clevertecspring.dao.entity.Passport;
import ru.clevertec.clevertecspring.dao.entity.Person;
import ru.clevertec.clevertecspring.dao.entity.enums.Sex;

import java.util.UUID;

/**
 * DTO для {@link Person}
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class PersonRequest {

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotBlank
    @Size(max = 100)
    private String surname;

    @NotNull
    private Sex sex;

    @NotNull
    private Passport passport;

    @NotNull
    private UUID residencyUuid;
}
