package ru.clevertec.clevertecspring.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.clevertec.clevertecspring.constant.Constant;
import ru.clevertec.clevertecspring.dao.entity.House;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static ru.clevertec.clevertecspring.constant.Constant.HOUSE_FIND_OWNER_HOUSES_ALL_TIME;
import static ru.clevertec.clevertecspring.constant.Constant.HOUSE_FIND_TENANT_HOUSES_ALL_TIME;
import static ru.clevertec.clevertecspring.constant.Constant.HOUSE_FULL_TEXT_SEARCH;

/**
 * Репозиторий для работы с {@link House}
 *
 * @see Constant
 */
@Repository
public interface HouseRepository extends JpaRepository<House, UUID> {

    Optional<House> findHouseByUuid(UUID uuid);

    Page<House> findAll(Pageable pageable);

    @Query(value = HOUSE_FIND_OWNER_HOUSES_ALL_TIME, nativeQuery = true)
    List<House> findOwnerHousesAllTime(UUID personUuid);

    @Query(value = HOUSE_FIND_TENANT_HOUSES_ALL_TIME, nativeQuery = true)
    List<House> findTenantHousesAllTime(UUID personUuid);

    @Query(value = HOUSE_FULL_TEXT_SEARCH, nativeQuery = true)
    List<House> fullTextSearch(String text);

    House save(House house);

    void deleteHouseByUuid(UUID uuid);
}
