package ru.clevertec.clevertecspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main-класс - точка входа в приложение
 */
@SpringBootApplication(scanBasePackages = "ru.clevertec.*")
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
