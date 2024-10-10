package com.artifactory.r2dbc;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping
public class Controller {

    private ConnectionFactory connectionFactory = ConnectionFactories.get("r2dbc:postgresql://postgres:eQr*5vk4omn;v2m@localhost/postgres");
    @Autowired
    Services service;

    @RequestMapping("/testConnection")
    public Mono<Void> testConnection() {

        return service.testConnection(connectionFactory);
    }
    @RequestMapping("/hola")
    public Mono<String> hol() {

        return service.hola();
    }
}
