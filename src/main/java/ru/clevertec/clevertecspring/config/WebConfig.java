package ru.clevertec.clevertecspring.config;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;
import java.util.List;

/**
 * Конфигурационный класс
 */
@Configuration
@EnableWebMvc
@ComponentScan("ru.clevertec.clevertecspring")
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        var messageConverter = new MappingJackson2HttpMessageConverter();
        messageConverter.setObjectMapper(new JsonMapper());
        messageConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
        converters.add(messageConverter);
    }
}
