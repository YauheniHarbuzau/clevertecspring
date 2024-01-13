package ru.clevertec.clevertecspring.service.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.clevertec.clevertecspring.dao.entity.House;
import ru.clevertec.clevertecspring.dao.entity.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO для {@link House}
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HouseRequest {

    @NotBlank
    @Size(max = 100)
    private String area;

    @NotBlank
    @Size(max = 100)
    private String country;

    @NotBlank
    @Size(max = 100)
    private String city;

    @NotBlank
    @Size(max = 100)
    private String street;

    @NotBlank
    @Size(max = 10)
    private String number;

    @Valid
    @Builder.Default
    private List<Person> residents = new ArrayList<>();

    @Valid
    @Builder.Default
    private List<Person> owners = new ArrayList<>();
}
