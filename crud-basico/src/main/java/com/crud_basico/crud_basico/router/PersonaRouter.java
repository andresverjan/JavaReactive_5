package com.crud_basico.crud_basico.router;

import com.crud_basico.crud_basico.handler.PersonaHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class PersonaRouter {

    @Bean
    public RouterFunction<ServerResponse> routes(PersonaHandler personaHandler){
        return route()
                .GET("/personas", personaHandler::getPersonasR)
                .GET("/persona/{id}", personaHandler::getPersonaByIdR)
                .POST("/persona/create", personaHandler::createPersona)
                .DELETE("/persona/{id}", personaHandler::deletePersona)
                .build();
    }
}
