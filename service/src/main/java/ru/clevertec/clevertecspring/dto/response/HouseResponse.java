package ru.clevertec.clevertecspring.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import ru.clevertec.clevertecspring.constant.Constant;
import ru.clevertec.clevertecspring.entity.House;

import java.time.LocalDateTime;
import java.util.UUID;

import static ru.clevertec.clevertecspring.constant.Constant.DATE_TIME_FORMAT;

/**
 * DTO для {@link House}
 *
 * @see Constant
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class HouseResponse {

    private UUID uuid;
    private String area;
    private String country;
    private String city;
    private String street;
    private String number;

    @JsonFormat(pattern = DATE_TIME_FORMAT)
    private LocalDateTime createDate;
}
