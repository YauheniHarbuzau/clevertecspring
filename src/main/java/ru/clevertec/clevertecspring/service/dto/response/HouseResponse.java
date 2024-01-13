package ru.clevertec.clevertecspring.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.clevertec.clevertecspring.dao.entity.House;

import java.util.UUID;

/**
 * DTO для {@link House}
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HouseResponse {

    private UUID uuid;
    private String area;
    private String country;
    private String city;
    private String street;
    private String number;
    private String createDate;
}
