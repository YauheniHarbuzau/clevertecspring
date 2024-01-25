package ru.clevertec.clevertecspring.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.clevertec.clevertecspring.dto.request.PersonRequest;
import ru.clevertec.clevertecspring.dto.response.PersonResponse;
import ru.clevertec.clevertecspring.entity.Person;

/**
 * Конвертер для {@link Person}, {@link PersonRequest} и {@link PersonResponse}
 */
@Mapper(componentModel = "spring")
public interface PersonMapper {

    @Mapping(target = "residencyUuid", source = "residency.uuid")
    PersonResponse toResponse(Person person);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "residency", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    Person toPerson(PersonRequest personRequest);
}
