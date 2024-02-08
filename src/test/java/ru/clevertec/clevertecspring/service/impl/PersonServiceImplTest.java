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
import ru.clevertec.clevertecspring.aop.PersonServiceAspect;
import ru.clevertec.clevertecspring.dao.entity.House;
import ru.clevertec.clevertecspring.dao.entity.Person;
import ru.clevertec.clevertecspring.dao.repository.HouseRepository;
import ru.clevertec.clevertecspring.dao.repository.PersonRepository;
import ru.clevertec.clevertecspring.exception.EntityNotFoundException;
import ru.clevertec.clevertecspring.service.PersonService;
import ru.clevertec.clevertecspring.service.dto.request.PersonRequest;
import ru.clevertec.clevertecspring.service.dto.response.PersonResponse;
import ru.clevertec.clevertecspring.service.mapper.PersonMapper;
import ru.clevertec.clevertecspring.testdatautil.HouseTestData;
import ru.clevertec.clevertecspring.testdatautil.PersonTestData;
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
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static ru.clevertec.clevertecspring.testdatautil.ConstantTestData.HOUSE_TEST_UUID_2;
import static ru.clevertec.clevertecspring.testdatautil.ConstantTestData.PAGE_REQUEST;
import static ru.clevertec.clevertecspring.testdatautil.ConstantTestData.PERSON_TEST_UUID_1;
import static ru.clevertec.clevertecspring.testdatautil.ConstantTestData.PERSON_TEST_UUID_2;

/**
 * Тестовый класс для {@link PersonServiceImpl}
 */
@RequiredArgsConstructor
@SpringBootTest
class PersonServiceImplTest extends PostgreSqlContainerInitializer {

    private final PersonService personService;

    @MockBean
    private final PersonRepository personRepository;

    @MockBean
    private final HouseRepository houseRepository;

    @MockBean
    private final PersonServiceAspect personServiceAspect;

    @SpyBean
    private final PersonMapper personMapper;

    @Captor
    private ArgumentCaptor<Person> personCaptor;

    @Nested
    class GetByUuidTest {
        @Test
        void checkGetByUuidShouldReturnCorrectPersonResponse() {
            // given
            UUID uuid = PERSON_TEST_UUID_1;

            Person expectedPerson = PersonTestData.builder()
                    .build().buildPerson();
            PersonResponse expectedPersonResponse = PersonTestData.builder()
                    .build().buildPersonResponse();

            doReturn(Optional.of(expectedPerson))
                    .when(personRepository).findPersonByUuid(uuid);

            // when
            PersonResponse actualPersonResponse = personService.getByUuid(uuid);

            // then
            assertEquals(expectedPersonResponse, actualPersonResponse);
        }

        @Test
        void checkGetByUuidShouldThrowEntityNotFoundException() {
            // given
            UUID uuidFound = PERSON_TEST_UUID_1;
            UUID uuidNotFound = PERSON_TEST_UUID_2;

            Person person = PersonTestData.builder()
                    .withUuid(uuidFound)
                    .build().buildPerson();
            PersonResponse personResponse = PersonTestData.builder()
                    .withUuid(uuidFound)
                    .build().buildPersonResponse();

            doReturn(Optional.of(person))
                    .when(personRepository).findPersonByUuid(uuidFound);

            // when, then
            assertAll(
                    () -> assertDoesNotThrow(() -> personService.getByUuid(uuidFound)),
                    () -> assertThrows(EntityNotFoundException.class, () -> personService.getByUuid(uuidNotFound))
            );
        }
    }

    @Nested
    class GetAllTest {
        @Test
        void checkGetAllShouldReturnCorrectPageOfPersonResponse() {
            // given
            Page<Person> expectedPersonPage = PersonTestData.builder()
                    .build().buildPersonPage();
            int expectedPageSize = expectedPersonPage.getSize();

            doReturn(expectedPersonPage)
                    .when(personRepository).findAll(any(PageRequest.class));

            // when
            Page<PersonResponse> actualPersonResponsePage = personService.getAll(PAGE_REQUEST);

            // then
            verify(personRepository, times(1)).findAll(any(PageRequest.class));
            assertThat(actualPersonResponsePage.getTotalElements()).isEqualTo(expectedPageSize);
        }

        @Test
        void checkGetAllShouldReturnEmptyPage() {
            // given
            Page<Person> expectedPersonPage = new PageImpl<>(emptyList());

            doReturn(expectedPersonPage)
                    .when(personRepository).findAll(any(PageRequest.class));

            // when
            Page<PersonResponse> actualPersonResponsePage = personService.getAll(PAGE_REQUEST);

            // then
            verify(personRepository, times(1)).findAll(any(PageRequest.class));
            assertThat(actualPersonResponsePage).isEmpty();
        }
    }

    @Nested
    class GetHouseOwnersTest {
        @Test
        void checkGetHouseOwnersShouldReturnCorrectListOfPersonResponse() {
            // given
            UUID houseUuid = HOUSE_TEST_UUID_2;

            List<Person> expectedPersonList = PersonTestData.builder()
                    .build().buildPersonList();
            List<PersonResponse> expectedPersonResponseList = PersonTestData.builder()
                    .build().buildPersonResponseList();

            doReturn(expectedPersonList)
                    .when(personRepository).findHouseOwners(houseUuid);

            // when
            List<PersonResponse> actualPersonResponseList = personService.getHouseOwners(houseUuid);

            // then
            assertAll(
                    () -> assertEquals(expectedPersonResponseList, actualPersonResponseList),
                    () -> assertEquals(2, actualPersonResponseList.size())
            );
        }
    }

    @Nested
    class GetHouseOwnersAllTimeTest {
        @Test
        void checkGetHouseOwnersAllTimeShouldReturnCorrectListOfPersonResponse() {
            // given
            UUID houseUuid = HOUSE_TEST_UUID_2;

            List<Person> expectedPersonList = PersonTestData.builder()
                    .build().buildPersonList();
            List<PersonResponse> expectedPersonResponseList = PersonTestData.builder()
                    .build().buildPersonResponseList();

            doReturn(expectedPersonList)
                    .when(personRepository).findHouseOwnersAllTime(houseUuid);

            // when
            List<PersonResponse> actualPersonResponseList = personService.getHouseOwnersAllTime(houseUuid);

            // then
            assertAll(
                    () -> assertEquals(expectedPersonResponseList, actualPersonResponseList),
                    () -> assertEquals(2, actualPersonResponseList.size())
            );
        }
    }

    @Nested
    class GetHouseResidentsTest {
        @Test
        void checkGetHouseResidentsShouldReturnCorrectListOfPersonResponse() {
            // given
            UUID houseUuid = HOUSE_TEST_UUID_2;

            List<Person> expectedPersonList = PersonTestData.builder()
                    .build().buildPersonList();
            List<PersonResponse> expectedPersonResponseList = PersonTestData.builder()
                    .build().buildPersonResponseList();

            doReturn(expectedPersonList)
                    .when(personRepository).findHouseResidents(houseUuid);

            // when
            List<PersonResponse> actualPersonResponseList = personService.getHouseResidents(houseUuid);

            // then
            assertAll(
                    () -> assertEquals(expectedPersonResponseList, actualPersonResponseList),
                    () -> assertEquals(2, actualPersonResponseList.size())
            );
        }
    }

    @Nested
    class GetHouseResidentsAllTimeTest {
        @Test
        void checkGetHouseTenantsAllTimeShouldReturnCorrectListOfPersonResponse() {
            // given
            UUID houseUuid = HOUSE_TEST_UUID_2;

            List<Person> expectedPersonList = PersonTestData.builder()
                    .build().buildPersonList();
            List<PersonResponse> expectedPersonResponseList = PersonTestData.builder()
                    .build().buildPersonResponseList();

            doReturn(expectedPersonList)
                    .when(personRepository).findHouseTenantsAllTime(houseUuid);

            // when
            List<PersonResponse> actualPersonResponseList = personService.getHouseTenantsAllTime(houseUuid);

            // then
            assertAll(
                    () -> assertEquals(expectedPersonResponseList, actualPersonResponseList),
                    () -> assertEquals(2, actualPersonResponseList.size())
            );
        }
    }

    @Nested
    class FullTextSearchTest {
        @Test
        void checkFullTextSearchShouldReturnCorrectListOfPersonResponse() {
            // given
            String textForSearch = "Ivan";

            List<Person> expectedPersonList = PersonTestData.builder()
                    .build().buildPersonList();
            List<PersonResponse> expectedPersonResponseList = PersonTestData.builder()
                    .build().buildPersonResponseList();

            doReturn(expectedPersonList)
                    .when(personRepository).fullTextSearch(textForSearch);

            // when
            List<PersonResponse> actualPersonResponseList = personService.fullTextSearch(textForSearch);

            // then
            assertAll(
                    () -> assertEquals(expectedPersonResponseList, actualPersonResponseList),
                    () -> assertEquals(2, actualPersonResponseList.size())
            );
        }
    }

    @Nested
    class CreateTest {
        @Test
        void shouldCreateShouldFillInMissingFields() {
            // given
            PersonRequest personToCreate = PersonTestData.builder()
                    .build().buildPersonRequest();
            Person expectedPerson = personMapper.toPerson(personToCreate);

            House houseInDb = HouseTestData.builder()
                    .build().buildHouse();

            doReturn(Optional.of(houseInDb))
                    .when(houseRepository).findHouseByUuid(personToCreate.getResidencyUuid());

            // when
            personService.create(personToCreate);

            // then
            verify(personRepository, times(1)).save(personCaptor.capture());
            Person actualPerson = personCaptor.getValue();
            assertAll(
                    () -> assertThat(actualPerson.getId()).isEqualTo(expectedPerson.getId()),
                    () -> assertThat(actualPerson.getName()).isEqualTo(expectedPerson.getName()),
                    () -> assertThat(actualPerson.getCreateDate()).isNotNull(),
                    () -> assertThat(actualPerson.getUpdateDate()).isNotNull()
            );
        }
    }

    @Nested
    class UpdateTest {
        @Test
        void checkUpdateShouldWorkCorrectly() {
            // given
            UUID personUuid = PERSON_TEST_UUID_2;

            PersonRequest personToUpdate = PersonTestData.builder()
                    .build().buildPersonRequest();
            Person personOldData = PersonTestData.builder()
                    .withUuid(personUuid)
                    .build().buildPerson();

            House houseInDb = HouseTestData.builder()
                    .build().buildHouse();

            doReturn(Optional.of(personOldData))
                    .when(personRepository).findPersonByUuid(personUuid);
            doReturn(Optional.of(houseInDb))
                    .when(houseRepository).findHouseByUuid(personToUpdate.getResidencyUuid());

            // when
            personService.update(personUuid, personToUpdate);

            // then
            verify(personRepository, times(1)).save(personCaptor.capture());
            Person actualPerson = personCaptor.getValue();
            assertAll(
                    () -> assertThat(actualPerson.getId()).isEqualTo(personOldData.getId()),
                    () -> assertThat(actualPerson.getName()).isEqualTo(personToUpdate.getName()),
                    () -> assertThat(actualPerson.getCreateDate()).isEqualTo(personOldData.getCreateDate()),
                    () -> assertThat(actualPerson.getUpdateDate()).isNotNull()
            );
        }
    }

    @Nested
    class DeleteByUuidTest {
        @Test
        void checkDeleteByUuidShouldWorkCorrectly() {
            // given
            UUID personUuid = PERSON_TEST_UUID_1;

            doNothing()
                    .when(personRepository).deletePersonByUuid(personUuid);

            // when
            personService.deleteByUuid(personUuid);

            // then
            verify(personRepository, times(1)).deletePersonByUuid(personUuid);
            assertThat(personRepository.findPersonByUuid(personUuid)).isEmpty();
        }
    }
}
