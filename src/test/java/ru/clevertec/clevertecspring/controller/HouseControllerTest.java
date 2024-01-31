package ru.clevertec.clevertecspring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.clevertec.clevertecspring.exception.EntityNotFoundException;
import ru.clevertec.clevertecspring.service.HouseService;
import ru.clevertec.clevertecspring.service.dto.request.HouseRequest;
import ru.clevertec.clevertecspring.service.dto.response.HouseResponse;
import ru.clevertec.clevertecspring.testdatautil.HouseTestData;
import ru.clevertec.clevertecspring.testdatautil.PostgreSqlContainerInitializer;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.clevertec.clevertecspring.testdatautil.ConstantTestData.HOUSE_TEST_UUID_1;
import static ru.clevertec.clevertecspring.testdatautil.ConstantTestData.PERSON_TEST_UUID_1;

/**
 * Тестовый класс для {@link HouseController}
 */
@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor
class HouseControllerTest extends PostgreSqlContainerInitializer {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @MockBean
    private final HouseService houseService;

    @Test
    void checkGetByUuidShouldReturnCorrectResult_Status200() throws Exception {
        // given
        UUID uuid = HOUSE_TEST_UUID_1;
        HouseResponse houseResponse = HouseTestData.builder()
                .build().buildHouseResponse();

        doReturn(houseResponse)
                .when(houseService).getByUuid(uuid);

        // when, then
        mockMvc.perform(get("/houses/{uuid}", uuid))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid").value(uuid.toString()))
                .andExpect(jsonPath("$.area").value("Gomel Region"))
                .andExpect(jsonPath("$.country").value("Belarus"))
                .andExpect(jsonPath("$.city").value("Gomel"))
                .andExpect(jsonPath("$.street").value("Bazarnaya"))
                .andExpect(jsonPath("$.number").value("1-A"));
    }

    @Test
    void checkGetByUuidShouldThrowEntityNotFoundException_Status404() throws Exception {
        // given
        UUID uuid = HOUSE_TEST_UUID_1;

        doThrow(EntityNotFoundException.class)
                .when(houseService).getByUuid(uuid);

        // when, then
        mockMvc.perform(get("/houses/{uuid}", uuid))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult -> mvcResult.getResolvedException().getClass()
                        .equals(EntityNotFoundException.class));
    }

    @Test
    void checkGetAllShouldReturnCorrectStatus_Status200() throws Exception {
        // given, when, then
        mockMvc.perform(get("/houses"))
                .andExpect(status().isOk());
    }

    @Test
    void checkGetOwnerHousesAllTimeShouldReturnCorrectResult_Status200() throws Exception {
        // given
        UUID personUuid = PERSON_TEST_UUID_1;
        List<HouseResponse> houseResponseList = HouseTestData.builder()
                .build().buildHouseResponseList();

        // when
        doReturn(houseResponseList)
                .when(houseService).getOwnerHousesAllTime(personUuid);

        // then
        HouseResponse expectedHouseResponse = houseResponseList.get(0);
        mockMvc.perform(get("/houses/owner/{personuuid}/alltime", personUuid))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$").isArray(),
                        jsonPath("$[0].uuid").value(expectedHouseResponse.getUuid().toString()),
                        jsonPath("$[0].area").value(expectedHouseResponse.getArea()),
                        jsonPath("$[0].country").value(expectedHouseResponse.getCountry()),
                        jsonPath("$[0].city").value(expectedHouseResponse.getCity()),
                        jsonPath("$[0].street").value(expectedHouseResponse.getStreet()),
                        jsonPath("$[0].number").value(expectedHouseResponse.getNumber()));
    }

    @Test
    void checkGetTenantHousesAllTimeShouldReturnCorrectResult_Status200() throws Exception {
        // given
        UUID personUuid = PERSON_TEST_UUID_1;
        List<HouseResponse> houseResponseList = HouseTestData.builder()
                .build().buildHouseResponseList();

        // when
        doReturn(houseResponseList)
                .when(houseService).getTenantHousesAllTime(personUuid);

        // then
        HouseResponse expectedHouseResponse = houseResponseList.get(0);
        mockMvc.perform(get("/houses/tenant/{personuuid}/alltime", personUuid))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$").isArray(),
                        jsonPath("$[0].uuid").value(expectedHouseResponse.getUuid().toString()),
                        jsonPath("$[0].area").value(expectedHouseResponse.getArea()),
                        jsonPath("$[0].country").value(expectedHouseResponse.getCountry()),
                        jsonPath("$[0].city").value(expectedHouseResponse.getCity()),
                        jsonPath("$[0].street").value(expectedHouseResponse.getStreet()),
                        jsonPath("$[0].number").value(expectedHouseResponse.getNumber()));
    }

    @Test
    void checkFullTextSearchShouldReturnCorrectResult_Status200() throws Exception {
        // given
        String textForSearch = "1-";
        List<HouseResponse> houseResponseList = HouseTestData.builder()
                .build().buildHouseResponseList();

        // when
        doReturn(houseResponseList)
                .when(houseService).fullTextSearch(textForSearch);

        // then
        HouseResponse expectedHouseResponse1 = houseResponseList.get(0);
        HouseResponse expectedHouseResponse2 = houseResponseList.get(1);
        mockMvc.perform(get("/houses/fulltextsearch/{text}", textForSearch))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$").isArray(),
                        jsonPath("$[0].uuid").value(expectedHouseResponse1.getUuid().toString()),
                        jsonPath("$[0].number").value(expectedHouseResponse1.getNumber()),
                        jsonPath("$[1].uuid").value(expectedHouseResponse2.getUuid().toString()),
                        jsonPath("$[1].number").value(expectedHouseResponse2.getNumber()));
    }

    @Test
    public void checkCreateShouldWorkCorrectly_Status201() throws Exception {
        // given
        HouseRequest houseToCreate = HouseTestData.builder()
                .build().buildHouseRequest();
        HouseResponse houseResponse = HouseTestData.builder()
                .build().buildHouseResponse();

        doReturn(houseResponse)
                .when(houseService).create(houseToCreate);

        // when, then
        mockMvc.perform(post("/houses")
                .content(objectMapper.writeValueAsString(houseToCreate))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.area").value("Gomel Region"))
                .andExpect(jsonPath("$.country").value("Belarus"))
                .andExpect(jsonPath("$.city").value("Gomel"))
                .andExpect(jsonPath("$.street").value("Bazarnaya"))
                .andExpect(jsonPath("$.number").value("1-A"));
    }

    @Test
    public void checkUpdateShouldWorkCorrectly_Status200() throws Exception {
        // given
        UUID uuid = HOUSE_TEST_UUID_1;

        HouseRequest houseToUpdate = HouseTestData.builder()
                .withNumber("1-B")
                .build().buildHouseRequest();
        HouseResponse houseResponse = HouseTestData.builder()
                .withNumber("1-B")
                .build().buildHouseResponse();

        doReturn(houseResponse)
                .when(houseService).update(uuid, houseToUpdate);

        // when, then
        mockMvc.perform(put("/houses/{uuid}", uuid)
                .content(objectMapper.writeValueAsString(houseToUpdate))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.area").value("Gomel Region"))
                .andExpect(jsonPath("$.country").value("Belarus"))
                .andExpect(jsonPath("$.city").value("Gomel"))
                .andExpect(jsonPath("$.street").value("Bazarnaya"))
                .andExpect(jsonPath("$.number").value("1-B"));
    }

    @Test
    public void checkUpdateShouldThrowEntityNotFoundException_Status4xx() throws Exception {
        // given
        UUID uuid = HOUSE_TEST_UUID_1;

        HouseRequest houseToUpdate = HouseTestData.builder()
                .withNumber("1-B")
                .build().buildHouseRequest();

        doThrow(EntityNotFoundException.class)
                .when(houseService).update(uuid, houseToUpdate);

        // when, then
        mockMvc.perform(put("/houses/{uuid}", uuid))
                .andExpect(status().is4xxClientError())
                .andExpect(mvcResult -> mvcResult.getResolvedException().getClass()
                        .equals(EntityNotFoundException.class));
    }

    @Test
    void checkDeleteByUuidShouldWorkCorrectly_Status204() throws Exception {
        // given
        UUID uuid = HOUSE_TEST_UUID_1;

        // when, then
        mockMvc.perform(delete("/houses/{uuid}", uuid))
                .andExpect(status().isNoContent());
    }
}
