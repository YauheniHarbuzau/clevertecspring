package ru.clevertec.clevertecspring.service.impl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.clevertec.clevertecspring.dao.entity.House;
import ru.clevertec.clevertecspring.dao.repository.HouseRepository;
import ru.clevertec.clevertecspring.exception.EntityNotFoundException;
import ru.clevertec.clevertecspring.service.HouseService;
import ru.clevertec.clevertecspring.service.dto.request.HouseRequest;
import ru.clevertec.clevertecspring.service.dto.response.HouseResponse;
import ru.clevertec.clevertecspring.service.mapper.HouseMapper;
import ru.clevertec.clevertecspring.testdatautil.HouseTestData;
import ru.clevertec.clevertecspring.testdatautil.PostgreSqlContainerInitializer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static ru.clevertec.clevertecspring.testdatautil.ConstantTestData.HOUSE_TEST_UUID_1;
import static ru.clevertec.clevertecspring.testdatautil.ConstantTestData.HOUSE_TEST_UUID_2;
import static ru.clevertec.clevertecspring.testdatautil.ConstantTestData.PAGE_REQUEST;
import static ru.clevertec.clevertecspring.testdatautil.ConstantTestData.PERSON_TEST_UUID_1;

/**
 * Тестовый класс для {@link HouseServiceImpl}
 */
@RequiredArgsConstructor
@SpringBootTest
class HouseServiceImplTest extends PostgreSqlContainerInitializer {

    private final HouseService houseService;

    @MockBean
    private final HouseRepository houseRepository;

    @SpyBean
    private final HouseMapper houseMapper;

    @Captor
    private ArgumentCaptor<House> houseCaptor;

    @Nested
    class GetByUuidTest {
        @Test
        void checkGetByUuidShouldReturnCorrectHouseResponse() {
            // given
            UUID uuid = HOUSE_TEST_UUID_1;

            House expectedHouse = HouseTestData.builder()
                    .build().buildHouse();
            HouseResponse expectedHouseResponse = HouseTestData.builder()
                    .build().buildHouseResponse();

            doReturn(Optional.of(expectedHouse))
                    .when(houseRepository).findHouseByUuid(uuid);

            // when
            HouseResponse actualHouseResponse = houseService.getByUuid(uuid);

            // then
            assertEquals(expectedHouseResponse, actualHouseResponse);
        }

        @Test
        void checkGetByUuidShouldThrowEntityNotFoundException() {
            // given
            UUID uuidFound = HOUSE_TEST_UUID_1;
            UUID uuidNotFound = HOUSE_TEST_UUID_2;

            House house = HouseTestData.builder()
                    .withUuid(uuidFound)
                    .build().buildHouse();
            HouseResponse houseResponse = HouseTestData.builder()
                    .withUuid(uuidFound)
                    .build().buildHouseResponse();

            doReturn(Optional.of(house))
                    .when(houseRepository).findHouseByUuid(uuidFound);

            // when, then
            assertAll(
                    () -> assertDoesNotThrow(() -> houseService.getByUuid(uuidFound)),
                    () -> assertThrows(EntityNotFoundException.class, () -> houseService.getByUuid(uuidNotFound))
            );
        }
    }

    @Nested
    class GetAllTest {
        @Test
        void checkGetAllShouldReturnCorrectPageOfHouseResponse() {
            // given
            Page<House> expectedHousePage = HouseTestData.builder()
                    .build().buildHousePage();
            int expectedPageSize = expectedHousePage.getSize();

            doReturn(expectedHousePage)
                    .when(houseRepository).findAll(any(PageRequest.class));

            // when
            Page<HouseResponse> actualHouseResponsePage = houseService.getAll(PAGE_REQUEST);

            // then
            verify(houseRepository, times(1)).findAll(any(PageRequest.class));
            assertThat(actualHouseResponsePage.getTotalElements()).isEqualTo(expectedPageSize);
        }

        @Test
        void checkGetAllShouldReturnEmptyPage() {
            // given
            Page<House> expectedHousePage = new PageImpl<>(emptyList());

            doReturn(expectedHousePage)
                    .when(houseRepository).findAll(any(PageRequest.class));

            // when
            Page<HouseResponse> actualHouseResponsePage = houseService.getAll(PAGE_REQUEST);

            // then
            verify(houseRepository, times(1)).findAll(any(PageRequest.class));
            assertThat(actualHouseResponsePage).isEmpty();
        }
    }

    @Nested
    class GetOwnerHousesAllTimeTest {
        @Test
        void checkGetOwnerHousesAllTimeShouldReturnCorrectListOfHouseResponse() {
            // given
            UUID personUuid = PERSON_TEST_UUID_1;

            List<House> expectedHouseList = HouseTestData.builder()
                    .build().buildHouseList();
            List<HouseResponse> expectedHouseResponseList = HouseTestData.builder()
                    .build().buildHouseResponseList();

            doReturn(expectedHouseList)
                    .when(houseRepository).findOwnerHousesAllTime(personUuid);

            // when
            List<HouseResponse> actualHouseResponseList = houseService.getOwnerHousesAllTime(personUuid);

            // then
            assertAll(
                    () -> assertEquals(expectedHouseResponseList, actualHouseResponseList),
                    () -> assertEquals(2, actualHouseResponseList.size())
            );
        }
    }

    @Nested
    class GetTenantHousesAllTimeTest {
        @Test
        void checkGetTenantHousesAllTimeShouldReturnCorrectListOfHouseResponse() {
            // given
            UUID personUuid = PERSON_TEST_UUID_1;

            List<House> expectedHouseList = HouseTestData.builder()
                    .build().buildHouseList();
            List<HouseResponse> expectedHouseResponseList = HouseTestData.builder()
                    .build().buildHouseResponseList();

            doReturn(expectedHouseList)
                    .when(houseRepository).findTenantHousesAllTime(personUuid);

            // when
            List<HouseResponse> actualHouseResponseList = houseService.getTenantHousesAllTime(personUuid);

            // then
            assertAll(
                    () -> assertEquals(expectedHouseResponseList, actualHouseResponseList),
                    () -> assertEquals(2, actualHouseResponseList.size())
            );
        }
    }

    @Nested
    class FullTextSearchTest {
        @Test
        void checkFullTextSearchShouldReturnCorrectListOfHouseResponse() {
            // given
            String textForSearch = "Gomel";

            List<House> expectedHouseList = HouseTestData.builder()
                    .build().buildHouseList();
            List<HouseResponse> expectedHouseResponseList = HouseTestData.builder()
                    .build().buildHouseResponseList();

            doReturn(expectedHouseList)
                    .when(houseRepository).fullTextSearch(textForSearch);

            // when
            List<HouseResponse> actualHouseResponseList = houseService.fullTextSearch(textForSearch);

            // then
            assertAll(
                    () -> assertEquals(expectedHouseResponseList, actualHouseResponseList),
                    () -> assertEquals(2, actualHouseResponseList.size())
            );
        }
    }

    @Nested
    class CreateTest {
        @Test
        void shouldCreateShouldFillInMissingFields() {
            // given
            HouseRequest houseToCreate = HouseTestData.builder()
                    .build().buildHouseRequest();
            House expectedHouse = houseMapper.toHouse(houseToCreate);

            // when
            houseService.create(houseToCreate);

            // then
            verify(houseRepository, times(1)).save(houseCaptor.capture());
            House actualHouse = houseCaptor.getValue();
            assertAll(
                    () -> assertThat(actualHouse.getId()).isEqualTo(expectedHouse.getId()),
                    () -> assertThat(actualHouse.getCountry()).isEqualTo(expectedHouse.getCountry()),
                    () -> assertThat(actualHouse.getCreateDate()).isNotNull()
            );
        }
    }

    @Nested
    class UpdateTest {
        @Test
        void checkUpdateShouldWorkCorrectly() {
            // given
            UUID houseUuid = HOUSE_TEST_UUID_2;

            HouseRequest houseToUpdate = HouseTestData.builder()
                    .build().buildHouseRequest();
            House houseOldData = HouseTestData.builder()
                    .withUuid(houseUuid)
                    .build().buildHouse();

            doReturn(Optional.of(houseOldData))
                    .when(houseRepository).findHouseByUuid(houseUuid);

            // when
            houseService.update(houseUuid, houseToUpdate);

            // then
            verify(houseRepository, times(1)).save(houseCaptor.capture());
            House actualHouse = houseCaptor.getValue();
            assertAll(
                    () -> assertThat(actualHouse.getId()).isEqualTo(houseOldData.getId()),
                    () -> assertThat(actualHouse.getCountry()).isEqualTo(houseToUpdate.getCountry()),
                    () -> assertThat(actualHouse.getCreateDate()).isEqualTo(houseOldData.getCreateDate())
            );
        }
    }

    @Nested
    class DeleteByUuidTest {
        @Test
        void checkDeleteByUuidShouldWorkCorrectly() {
            // given
            UUID houseUuid = HOUSE_TEST_UUID_1;

            doNothing()
                    .when(houseRepository).deleteHouseByUuid(houseUuid);

            // when
            houseService.deleteByUuid(houseUuid);

            // then
            verify(houseRepository, times(1)).deleteHouseByUuid(houseUuid);
            assertThat(houseRepository.findHouseByUuid(houseUuid)).isEmpty();
        }
    }
}
