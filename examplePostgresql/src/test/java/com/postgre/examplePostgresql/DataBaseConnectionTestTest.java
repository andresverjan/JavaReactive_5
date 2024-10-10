package com.postgre.examplePostgresql;

import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class DataBaseConnectionTestTest {

    @Autowired
    private ConnectionFactory connectionFactory;

    @Test
    void testConnection() {
        DataBaseConnectionTest dataBaseConnectionTest = new DataBaseConnectionTest();
        StepVerifier.create(dataBaseConnectionTest.testConnection(connectionFactory))
                .verifyComplete();
    }
}