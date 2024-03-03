package com.example;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

class MyTest {
    private static final PostgreSQLContainer<?> postgresContainer =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"))
                    .withDatabaseName("mydb")
                    .withUsername("user")
                    .withPassword("admin");

    // Inicjalizacja kontenera przed testami
    @BeforeAll
    static void startContainer() {
        postgresContainer.start();
    }

    @AfterAll
    static void stopContainer() {
        postgresContainer.stop();
    }

    @Test
    void testDatabaseConnection() throws SQLException {
        // Pobierz dane dostępu do bazy z kontenera
        String jdbcUrl = postgresContainer.getJdbcUrl();
        String username = postgresContainer.getUsername();
        String password = postgresContainer.getPassword();

        // Nawiąż połączenie z bazą danych
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            assertNotNull(connection);
        }
    }
}
