package com.R2DBC.UsingR2DBC;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class DatabaseConnectionTester {

    public Mono<Void> testConnection(ConnectionFactory connectionFactory) {
        return Mono.from(connectionFactory.create())
                .flatMap(connection ->
                        Mono.from(connection.createStatement("SELECT 1").execute())
                                .doOnNext(result -> System.out.println("Connection successful!"))
                                .doFinally(signalType -> connection.close())
                ).then();
    }
}
