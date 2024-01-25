package ru.clevertec.clevertecspring.testdatautil;

import lombok.Builder;
import lombok.Data;
import ru.clevertec.clevertecspring.dto.request.PersonRequest;
import ru.clevertec.clevertecspring.dto.response.PersonResponse;
import ru.clevertec.clevertecspring.entity.House;
import ru.clevertec.clevertecspring.entity.HouseHistory;
import ru.clevertec.clevertecspring.entity.Passport;
import ru.clevertec.clevertecspring.entity.Person;
import ru.clevertec.clevertecspring.entity.enums.Sex;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static java.time.Month.JANUARY;
import static java.util.Collections.emptyList;
import static ru.clevertec.clevertecspring.entity.enums.Sex.MALE;

/**
 * Класс для создания тестовых данных
 *
 * @see Person
 * @see PersonRequest
 * @see PersonResponse
 */
@Data
@Builder(setterPrefix = "with")
public class PersonTestData {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private UUID uuid = UUID.fromString("cb7f2552-a60d-4831-a5f2-ab1cb1262def");

    @Builder.Default
    private String name = "Ivan";

    @Builder.Default
    private String surname = "Ivanov";

    @Builder.Default
    private Sex sex = MALE;

    @Builder.Default
    private Passport passport = new Passport("HB", "1234567");

    @Builder.Default
    private LocalDateTime createDate = LocalDateTime.of(2024, JANUARY, 24, 12, 0, 0);

    @Builder.Default
    private LocalDateTime updateDate = LocalDateTime.of(2024, JANUARY, 24, 12, 0, 0);

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
        return new PersonRequest(name, surname, sex, passport, residencyUuid, ownedHouses);
    }

    public PersonResponse buildPersonResponse() {
        return new PersonResponse(uuid, name, surname, sex, passport, residencyUuid, createDate, updateDate);
    }
}
