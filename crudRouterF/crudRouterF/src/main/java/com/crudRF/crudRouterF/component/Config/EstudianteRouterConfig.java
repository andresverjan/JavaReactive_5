package com.crudRF.crudRouterF.component.Config;

import com.crudRF.crudRouterF.component.Handler.EstudianteComponentHandler;
import com.crudRF.crudRouterF.component.Handler.MateriaComponentHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class EstudianteRouterConfig {
    @Bean
    public RouterFunction<ServerResponse> Estudianteroutes (EstudianteComponentHandler estudianteComponentHandler){
        return route()
                .GET("/estudiantes", estudianteComponentHandler::getEstudiantes)
                .GET("/estudiantes/{id}", estudianteComponentHandler::getEstudianteById)
                .POST("/estudiantes", estudianteComponentHandler::create)
                .PUT("/estudiantes", estudianteComponentHandler::update)
                .DELETE("/estudiantes/{id}", estudianteComponentHandler::deleteEstudianteById)
                .build();
    }
}
