package com.postgre.examplePostgresql;

import io.r2dbc.spi.ConnectionFactory;
import reactor.core.publisher.Mono;

public class DataBaseConnectionTest {
    public Mono<Void> testConnection(ConnectionFactory connectionFactory) {
        return Mono.from(connectionFactory.create()).flatMap(connection -> Mono.from(connection.createStatement("SELECT 1")
                                .execute()).doOnNext(result -> System.out.println("Connection successful!"))
                        .doFinally(signalType -> connection.close()))
                .then()
                .doOnError(throwable -> System.out.println("Connection failed!"));
    }
}
