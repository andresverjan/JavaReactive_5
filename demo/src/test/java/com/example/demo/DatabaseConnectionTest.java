package com.example.demo;

import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static reactor.core.publisher.Mono.when;


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