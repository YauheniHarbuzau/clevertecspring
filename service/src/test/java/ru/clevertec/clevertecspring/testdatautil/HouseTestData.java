package ru.clevertec.clevertecspring.testdatautil;

import lombok.Builder;
import lombok.Data;
import ru.clevertec.clevertecspring.dto.request.HouseRequest;
import ru.clevertec.clevertecspring.dto.response.HouseResponse;
import ru.clevertec.clevertecspring.entity.House;
import ru.clevertec.clevertecspring.entity.HouseHistory;
import ru.clevertec.clevertecspring.entity.Person;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static java.time.Month.JANUARY;
import static java.util.Collections.emptyList;

/**
 * Класс для создания тестовых данных
 *
 * @see House
 * @see HouseRequest
 * @see HouseResponse
 */
@Data
@Builder(setterPrefix = "with")
public class HouseTestData {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private UUID uuid = UUID.fromString("5716b871-64c5-451b-bad4-0cf2931e62e4");

    @Builder.Default
    private String area = "Eurasia";

    @Builder.Default
    private String country = "Belarus";

    @Builder.Default
    private String city = "Gomel";

    @Builder.Default
    private String street = "Bazarnaya";

    @Builder.Default
    private String number = "1-A";

    @Builder.Default
    private LocalDateTime createDate = LocalDateTime.of(2024, JANUARY, 24, 12, 0, 0);

    @Builder.Default
    private List<Person> residents = emptyList();

    @Builder.Default
    private List<Person> owners = emptyList();

    @Builder.Default
    private List<HouseHistory> houseHistories = emptyList();

    public House buildHouse() {
        return new House(id, uuid, area, country, city, street, number, createDate, residents, owners, houseHistories);
    }

    public HouseRequest buildHouseRequest() {
        return new HouseRequest(area, country, city, street, number, residents, owners);
    }

    public HouseResponse buildHouseResponse() {
        return new HouseResponse(uuid, area, country, city, street, number, createDate);
    }
}
