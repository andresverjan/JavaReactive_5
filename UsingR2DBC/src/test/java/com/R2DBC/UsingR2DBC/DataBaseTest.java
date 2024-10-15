package com.R2DBC.UsingR2DBC;

import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class DataBaseTest{
    @Autowired
    private ConnectionFactory connectionFactory;
    @Test
    public void testConnection(){
        DatabaseConnectionTester databaseConnectionTester = new DatabaseConnectionTester();
        StepVerifier.create(databaseConnectionTester.testConnection(connectionFactory))
            .verifyComplete();
    }
}