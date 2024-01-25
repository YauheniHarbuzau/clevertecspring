package ru.clevertec.clevertecspring.mapper;

import org.junit.jupiter.api.Test;
import ru.clevertec.clevertecspring.dto.request.HouseRequest;
import ru.clevertec.clevertecspring.dto.response.HouseResponse;
import ru.clevertec.clevertecspring.entity.House;
import ru.clevertec.clevertecspring.testdatautil.HouseTestData;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isNull;

/**
 * Тестовый класс для {@link HouseMapper}
 */
class HouseMapperTest {

    private final HouseMapper houseMapper = new HouseMapperImpl();

    @Test
    void checkToResponseShouldReturnCorrectHouseResponse() {
        // given
        House mappingHouse = HouseTestData.builder()
                .build().buildHouse();

        // when
        HouseResponse actualHouseResponse = houseMapper.toResponse(mappingHouse);

        // than
        assertThat(actualHouseResponse)
                .hasFieldOrPropertyWithValue(HouseResponse.Fields.uuid, mappingHouse.getUuid())
                .hasFieldOrPropertyWithValue(HouseResponse.Fields.area, mappingHouse.getArea())
                .hasFieldOrPropertyWithValue(HouseResponse.Fields.country, mappingHouse.getCountry())
                .hasFieldOrPropertyWithValue(HouseResponse.Fields.city, mappingHouse.getCity())
                .hasFieldOrPropertyWithValue(HouseResponse.Fields.street, mappingHouse.getStreet())
                .hasFieldOrPropertyWithValue(HouseResponse.Fields.number, mappingHouse.getNumber())
                .hasFieldOrPropertyWithValue(HouseResponse.Fields.createDate, mappingHouse.getCreateDate());
    }

    @Test
    void checkToHouseShouldReturnCorrectHouse() {
        // given
        HouseRequest mappingHouseRequest = HouseTestData.builder()
                .build().buildHouseRequest();

        // when
        House actualHouse = houseMapper.toHouse(mappingHouseRequest);

        // than
        assertThat(actualHouse)
                .hasFieldOrPropertyWithValue(House.Fields.id, isNull())
                .hasFieldOrPropertyWithValue(House.Fields.uuid, isNull())
                .hasFieldOrPropertyWithValue(House.Fields.area, actualHouse.getArea())
                .hasFieldOrPropertyWithValue(House.Fields.country, actualHouse.getCountry())
                .hasFieldOrPropertyWithValue(House.Fields.city, actualHouse.getCity())
                .hasFieldOrPropertyWithValue(House.Fields.street, actualHouse.getStreet())
                .hasFieldOrPropertyWithValue(House.Fields.number, actualHouse.getNumber())
                .hasFieldOrPropertyWithValue(House.Fields.createDate, isNull());
    }
}
