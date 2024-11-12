package com.lab.myfirstcrud.component;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {
/*
Bean para generar los endpoint de cada una de las operaciones, y dirigirlos al handler
quien enruta al servicio
 */
    @Bean
    public RouterFunction<ServerResponse> routerFunction(PersonComponentHandler personComponentHandler) {
        return route()
                .POST("personR", personComponentHandler::create)
                .GET("personR", personComponentHandler::getAllPersons)
                .GET("personR/{id}", personComponentHandler::getPersonById)
                .PUT("personR", personComponentHandler::updatePerson)
                .DELETE("personR/{id}", personComponentHandler::deletePerson)
                .build();
    }
}
