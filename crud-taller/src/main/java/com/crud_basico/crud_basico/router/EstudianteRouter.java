package com.crud_basico.crud_basico.router;

import com.crud_basico.crud_basico.handler.EstudianteHandler;
import com.crud_basico.crud_basico.handler.MateriaHandler;
import com.crud_basico.crud_basico.handler.NotaHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class EstudianteRouter {

    @Bean
    public RouterFunction<ServerResponse> routes(EstudianteHandler estudianteHandler, MateriaHandler materiaHandler, NotaHandler notaHandler) {
        return route(GET("/estudiantes"), estudianteHandler::findAll)
                .andRoute(GET("/estudiantes/{id}"), estudianteHandler::findById)
                .andRoute(POST("/estudiantes"), estudianteHandler::save)
                .andRoute(DELETE("/estudiantes/{id}"), estudianteHandler::deleteById)
                .andRoute(GET("/materias"), materiaHandler::findAll)
                .andRoute(GET("/materias/{id}"), materiaHandler::findById)
                .andRoute(POST("/materias"), materiaHandler::save)
                .andRoute(DELETE("/materias/{id}"), materiaHandler::deleteById)
                .andRoute(GET("/notas"), notaHandler::findAll)
                .andRoute(GET("/notas/{id}"), notaHandler::findById)
                .andRoute(POST("/notas"), notaHandler::save)
                .andRoute(DELETE("/notas/{id}"), notaHandler::deleteById)
                .andRoute(GET("/reportes/aprobados"), estudianteHandler::reporteAprobados)
                .andRoute(GET("/reportes/reprobados"), estudianteHandler::reporteReprobados);
    }
}
