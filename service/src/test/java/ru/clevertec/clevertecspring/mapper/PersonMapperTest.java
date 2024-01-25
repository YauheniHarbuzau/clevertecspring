package ru.clevertec.clevertecspring.mapper;

import org.junit.jupiter.api.Test;
import ru.clevertec.clevertecspring.dto.request.PersonRequest;
import ru.clevertec.clevertecspring.dto.response.PersonResponse;
import ru.clevertec.clevertecspring.entity.Person;
import ru.clevertec.clevertecspring.testdatautil.PersonTestData;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isNull;

/**
 * Тестовый класс для {@link PersonMapper}
 */
class PersonMapperTest {

    private final PersonMapper personMapper = new PersonMapperImpl();

    @Test
    void checkToResponseShouldReturnCorrectPersonResponse() {
        // given
        Person mappingPerson = PersonTestData.builder()
                .build().buildPerson();

        // when
        PersonResponse actualPersonResponse = personMapper.toResponse(mappingPerson);

        // than
        assertThat(actualPersonResponse)
                .hasFieldOrPropertyWithValue(PersonResponse.Fields.uuid, mappingPerson.getUuid())
                .hasFieldOrPropertyWithValue(PersonResponse.Fields.name, mappingPerson.getName())
                .hasFieldOrPropertyWithValue(PersonResponse.Fields.surname, mappingPerson.getSurname())
                .hasFieldOrPropertyWithValue(PersonResponse.Fields.sex, mappingPerson.getSex())
                .hasFieldOrPropertyWithValue(PersonResponse.Fields.passport, mappingPerson.getPassport())
                .hasFieldOrPropertyWithValue(PersonResponse.Fields.residencyUuid, mappingPerson.getResidency().getUuid())
                .hasFieldOrPropertyWithValue(PersonResponse.Fields.createDate, mappingPerson.getCreateDate())
                .hasFieldOrPropertyWithValue(PersonResponse.Fields.updateDate, mappingPerson.getUpdateDate());
    }

    @Test
    void checkToPersonShouldReturnCorrectPerson() {
        // given
        PersonRequest mappingPersonRequest = PersonTestData.builder()
                .build().buildPersonRequest();

        // when
        Person actualPerson = personMapper.toPerson(mappingPersonRequest);

        // than
        assertThat(actualPerson)
                .hasFieldOrPropertyWithValue(Person.Fields.id, isNull())
                .hasFieldOrPropertyWithValue(Person.Fields.uuid, isNull())
                .hasFieldOrPropertyWithValue(Person.Fields.name, mappingPersonRequest.getName())
                .hasFieldOrPropertyWithValue(Person.Fields.surname, mappingPersonRequest.getSurname())
                .hasFieldOrPropertyWithValue(Person.Fields.sex, mappingPersonRequest.getSex())
                .hasFieldOrPropertyWithValue(Person.Fields.passport, mappingPersonRequest.getPassport())
                .hasFieldOrPropertyWithValue(Person.Fields.createDate, isNull())
                .hasFieldOrPropertyWithValue(Person.Fields.updateDate, isNull());
    }
}
