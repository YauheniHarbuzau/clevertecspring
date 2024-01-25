package ru.clevertec.clevertecspring.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.clevertec.clevertecspring.dto.request.HouseRequest;
import ru.clevertec.clevertecspring.dto.response.HouseResponse;
import ru.clevertec.clevertecspring.entity.House;

/**
 * Конвертер для {@link House}, {@link HouseRequest} и {@link HouseResponse}
 */
@Mapper(componentModel = "spring")
public interface HouseMapper {

    HouseResponse toResponse(House house);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    House toHouse(HouseRequest houseRequest);
}
