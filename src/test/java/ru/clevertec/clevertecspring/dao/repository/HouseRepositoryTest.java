package ru.clevertec.clevertecspring.dao.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.clevertec.clevertecspring.dao.entity.House;
import ru.clevertec.clevertecspring.testdatautil.HouseTestData;
import ru.clevertec.clevertecspring.testdatautil.PostgreSqlContainerInitializer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.clevertec.clevertecspring.testdatautil.ConstantTestData.PERSON_TEST_UUID_1;

/**
 * Тестовый класс для {@link HouseRepository}
 */
@SpringBootTest
@RequiredArgsConstructor
class HouseRepositoryTest extends PostgreSqlContainerInitializer {

    private final HouseRepository houseRepository;

    @Test
    void checkFindHouseByUuidShouldReturnCorrectHouse() {
        // given
        House expectedHouse = HouseTestData.builder()
                .build().buildHouse();

        // when
        House actualHouse = houseRepository.findHouseByUuid(expectedHouse.getUuid()).get();

        // then
        assertThat(actualHouse)
                .hasFieldOrPropertyWithValue(House.Fields.uuid, expectedHouse.getUuid())
                .hasFieldOrPropertyWithValue(House.Fields.area, expectedHouse.getArea())
                .hasFieldOrPropertyWithValue(House.Fields.country, expectedHouse.getCountry())
                .hasFieldOrPropertyWithValue(House.Fields.city, expectedHouse.getCity())
                .hasFieldOrPropertyWithValue(House.Fields.street, expectedHouse.getStreet())
                .hasFieldOrPropertyWithValue(House.Fields.number, expectedHouse.getNumber());
    }

    @Test
    void checkFindHouseByUuidShouldReturnOptionalEmpty() {
        // given
        UUID uuid = UUID.randomUUID();

        // when
        Optional<House> actualHouse = houseRepository.findHouseByUuid(uuid);

        // then
        assertThat(actualHouse).isEmpty();
    }

    @Test
    void checkFindOwnerHousesAllTimeShouldReturnCorrectList() {
        // given, when
        List<House> actualHouseList = houseRepository.findOwnerHousesAllTime(PERSON_TEST_UUID_1);

        // then
        assertAll(
                () -> assertEquals(2, actualHouseList.size()),
                () -> assertEquals("Gomel Region", actualHouseList.get(0).getArea()),
                () -> assertEquals("Belarus", actualHouseList.get(0).getCountry()),
                () -> assertEquals("Gomel", actualHouseList.get(0).getCity()),
                () -> assertEquals("Bazarnaya", actualHouseList.get(0).getStreet()),
                () -> assertEquals("1-A", actualHouseList.get(0).getNumber()),
                () -> assertEquals("Gomel Region", actualHouseList.get(1).getArea()),
                () -> assertEquals("Belarus", actualHouseList.get(1).getCountry()),
                () -> assertEquals("Gomel", actualHouseList.get(1).getCity()),
                () -> assertEquals("Bazarnaya", actualHouseList.get(1).getStreet()),
                () -> assertEquals("1-B", actualHouseList.get(1).getNumber())
        );
    }

    @Test
    void checkFindTenantHousesAllTimeShouldReturnCorrectList() {
        // given, when
        List<House> actualHouseList = houseRepository.findTenantHousesAllTime(PERSON_TEST_UUID_1);

        // then
        assertAll(
                () -> assertEquals(1, actualHouseList.size()),
                () -> assertEquals("Gomel Region", actualHouseList.get(0).getArea()),
                () -> assertEquals("Belarus", actualHouseList.get(0).getCountry()),
                () -> assertEquals("Gomel", actualHouseList.get(0).getCity()),
                () -> assertEquals("Bazarnaya", actualHouseList.get(0).getStreet()),
                () -> assertEquals("1-A", actualHouseList.get(0).getNumber())
        );
    }

    @Test
    void checkFullTextSearchShouldReturnCorrectList() {
        // given, when
        List<House> actualHouseList = houseRepository.fullTextSearch("1-");

        // then
        assertAll(
                () -> assertEquals(2, actualHouseList.size()),
                () -> assertEquals("Gomel Region", actualHouseList.get(0).getArea()),
                () -> assertEquals("Belarus", actualHouseList.get(0).getCountry()),
                () -> assertEquals("Gomel", actualHouseList.get(0).getCity()),
                () -> assertEquals("Bazarnaya", actualHouseList.get(0).getStreet()),
                () -> assertEquals("1-A", actualHouseList.get(0).getNumber()),
                () -> assertEquals("Gomel Region", actualHouseList.get(1).getArea()),
                () -> assertEquals("Belarus", actualHouseList.get(1).getCountry()),
                () -> assertEquals("Gomel", actualHouseList.get(1).getCity()),
                () -> assertEquals("Bazarnaya", actualHouseList.get(1).getStreet()),
                () -> assertEquals("1-B", actualHouseList.get(1).getNumber())
        );
    }
}
