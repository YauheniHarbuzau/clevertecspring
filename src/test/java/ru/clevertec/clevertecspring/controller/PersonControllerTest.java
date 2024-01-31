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
import ru.clevertec.clevertecspring.service.PersonService;
import ru.clevertec.clevertecspring.service.dto.request.PersonRequest;
import ru.clevertec.clevertecspring.service.dto.response.PersonResponse;
import ru.clevertec.clevertecspring.testdatautil.PersonTestData;
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
 * Тестовый класс для {@link PersonController}
 */
@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor
class PersonControllerTest extends PostgreSqlContainerInitializer {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @MockBean
    private final PersonService personService;

    @Test
    void checkGetByUuidShouldReturnCorrectResult_Status200() throws Exception {
        // given
        UUID uuid = PERSON_TEST_UUID_1;
        PersonResponse personResponse = PersonTestData.builder()
                .build().buildPersonResponse();

        doReturn(personResponse)
                .when(personService).getByUuid(uuid);

        // when, then
        mockMvc.perform(get("/persons/{uuid}", uuid))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid").value(uuid.toString()))
                .andExpect(jsonPath("$.name").value("Ivan"))
                .andExpect(jsonPath("$.surname").value("Ivanov"))
                .andExpect(jsonPath("$.sex").value("MALE"));
    }

    @Test
    void checkGetByUuidShouldThrowEntityNotFoundException_Status404() throws Exception {
        // given
        UUID uuid = PERSON_TEST_UUID_1;

        doThrow(EntityNotFoundException.class)
                .when(personService).getByUuid(uuid);

        // when, then
        mockMvc.perform(get("/persons/{uuid}", uuid))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult -> mvcResult.getResolvedException().getClass()
                        .equals(EntityNotFoundException.class));
    }

    @Test
    void checkGetAllShouldReturnCorrectStatus_Status200() throws Exception {
        // given, when, then
        mockMvc.perform(get("/persons"))
                .andExpect(status().isOk());
    }

    @Test
    void checkGetHouseOwnersShouldReturnCorrectResult_Status200() throws Exception {
        // given
        UUID houseUuid = HOUSE_TEST_UUID_1;
        List<PersonResponse> personResponseList = PersonTestData.builder()
                .build().buildPersonResponseList();

        // when
        doReturn(personResponseList)
                .when(personService).getHouseOwners(houseUuid);

        // then
        PersonResponse expectedPersonResponse = personResponseList.get(0);
        mockMvc.perform(get("/persons/owners/{houseuuid}", houseUuid))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$").isArray(),
                        jsonPath("$[0].uuid").value(expectedPersonResponse.getUuid().toString()),
                        jsonPath("$[0].name").value(expectedPersonResponse.getName()),
                        jsonPath("$[0].surname").value(expectedPersonResponse.getSurname()),
                        jsonPath("$[0].sex").value(expectedPersonResponse.getSex().toString()));
    }

    @Test
    void checkGetHouseOwnersAllTimeShouldReturnCorrectResult_Status200() throws Exception {
        // given
        UUID houseUuid = HOUSE_TEST_UUID_1;
        List<PersonResponse> personResponseList = PersonTestData.builder()
                .build().buildPersonResponseList();

        // when
        doReturn(personResponseList)
                .when(personService).getHouseOwnersAllTime(houseUuid);

        // then
        PersonResponse expectedPersonResponse = personResponseList.get(0);
        mockMvc.perform(get("/persons/owners/{houseuuid}/alltime", houseUuid))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$").isArray(),
                        jsonPath("$[0].uuid").value(expectedPersonResponse.getUuid().toString()),
                        jsonPath("$[0].name").value(expectedPersonResponse.getName()),
                        jsonPath("$[0].surname").value(expectedPersonResponse.getSurname()),
                        jsonPath("$[0].sex").value(expectedPersonResponse.getSex().toString()));
    }

    @Test
    void checkGetHouseResidentsShouldReturnCorrectResult_Status200() throws Exception {
        // given
        UUID houseUuid = HOUSE_TEST_UUID_1;
        List<PersonResponse> personResponseList = PersonTestData.builder()
                .build().buildPersonResponseList();

        // when
        doReturn(personResponseList)
                .when(personService).getHouseResidents(houseUuid);

        // then
        PersonResponse expectedPersonResponse = personResponseList.get(0);
        mockMvc.perform(get("/persons/residents/{houseuuid}", houseUuid))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$").isArray(),
                        jsonPath("$[0].uuid").value(expectedPersonResponse.getUuid().toString()),
                        jsonPath("$[0].name").value(expectedPersonResponse.getName()),
                        jsonPath("$[0].surname").value(expectedPersonResponse.getSurname()),
                        jsonPath("$[0].sex").value(expectedPersonResponse.getSex().toString()));
    }

    @Test
    void checkGetHouseTenantsAllTimeShouldReturnCorrectResult_Status200() throws Exception {
        // given
        UUID houseUuid = HOUSE_TEST_UUID_1;
        List<PersonResponse> personResponseList = PersonTestData.builder()
                .build().buildPersonResponseList();

        // when
        doReturn(personResponseList)
                .when(personService).getHouseTenantsAllTime(houseUuid);

        // then
        PersonResponse expectedPersonResponse = personResponseList.get(0);
        mockMvc.perform(get("/persons/tenants/{houseuuid}/alltime", houseUuid))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$").isArray(),
                        jsonPath("$[0].uuid").value(expectedPersonResponse.getUuid().toString()),
                        jsonPath("$[0].name").value(expectedPersonResponse.getName()),
                        jsonPath("$[0].surname").value(expectedPersonResponse.getSurname()),
                        jsonPath("$[0].sex").value(expectedPersonResponse.getSex().toString()));
    }

    @Test
    void checkFullTextSearchShouldReturnCorrectResult_Status200() throws Exception {
        // given
        String textForSearch = "Ivan";
        List<PersonResponse> personResponseList = PersonTestData.builder()
                .build().buildPersonResponseList();

        // when
        doReturn(personResponseList)
                .when(personService).fullTextSearch(textForSearch);

        // then
        PersonResponse expectedPersonResponse1 = personResponseList.get(0);
        PersonResponse expectedPersonResponse2 = personResponseList.get(1);
        mockMvc.perform(get("/persons/fulltextsearch/{text}", textForSearch))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$").isArray(),
                        jsonPath("$[0].uuid").value(expectedPersonResponse1.getUuid().toString()),
                        jsonPath("$[0].name").value(expectedPersonResponse1.getName()),
                        jsonPath("$[0].surname").value(expectedPersonResponse1.getSurname()),
                        jsonPath("$[1].uuid").value(expectedPersonResponse2.getUuid().toString()),
                        jsonPath("$[1].name").value(expectedPersonResponse2.getName()),
                        jsonPath("$[1].surname").value(expectedPersonResponse2.getSurname()));
    }

    @Test
    public void checkCreateShouldWorkCorrectly_Status201() throws Exception {
        // given
        PersonRequest personToCreate = PersonTestData.builder()
                .build().buildPersonRequest();
        PersonResponse personResponse = PersonTestData.builder()
                .build().buildPersonResponse();

        doReturn(personResponse)
                .when(personService).create(personToCreate);

        // when, then
        mockMvc.perform(post("/persons")
                .content(objectMapper.writeValueAsString(personToCreate))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Ivan"))
                .andExpect(jsonPath("$.surname").value("Ivanov"))
                .andExpect(jsonPath("$.sex").value("MALE"));
    }

    @Test
    public void checkCreateShouldThrowEntityNotFoundException_Status4xx() throws Exception {
        // given
        PersonRequest personToCreate = PersonTestData.builder()
                .build().buildPersonRequest();

        doThrow(EntityNotFoundException.class)
                .when(personService).create(personToCreate);

        // when, then
        mockMvc.perform(post("/persons"))
                .andExpect(status().is4xxClientError())
                .andExpect(mvcResult -> mvcResult.getResolvedException().getClass()
                        .equals(EntityNotFoundException.class));
    }

    @Test
    public void checkUpdateShouldWorkCorrectly_Status200() throws Exception {
        // given
        UUID uuid = PERSON_TEST_UUID_1;

        PersonRequest personToUpdate = PersonTestData.builder()
                .withName("Alex")
                .build().buildPersonRequest();
        PersonResponse personResponse = PersonTestData.builder()
                .withName("Alex")
                .build().buildPersonResponse();

        doReturn(personResponse)
                .when(personService).update(uuid, personToUpdate);

        // when, then
        mockMvc.perform(put("/persons/{uuid}", uuid)
                .content(objectMapper.writeValueAsString(personToUpdate))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alex"))
                .andExpect(jsonPath("$.surname").value("Ivanov"))
                .andExpect(jsonPath("$.sex").value("MALE"));
    }

    @Test
    public void checkUpdateShouldThrowEntityNotFoundException_Status4xx() throws Exception {
        // given
        UUID uuid = PERSON_TEST_UUID_1;

        PersonRequest personToUpdate = PersonTestData.builder()
                .withName("Alex")
                .build().buildPersonRequest();

        doThrow(EntityNotFoundException.class)
                .when(personService).update(uuid, personToUpdate);

        // when, then
        mockMvc.perform(put("/persons/{uuid}", uuid))
                .andExpect(status().is4xxClientError())
                .andExpect(mvcResult -> mvcResult.getResolvedException().getClass()
                        .equals(EntityNotFoundException.class));
    }

    @Test
    void checkDeleteByUuidShouldWorkCorrectly_Status204() throws Exception {
        // given
        UUID uuid = PERSON_TEST_UUID_1;

        // when, then
        mockMvc.perform(delete("/persons/{uuid}", uuid))
                .andExpect(status().isNoContent());
    }
}
