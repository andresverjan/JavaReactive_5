package com.crudRF.crudRouterF.component.Config;

import com.crudRF.crudRouterF.component.Handler.MateriaComponentHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class MateriaRouterConfig {
    @Bean
    public RouterFunction<ServerResponse> Materiaroutes (MateriaComponentHandler materiaComponentHandler){
        return route()
                .GET("/materias", materiaComponentHandler::getMaterias)
                .GET("/materias/{id}", materiaComponentHandler::getMateriaById)
                .POST("/materias", materiaComponentHandler::create)
                .PUT("/materias", materiaComponentHandler::update)
                .DELETE("/materias/{id}", materiaComponentHandler::deleteMateriaById)
                .build();
    }
}
