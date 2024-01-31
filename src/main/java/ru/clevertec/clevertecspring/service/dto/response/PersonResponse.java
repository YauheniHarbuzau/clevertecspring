package ru.clevertec.clevertecspring.service.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import ru.clevertec.clevertecspring.constant.Constant;
import ru.clevertec.clevertecspring.dao.entity.Passport;
import ru.clevertec.clevertecspring.dao.entity.Person;
import ru.clevertec.clevertecspring.dao.entity.enums.Sex;

import java.time.LocalDateTime;
import java.util.UUID;

import static ru.clevertec.clevertecspring.constant.Constant.DATE_TIME_FORMAT;

/**
 * DTO для {@link Person}
 *
 * @see Constant
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class PersonResponse {

    private UUID uuid;
    private String name;
    private String surname;
    private Sex sex;
    private Passport passport;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UUID residencyUuid;

    @JsonFormat(pattern = DATE_TIME_FORMAT)
    private LocalDateTime createDate;

    @JsonFormat(pattern = DATE_TIME_FORMAT)
    private LocalDateTime updateDate;
}
