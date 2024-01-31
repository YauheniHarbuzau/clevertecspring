package ru.clevertec.clevertecspring.testdatautil;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import ru.clevertec.clevertecspring.dao.entity.House;
import ru.clevertec.clevertecspring.dao.entity.HouseHistory;
import ru.clevertec.clevertecspring.dao.entity.Person;
import ru.clevertec.clevertecspring.service.dto.request.HouseRequest;
import ru.clevertec.clevertecspring.service.dto.response.HouseResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static java.util.Collections.emptyList;
import static ru.clevertec.clevertecspring.testdatautil.ConstantTestData.HOUSE_TEST_UUID_1;
import static ru.clevertec.clevertecspring.testdatautil.ConstantTestData.TEST_TEST_DATE;

/**
 * Класс для создания тестовых данных
 */
@Data
@Builder(setterPrefix = "with")
public class HouseTestData {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private UUID uuid = HOUSE_TEST_UUID_1;

    @Builder.Default
    private String area = "Gomel Region";

    @Builder.Default
    private String country = "Belarus";

    @Builder.Default
    private String city = "Gomel";

    @Builder.Default
    private String street = "Bazarnaya";

    @Builder.Default
    private String number = "1-A";

    @Builder.Default
    private LocalDateTime createDate = TEST_TEST_DATE;

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
        return new HouseRequest(area, country, city, street, number);
    }

    public HouseResponse buildHouseResponse() {
        return new HouseResponse(uuid, area, country, city, street, number, createDate);
    }

    public List<House> buildHouseList() {
        House house1 = buildHouse();
        House house2 = builder()
                .withId(2L)
                .withNumber("1-B")
                .build().buildHouse();

        return List.of(house1, house2);
    }

    public Page<House> buildHousePage() {
        return new PageImpl<>(buildHouseList());
    }

    public List<HouseResponse> buildHouseResponseList() {
        HouseResponse houseResponse1 = buildHouseResponse();
        HouseResponse houseResponse2 = builder()
                .withNumber("1-B")
                .build().buildHouseResponse();

        return List.of(houseResponse1, houseResponse2);
    }
}
