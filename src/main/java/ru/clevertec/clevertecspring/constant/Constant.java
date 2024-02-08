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
    public static final String UNKNOWN_METHOD_EXCEPTION_MESSAGE_FORMAT = "Unknown method: %s";

    public static final String LRU = "LRU";
    public static final String LFU = "LFU";

    public static final String GET_BY_UUID = "getByUuid";
    public static final String GET_ALL = "getAll";
    public static final String CREATE = "create";
    public static final String UPDATE = "update";
    public static final String DELETE_BY_UUID = "deleteByUuid";
    public static final String GET_HOUSE_OWNERS = "getHouseOwners";
    public static final String GET_HOUSE_OWNERS_ALL_TIME = "getHouseOwnersAllTime";
    public static final String GET_HOUSE_RESIDENTS = "getHouseResidents";
    public static final String GET_HOUSE_TENANTS_ALL_TIME = "getHouseTenantsAllTime";
    public static final String GET_OWNER_HOUSES_ALL_TIME = "getOwnerHousesAllTime";
    public static final String GET_TENANT_HOUSES_ALL_TIME = "getTenantHousesAllTime";
    public static final String FULL_TEXT_SEARCH = "fullTextSearch";

    public static final String LOG_BEFORE_PERSON_GET_BY_UUID = "Method: {} - Get Person By UUID: {}";
    public static final String LOG_BEFORE_PERSON_GET_ALL = "Method: {} - Get All Persons; Page Number: {}; Page Size: {}";
    public static final String LOG_BEFORE_PERSON_GET_HOUSE_OWNERS = "Method: {} - Get House Owners By House UUID: {}";
    public static final String LOG_BEFORE_PERSON_GET_HOUSE_OWNERS_ALL_TIME = "Method: {} - Get House Owners For All Time By House UUID: {}";
    public static final String LOG_BEFORE_PERSON_GET_HOUSE_RESIDENTS = "Method: {} - Get House Residents By House UUID: {}";
    public static final String LOG_BEFORE_PERSON_GET_HOUSE_TENANTS_ALL_TIME = "Method: {} - Get House Tenants For All Time By House UUID: {}";
    public static final String LOG_BEFORE_PERSON_FULL_TEXT_SEARCH = "Method: {} - Person Full Text Search By: {}";
    public static final String LOG_BEFORE_PERSON_CREATE = "Method: {} - Create New Person; Person Request: {}";
    public static final String LOG_BEFORE_PERSON_UPDATE = "Method: {} - Update Previous Person; UUID: {}; Person Request: {}";
    public static final String LOG_BEFORE_PERSON_DELETE_BY_UUID = "Method: {} - Delete Person By UUID: {}";
    public static final String LOG_AFTER_RETURNING_PERSON_CREATE = "Method: {} - Person Created; Person Response: {}";
    public static final String LOG_AFTER_RETURNING_PERSON_UPDATE = "Method: {} - Person Updated; Person Response: {}";

    public static final String LOG_BEFORE_HOUSE_GET_BY_UUID = "Method: {} - Get House By UUID: {}";
    public static final String LOG_BEFORE_HOUSE_GET_ALL = "Method: {} - Get All Houses; Page Number: {}; Page Size: {}";
    public static final String LOG_BEFORE_HOUSE_GET_OWNER_HOUSES_ALL_TIME = "Method: {} - Get Houses For All Time By Person (Owner) UUID: {}";
    public static final String LOG_BEFORE_HOUSE_GET_TENANT_HOUSES_ALL_TIME = "Method: {} - Get Houses For All Time By Person (Tenant) UUID: {}";
    public static final String LOG_BEFORE_HOUSE_FULL_TEXT_SEARCH = "Method: {} - House Full Text Search By: {}";
    public static final String LOG_BEFORE_HOUSE_CREATE = "Method: {} - Create New House; House Request: {}";
    public static final String LOG_BEFORE_HOUSE_UPDATE = "Method: {} - Update Previous House; UUID: {}; House Request: {}";
    public static final String LOG_BEFORE_HOUSE_DELETE_BY_UUID = "Method: {} - Delete House By UUID: {}";
    public static final String LOG_AFTER_RETURNING_HOUSE_CREATE = "Method: {} - House Created; House Response: {}";
    public static final String LOG_AFTER_RETURNING_HOUSE_UPDATE = "Method: {} - House Updated; House Response: {}";

    public static final String LOG_AFTER_THROWING = "Method: {} - {}";

    public static final String PERSON_UUID_DESCRIPTION = "Person UUID";
    public static final String PERSON_UUID_EXAMPLE = "6e19a7ae-78f5-475f-92cd-8e838bbc269e";
    public static final String PERSON_TEXT_SEARCH_EXAMPLE = "Ivan";

    public static final String HOUSE_UUID_DESCRIPTION = "House UUID";
    public static final String HOUSE_UUID_EXAMPLE = "7d7369f0-368f-4ed3-9fbb-71e7c7664121";
    public static final String HOUSE_TEXT_SEARCH_EXAMPLE = "Belarus";

    public static final String PERSON_REQUEST_EXAMPLE = """
            {
              "name": "Ivan",
              "surname": "Ivanov",
              "sex": "MALE",
              "passport": {
                "series": "HB",
                "number": "3335577"
              },
              "residencyUuid": "7d7369f0-368f-4ed3-9fbb-71e7c7664121"
            }
            """;

    public static final String HOUSE_REQUEST_EXAMPLE = """
            {
              "area": "Gomel Region",
              "country": "Belarus",
              "city": "Gomel",
              "street": "Bazarnaya",
              "number": "4"
            }
            """;
}
