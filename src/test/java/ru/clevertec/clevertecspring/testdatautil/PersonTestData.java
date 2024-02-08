package ru.clevertec.clevertecspring.testdatautil;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import ru.clevertec.clevertecspring.dao.entity.House;
import ru.clevertec.clevertecspring.dao.entity.HouseHistory;
import ru.clevertec.clevertecspring.dao.entity.Passport;
import ru.clevertec.clevertecspring.dao.entity.Person;
import ru.clevertec.clevertecspring.dao.entity.enums.Sex;
import ru.clevertec.clevertecspring.service.dto.request.PersonRequest;
import ru.clevertec.clevertecspring.service.dto.response.PersonResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static java.util.Collections.emptyList;
import static ru.clevertec.clevertecspring.dao.entity.enums.PassportSeries.KH;
import static ru.clevertec.clevertecspring.dao.entity.enums.Sex.FEMALE;
import static ru.clevertec.clevertecspring.dao.entity.enums.Sex.MALE;
import static ru.clevertec.clevertecspring.testdatautil.ConstantTestData.PERSON_TEST_UUID_1;
import static ru.clevertec.clevertecspring.testdatautil.ConstantTestData.PERSON_TEST_UUID_2;
import static ru.clevertec.clevertecspring.testdatautil.ConstantTestData.TEST_TEST_DATE;

/**
 * Класс для создания тестовых данных
 */
@Data
@Builder(setterPrefix = "with")
public class PersonTestData {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private UUID uuid = PERSON_TEST_UUID_1;

    @Builder.Default
    private String name = "Ivan";

    @Builder.Default
    private String surname = "Ivanov";

    @Builder.Default
    private Sex sex = MALE;

    @Builder.Default
    private Passport passport = PassportTestData.builder().build().buildPassport();

    @Builder.Default
    private LocalDateTime createDate = TEST_TEST_DATE;

    @Builder.Default
    private LocalDateTime updateDate = TEST_TEST_DATE;

    @Builder.Default
    private House residency = HouseTestData.builder().build().buildHouse();

    @Builder.Default
    private UUID residencyUuid = HouseTestData.builder().build().buildHouse().getUuid();

    @Builder.Default
    private List<House> ownedHouses = emptyList();

    @Builder.Default
    private List<HouseHistory> houseHistories = emptyList();

    public Person buildPerson() {
        return new Person(id, uuid, name, surname, sex, passport, createDate, updateDate, residency, ownedHouses, houseHistories);
    }

    public PersonRequest buildPersonRequest() {
        return new PersonRequest(name, surname, sex, passport, residencyUuid);
    }

    public PersonResponse buildPersonResponse() {
        return new PersonResponse(uuid, name, surname, sex, passport, residencyUuid, createDate, updateDate);
    }

    public List<Person> buildPersonList() {
        Person person1 = buildPerson();
        Person person2 = builder()
                .withId(1L)
                .withUuid(PERSON_TEST_UUID_2)
                .withName("Svetlana")
                .withSurname("Ivanova")
                .withSex(FEMALE)
                .withPassport(PassportTestData.builder().withSeries(KH).withNumber("7654321").build().buildPassport())
                .build().buildPerson();

        return List.of(person1, person2);
    }

    public Page<Person> buildPersonPage() {
        return new PageImpl<>(buildPersonList());
    }

    public List<PersonResponse> buildPersonResponseList() {
        PersonResponse personResponse1 = buildPersonResponse();
        PersonResponse personResponse2 = builder()
                .withUuid(PERSON_TEST_UUID_2)
                .withName("Svetlana")
                .withSurname("Ivanova")
                .withSex(FEMALE)
                .withPassport(PassportTestData.builder().withSeries(KH).withNumber("7654321").build().buildPassport())
                .build().buildPersonResponse();

        return List.of(personResponse1, personResponse2);
    }
}
