package ru.clevertec.clevertecspring.config;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Objects;

/**
 * Конфигурационный класс
 */
@Configuration
@EnableTransactionManagement
@ComponentScan("ru.clevertec.clevertecspring")
public class PropertyConfig {

    @Bean
    public static BeanFactoryPostProcessor beanFactoryPostProcessor() {
        var configure = new PropertySourcesPlaceholderConfigurer();
        var yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("application.yml"));
        var yamlObject = Objects.requireNonNull(yaml.getObject(), "Yaml not found");
        configure.setProperties(yamlObject);
        return configure;
    }
}
