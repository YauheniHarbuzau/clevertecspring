package ru.clevertec.clevertecspring.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.clevertec.clevertecspring.dao.entity.House;
import ru.clevertec.clevertecspring.service.dto.request.HouseRequest;
import ru.clevertec.clevertecspring.service.dto.response.HouseResponse;

/**
 * Конвертер для {@link House}, {@link HouseRequest}, {@link HouseResponse}
 */
@Mapper(componentModel = "spring")
public interface HouseMapper {

    @Mapping(target = "createDate", dateFormat = "yyyy-MM-dd'T'HH:mm:ss:SSS")
    HouseResponse toResponse(House house);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    House toHouse(HouseRequest houseRequest);
}
