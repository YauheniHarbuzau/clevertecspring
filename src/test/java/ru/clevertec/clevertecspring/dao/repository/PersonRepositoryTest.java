package ru.clevertec.clevertecspring.dao.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.clevertec.clevertecspring.dao.entity.Person;
import ru.clevertec.clevertecspring.testdatautil.PersonTestData;
import ru.clevertec.clevertecspring.testdatautil.PostgreSqlContainerInitializer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.clevertec.clevertecspring.dao.entity.enums.Sex.FEMALE;
import static ru.clevertec.clevertecspring.dao.entity.enums.Sex.MALE;
import static ru.clevertec.clevertecspring.testdatautil.ConstantTestData.HOUSE_TEST_UUID_1;

/**
 * Тестовый класс для {@link PersonRepository}
 */
@SpringBootTest
@RequiredArgsConstructor
class PersonRepositoryTest extends PostgreSqlContainerInitializer {

    private final PersonRepository personRepository;

    @Test
    void checkFindPersonByUuidShouldReturnCorrectPerson() {
        // given
        Person expectedPerson = PersonTestData.builder()
                .build().buildPerson();

        // when
        Person actualPerson = personRepository.findPersonByUuid(expectedPerson.getUuid()).get();

        // then
        assertThat(actualPerson)
                .hasFieldOrPropertyWithValue(Person.Fields.uuid, expectedPerson.getUuid())
                .hasFieldOrPropertyWithValue(Person.Fields.name, expectedPerson.getName())
                .hasFieldOrPropertyWithValue(Person.Fields.surname, expectedPerson.getSurname())
                .hasFieldOrPropertyWithValue(Person.Fields.sex, expectedPerson.getSex());
    }

    @Test
    void checkFindPersonByUuidShouldReturnOptionalEmpty() {
        // given
        UUID uuid = UUID.randomUUID();

        // when
        Optional<Person> actualPerson = personRepository.findPersonByUuid(uuid);

        // then
        assertThat(actualPerson).isEmpty();
    }

    @Test
    void checkFindHouseOwnersShouldReturnCorrectList() {
        // given, when
        List<Person> actualPersonList = personRepository.findHouseOwners(HOUSE_TEST_UUID_1);

        // then
        assertAll(
                () -> assertEquals(1, actualPersonList.size()),
                () -> assertEquals("Ivan", actualPersonList.get(0).getName()),
                () -> assertEquals("Ivanov", actualPersonList.get(0).getSurname()),
                () -> assertEquals(MALE, actualPersonList.get(0).getSex())
        );
    }

    @Test
    void checkFindHouseOwnersAllTimeShouldReturnCorrectList() {
        // given, when
        List<Person> actualPersonList = personRepository.findHouseOwnersAllTime(HOUSE_TEST_UUID_1);

        // then
        assertAll(
                () -> assertEquals(1, actualPersonList.size()),
                () -> assertEquals("Ivan", actualPersonList.get(0).getName()),
                () -> assertEquals("Ivanov", actualPersonList.get(0).getSurname()),
                () -> assertEquals(MALE, actualPersonList.get(0).getSex())
        );
    }

    @Test
    void checkFindHouseResidentsShouldReturnCorrectList() {
        // given, when
        List<Person> actualPersonList = personRepository.findHouseResidents(HOUSE_TEST_UUID_1);

        // then
        assertAll(
                () -> assertEquals(2, actualPersonList.size()),
                () -> assertEquals("Ivan", actualPersonList.get(0).getName()),
                () -> assertEquals("Ivanov", actualPersonList.get(0).getSurname()),
                () -> assertEquals(MALE, actualPersonList.get(0).getSex()),
                () -> assertEquals("Svetlana", actualPersonList.get(1).getName()),
                () -> assertEquals("Ivanova", actualPersonList.get(1).getSurname()),
                () -> assertEquals(FEMALE, actualPersonList.get(1).getSex())
        );
    }

    @Test
    void checkFindHouseTenantsAllTimeShouldReturnCorrectList() {
        // given, when
        List<Person> actualPersonList = personRepository.findHouseTenantsAllTime(HOUSE_TEST_UUID_1);

        // then
        assertAll(
                () -> assertEquals(2, actualPersonList.size()),
                () -> assertEquals("Ivan", actualPersonList.get(0).getName()),
                () -> assertEquals("Ivanov", actualPersonList.get(0).getSurname()),
                () -> assertEquals(MALE, actualPersonList.get(0).getSex()),
                () -> assertEquals("Svetlana", actualPersonList.get(1).getName()),
                () -> assertEquals("Ivanova", actualPersonList.get(1).getSurname()),
                () -> assertEquals(FEMALE, actualPersonList.get(1).getSex())
        );
    }

    @Test
    void checkFullTextSearchShouldReturnCorrectList() {
        // given, when
        List<Person> actualPersonList = personRepository.fullTextSearch("Ivan");

        // then
        assertAll(
                () -> assertEquals(2, actualPersonList.size()),
                () -> assertEquals("Ivan", actualPersonList.get(0).getName()),
                () -> assertEquals("Ivanov", actualPersonList.get(0).getSurname()),
                () -> assertEquals(MALE, actualPersonList.get(0).getSex()),
                () -> assertEquals("Svetlana", actualPersonList.get(1).getName()),
                () -> assertEquals("Ivanova", actualPersonList.get(1).getSurname()),
                () -> assertEquals(FEMALE, actualPersonList.get(1).getSex())
        );
    }
}
