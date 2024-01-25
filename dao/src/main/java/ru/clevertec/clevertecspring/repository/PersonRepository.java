package ru.clevertec.clevertecspring.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.clevertec.clevertecspring.constant.Constant;
import ru.clevertec.clevertecspring.entity.Person;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static ru.clevertec.clevertecspring.constant.Constant.PERSON_FIND_HOUSE_OWNERS;
import static ru.clevertec.clevertecspring.constant.Constant.PERSON_FIND_HOUSE_OWNERS_ALL_TIME;
import static ru.clevertec.clevertecspring.constant.Constant.PERSON_FIND_HOUSE_RESIDENTS;
import static ru.clevertec.clevertecspring.constant.Constant.PERSON_FIND_HOUSE_RESIDENTS_ALL_TIME;
import static ru.clevertec.clevertecspring.constant.Constant.PERSON_FULL_TEXT_SEARCH;

/**
 * Репозиторий для работы с {@link Person}
 *
 * @see Constant
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, UUID> {

    Optional<Person> findPersonByUuid(UUID uuid);

    Page<Person> findAll(Pageable pageable);

    @Query(value = PERSON_FIND_HOUSE_OWNERS, nativeQuery = true)
    List<Person> findHouseOwners(UUID houseUuid);

    @Query(value = PERSON_FIND_HOUSE_OWNERS_ALL_TIME, nativeQuery = true)
    List<Person> findHouseOwnersAllTime(UUID houseUuid);

    @Query(value = PERSON_FIND_HOUSE_RESIDENTS, nativeQuery = true)
    List<Person> findHouseResidents(UUID houseUuid);

    @Query(value = PERSON_FIND_HOUSE_RESIDENTS_ALL_TIME, nativeQuery = true)
    List<Person> findHouseResidentsAllTime(UUID houseUuid);

    @Query(value = PERSON_FULL_TEXT_SEARCH, nativeQuery = true)
    List<Person> fullTextSearch(String text);

    Person save(Person person);

    void deletePersonByUuid(UUID uuid);
}
