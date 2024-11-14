package org.example.component;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> routes(StudentComponentHandler studentComponentHandler) {
        return route()
                .GET("/students", studentComponentHandler::getAllStudents)
                .GET("/students/{id}", studentComponentHandler::getStudentById)
                .POST("/students", studentComponentHandler::createStudent)
                .PUT("/students", studentComponentHandler::updateStudent)
                .DELETE("/students/{id}", studentComponentHandler::deleteStudent)
                .GET("/aprobados", studentComponentHandler::getStudentsAprobados)
                .GET("/reprobados", studentComponentHandler::getStudentsReprobados)
                .build();
    }
}
