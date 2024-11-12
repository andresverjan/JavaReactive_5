package com.candelo.mariela.database_conection;

import reactor.core.publisher.Mono;
import io.r2dbc.spi.ConnectionFactory;

public class DatabaseConnectionTest {

    public Mono<Void> testConnection(ConnectionFactory connectionFactory) {
        return Mono.from(connectionFactory.create())
                .flatMap(connection ->
                        Mono.from(connection.createStatement("SELECT 1").execute())
                                .doOnNext(result -> System.out.println("Connection successful!"))
                                .doFinally(signalType -> connection.close())        )
                .then()
                .doOnError(e -> System.err.println("Connection failed: " + e.getMessage()));
    }
}
