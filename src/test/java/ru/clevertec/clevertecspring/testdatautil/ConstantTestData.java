package ru.clevertec.clevertecspring.testdatautil;

import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.UUID;

import static java.time.Month.JANUARY;

/**
 * Класс для хранения тестовых констант
 */
public class ConstantTestData {

    public static final UUID PERSON_TEST_UUID_1 = UUID.fromString("6e19a7ae-78f5-475f-92cd-8e838bbc269e");
    public static final UUID PERSON_TEST_UUID_2 = UUID.fromString("36245727-058e-455b-9d42-accc28c05177");

    public static final UUID HOUSE_TEST_UUID_1 = UUID.fromString("7d7369f0-368f-4ed3-9fbb-71e7c7664121");
    public static final UUID HOUSE_TEST_UUID_2 = UUID.fromString("3d2dba22-783b-4a45-9f20-4457dbef611a");

    public static final LocalDateTime TEST_TEST_DATE = LocalDateTime.of(2024, JANUARY, 24, 12, 0, 0);

    public static final PageRequest PAGE_REQUEST = PageRequest.of(0, 15);
}
