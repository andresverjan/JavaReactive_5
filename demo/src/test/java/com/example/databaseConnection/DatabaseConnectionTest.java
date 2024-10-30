package com.example.databaseConnection;

import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;



@ExtendWith(SpringExtension .class)
@SpringBootTest
class DatabaseConnectionTest {
    @Autowired
    private ConnectionFactory connectionFactory;

    @InjectMocks
    private DatabaseConnection databaseConnection;

    @Test
    void testConnection() {
        StepVerifier.create(databaseConnection.testConnection(connectionFactory))
                .expectComplete()
                .verify();
    }

}