package com.candelo.mariela.database_conection;

import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class DatabaseConnectionTestTest {

    @Autowired
    private ConnectionFactory connectionFactory;

    @Test
    public void testConnection(){
        DatabaseConnectionTest databaseConnectionTest = new DatabaseConnectionTest();
        StepVerifier.create(databaseConnectionTest.testConnection(connectionFactory))
                .verifyComplete();
    }


}
