package com.crudRF.crudRouterF.component.Config;

import com.crudRF.crudRouterF.component.Handler.MateriaComponentHandler;
import com.crudRF.crudRouterF.component.Handler.NotaComponentHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class NotaRouterConfig {
    @Bean
    public RouterFunction<ServerResponse> Notaroutes (NotaComponentHandler notaComponentHandler){
        return route()
                .GET("/notas/aprobados/{materiaId}", notaComponentHandler::obtenerEstudiantesAprobadosPorMateria)
                .GET("/notas/reprobados/{materiaId}", notaComponentHandler::obtenerEstudiantesReprobadosPorMateria)
                //.GET("/materias/{id}", materiaComponentHandler::getMateriaById)
                .POST("/notas", notaComponentHandler::create)
                //.PUT("/materias", materiaComponentHandler::update)
                //.DELETE("/materias/{id}", materiaComponentHandler::deleteMateriaById)
                .build();
    }
}
