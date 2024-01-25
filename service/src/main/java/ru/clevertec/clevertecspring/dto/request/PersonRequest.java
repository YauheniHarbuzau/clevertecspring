package ru.clevertec.clevertecspring.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import ru.clevertec.clevertecspring.entity.House;
import ru.clevertec.clevertecspring.entity.Passport;
import ru.clevertec.clevertecspring.entity.Person;
import ru.clevertec.clevertecspring.entity.enums.Sex;

import java.util.ArrayList;
import java.util.List;
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

    @Valid
    @JsonIgnore
    @Builder.Default
    private List<House> ownedHouses = new ArrayList<>();
}
