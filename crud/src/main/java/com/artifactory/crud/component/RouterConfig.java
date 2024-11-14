package com.artifactory.crud.component;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {
    @Bean
    public RouterFunction<ServerResponse> routes(ComponentHandler componentHandler) {
    return route()
            .POST("/materia/", componentHandler::createMateria)
            .GET("/estudianteAprobados/", componentHandler::getEstudianteAprobados)
            .GET("/estudianteReprobados/", componentHandler::getEstudianteReprobados)
            .GET("/estudiantes/", componentHandler::getEstudiante)
            .GET("/person/", componentHandler::getPersons)
            .GET("/person/{id}", componentHandler::getPersonById)
            .POST("/person/", componentHandler::create)
            .PUT("/person/", componentHandler::update)
            .DELETE("/person/{id}", componentHandler::deletePersonByid)
            .build();
    }
}