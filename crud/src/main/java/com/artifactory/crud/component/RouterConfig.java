package com.artifactory.crud.component;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {
    @Bean
    public RouterFunction<ServerResponse> routes(PersonComponentHandler personComponentHandler) {
    return route()
            .GET("/person/", personComponentHandler::getPersons)
            .GET("/person/{id}", personComponentHandler::getPersonById)
            .POST("/person/", personComponentHandler::create)
            .PUT("/person/", personComponentHandler::update)
            .DELETE("/person/{id}", personComponentHandler::deletePersonByid)
            .build();
    }
}