package ru.clevertec.clevertecspring.testdatautil;

import lombok.Builder;
import lombok.Data;
import ru.clevertec.clevertecspring.dao.entity.Passport;
import ru.clevertec.clevertecspring.dao.entity.enums.PassportSeries;

import static ru.clevertec.clevertecspring.dao.entity.enums.PassportSeries.HB;

/**
 * Класс для создания тестовых данных
 */
@Data
@Builder(setterPrefix = "with")
public class PassportTestData {

    @Builder.Default
    private PassportSeries series = HB;

    @Builder.Default
    private String number = "1234567";

    public Passport buildPassport() {
        return new Passport(series, number);
    }
}
