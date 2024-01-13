package ru.clevertec.clevertecspring.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Конфигурационный класс
 */
@Configuration
@RequiredArgsConstructor
@ComponentScan("ru.clevertec.clevertecspring")
public class JdbcConfig {

    private final DataSource dataSource;

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
