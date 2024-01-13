package ru.clevertec.clevertecspring.config;

import liquibase.integration.spring.SpringLiquibase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Конфигурационный класс
 */
@Configuration
@RequiredArgsConstructor
@ComponentScan("ru.clevertec.clevertecspring")
public class LiquibaseConfig {

    private final DataSource dataSource;

    @Bean
    public SpringLiquibase liquibase() {
        var liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog("classpath:db.changelog/db.changelog-master.yml");
        return liquibase;
    }
}
