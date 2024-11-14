package org.example.component;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfigSubject {

    @Bean
    public RouterFunction<ServerResponse> routes(SubjectComponentHandler subjectComponentHandler) {
        return route()
                .GET("/subject", subjectComponentHandler::getAllSubject)
                .GET("/subject/{id}", subjectComponentHandler::getSubjectById)
                .POST("/subject", subjectComponentHandler::createSubject)
                .PUT("/subject", subjectComponentHandler::updateSubject)
                .DELETE("/subject/{id}", subjectComponentHandler::deleteSubject)
                .POST("/subject/student/{studentId}", subjectComponentHandler::addSubjectToStudent)
                .build();
    }
}
