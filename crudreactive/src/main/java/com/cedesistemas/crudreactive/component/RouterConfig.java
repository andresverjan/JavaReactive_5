package com.cedesistemas.crudreactive.component;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> routes(PersonComponentHandler handler) {
        return route()
                .GET("/router-get-all-persons", handler::getAllPersons)
                .GET("/router-get-by-id/{id}", handler::getPersonById)
                .POST("/router-save-person", handler::savePerson)
                .PUT("/router-update-person", handler::updatePerson)
                .DELETE("/router-delete/{id}", handler::deletePerson).build();
    }
}
