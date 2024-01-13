package ru.clevertec.clevertecspring.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.clevertec.clevertecspring.dao.entity.Person;
import ru.clevertec.clevertecspring.service.dto.request.PersonRequest;
import ru.clevertec.clevertecspring.service.dto.response.PersonResponse;

/**
 * Конвертер для {@link Person}, {@link PersonRequest}, {@link PersonResponse}
 */
@Mapper(componentModel = "spring")
public interface PersonMapper {

    @Mapping(target = "createDate", dateFormat = "yyyy-MM-dd'T'HH:mm:ss:SSS")
    @Mapping(target = "updateDate", dateFormat = "yyyy-MM-dd'T'HH:mm:ss:SSS")
    @Mapping(target = "residencyUuid", source = "residency.uuid")
    PersonResponse toResponse(Person person);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "residency", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    Person toPerson(PersonRequest personRequest);
}
