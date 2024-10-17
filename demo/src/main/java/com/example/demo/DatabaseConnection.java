package com.example.demo;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import reactor.core.publisher.Mono;

public class DatabaseConnection {

    //static ConnectionFactory connectionFactory = ConnectionFactories.get("r2dbc:postgresql://postgres:12345@localhost/postgres");

    public Mono<Void> testConnection(ConnectionFactory connectionFactory) {
        return Mono.from(connectionFactory.create())
                .flatMap(connection -> Mono.from(connection.createStatement("SELECT 1").execute())
                        .doOnNext(result -> System.out.println("Connection successful!"))
                        .doFinally(signalType -> connection.close())
                )
                .then()
                .doOnError(throwable -> System.out.println("Connection failed!"));
    }
}
