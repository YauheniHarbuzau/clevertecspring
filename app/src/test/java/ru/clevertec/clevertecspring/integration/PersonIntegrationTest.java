package ru.clevertec.clevertecspring.integration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.clevertec.clevertecspring.exception.EntityNotFoundException;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Интеграционный тест
 */
@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@Execution(ExecutionMode.CONCURRENT)
public class PersonIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15.2")
            .withDatabaseName("clevertec_spring")
            .withUsername("postgres")
            .withPassword("postgres");

    @DynamicPropertySource
    public static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @BeforeAll
    static void beforeAll() {
        postgreSQLContainer.start();
    }

    @AfterAll
    static void afterAll() {
        postgreSQLContainer.stop();
    }

    @Test
    void checkPersonGetByUuidShouldReturnCorrectResult_Status200() throws Exception {
        // given
        UUID uuid = UUID.fromString("6e19a7ae-78f5-475f-92cd-8e838bbc269e");

        // when, than
        mockMvc.perform(get("/persons/{uuid}", uuid))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid").value(uuid.toString()))
                .andExpect(jsonPath("$.name").value("Ivan"))
                .andExpect(jsonPath("$.surname").value("Ivanov"));
    }

    @Test
    void checkPersonGetByUuidShouldThrowEntityNotFoundException_Status404() throws Exception {
        // given
        UUID uuid = UUID.fromString("7d7369f0-368f-4ed3-9fbb-71e7c7664121");

        // when, than
        mockMvc.perform(get("/persons/{uuid}", uuid))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult -> mvcResult.getResolvedException().getClass()
                        .equals(EntityNotFoundException.class));
    }

    @Test
    void checkPersonGetAllShouldWorkCorrectly_Status200() throws Exception {
        // given, when, than
        mockMvc.perform(get("/persons"))
                .andExpect(status().isOk());
    }

    @Test
    void checkPersonDeleteByUuidShouldWorkCorrectly_Status204() throws Exception {
        // given
        UUID uuid = UUID.fromString("7d7369f0-368f-4ed3-9fbb-71e7c7664121");

        // when, than
        mockMvc.perform(delete("/persons/{uuid}", uuid))
                .andExpect(status().isNoContent());
    }
}
