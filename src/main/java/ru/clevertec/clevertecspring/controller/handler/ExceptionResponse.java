package ru.clevertec.clevertecspring.controller.handler;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.clevertec.clevertecspring.constant.Constant;

import java.time.LocalDateTime;
import java.util.List;

import static ru.clevertec.clevertecspring.constant.Constant.DATE_TIME_FORMAT;

/**
 * Класс, предоставляющий форму ответа для генерируемых исключений
 *
 * @see ControllerExceptionHandler
 * @see Constant
 */
@Data
@AllArgsConstructor(staticName = "of")
public class ExceptionResponse {

    @JsonFormat(pattern = DATE_TIME_FORMAT)
    private LocalDateTime timestamp;

    private int status;
    private String message;
    private String debugMessage;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> errors;
}
