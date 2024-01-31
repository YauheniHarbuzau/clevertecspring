package ru.clevertec.clevertecspring.constant;

/**
 * Класс для хранения констант
 */
public class Constant {

    public static final String PERSON_FIND_HOUSE_OWNERS = """
            SELECT p.*
            FROM persons p
            JOIN houses_owners ho
                ON p.id = ho.owner_id
            JOIN houses h
                ON ho.house_id = h.id
            WHERE h.uuid = :houseUuid
            """;

    public static final String PERSON_FIND_HOUSE_OWNERS_ALL_TIME = """
            SELECT p.*
            FROM persons p
            JOIN house_history hh
                ON p.id = hh.person_id
                AND hh.ownership = 'OWNER'
            JOIN houses h
                ON hh.house_id = h.id
            WHERE h.uuid = :houseUuid
            """;

    public static final String PERSON_FIND_HOUSE_RESIDENTS = """
            SELECT p.*
            FROM persons p
            JOIN houses h
                ON p.residency_id = h.id
            WHERE h.uuid = :houseUuid
            """;

    public static final String PERSON_FIND_HOUSE_TENANTS_ALL_TIME = """
            SELECT p.*
            FROM persons p
            JOIN house_history hh
                ON p.id = hh.person_id
                AND hh.ownership = 'TENANT'
            JOIN houses h
                ON hh.house_id = h.id
            WHERE h.uuid = :houseUuid
            """;

    public static final String PERSON_FULL_TEXT_SEARCH = """
            SELECT p.*
            FROM persons p
            WHERE CONCAT(name, ' ', surname)
                LIKE '%' || :text || '%'
            """;

    public static final String HOUSE_FIND_OWNER_HOUSES_ALL_TIME = """
            SELECT h.*
            FROM houses h
            JOIN house_history hh
                ON h.id = hh.house_id
                AND hh.ownership = 'OWNER'
            JOIN persons p
                ON hh.person_id = p.id
            WHERE p.uuid = :personUuid
            """;

    public static final String HOUSE_FIND_TENANT_HOUSES_ALL_TIME = """
            SELECT h.*
            FROM houses h
            JOIN house_history hh
                ON h.id = hh.house_id
                AND hh.ownership = 'TENANT'
            JOIN persons p
                ON hh.person_id = p.id
            WHERE p.uuid = :personUuid
            """;

    public static final String HOUSE_FULL_TEXT_SEARCH = """
            SELECT h.*
            FROM houses h
            WHERE CONCAT(area, ' ', country, ' ', city, ' ', street, ' ', number)
                LIKE '%' || :text || '%'
            """;

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss:SSS";

    public static final String ENTITY_NOT_FOUND_EXCEPTION_MESSAGE_FORMAT = "%s not found by: %s";

    public static final String CACHE_ALGORITHM_EXCEPTION_MESSAGE_FORMAT = "Unknown cache algorithm: %s";
}
