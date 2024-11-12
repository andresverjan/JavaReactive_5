package com.lab.academicsystem.component;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> routesStudents(StudentComponentHandler studentComponentHandler) {
        return RouterFunctions
                .nest(RequestPredicates.path("/student"),
                    RouterFunctions
                            .route(RequestPredicates.POST(""),studentComponentHandler::createStudent)
                            .andRoute(RequestPredicates.GET(""),studentComponentHandler::getAllStudent)
                            .andRoute(RequestPredicates.GET("/{id}"),studentComponentHandler::getStudentById)
                            .andRoute(RequestPredicates.DELETE("/{id}"),studentComponentHandler::deleteStudent)
                            .andRoute(RequestPredicates.PUT(""),studentComponentHandler::updateStudent)
                );
    }

    @Bean
    public RouterFunction<ServerResponse> routesSubjects(SubjectComponentHandler subjectComponentHandler) {
        return RouterFunctions
                .nest(RequestPredicates.path("/subject"),
                        RouterFunctions
                                .route(RequestPredicates.POST(""),subjectComponentHandler::createSubject)
                                .andRoute(RequestPredicates.GET(""),subjectComponentHandler::getAllSubject)
                                .andRoute(RequestPredicates.GET("/{id}"),subjectComponentHandler::getSubjectById)
                                .andRoute(RequestPredicates.PUT(""),subjectComponentHandler::updateSubject)
                                .andRoute(RequestPredicates.DELETE("/{id}"), subjectComponentHandler::deleteSubject)
                );
    }

    @Bean
    public RouterFunction<ServerResponse> routesGrade(SubjectComponentHandler subjectComponentHandler, GradeComponentHandler gradeComponentHandler) {
        return RouterFunctions
                .nest(RequestPredicates.path("/grade"),
                        RouterFunctions
                                .route(RequestPredicates.POST(""),gradeComponentHandler::createGrade)
                                .andRoute(RequestPredicates.GET(""),gradeComponentHandler::getAllGrade)
                                .andRoute(RequestPredicates.GET("/{id}"),gradeComponentHandler::getGradeById)
                                .andRoute(RequestPredicates.PUT(""),gradeComponentHandler::updateGrade)
                                .andRoute(RequestPredicates.DELETE("/{id}"), gradeComponentHandler::deleteGrade)
                );
    }

    @Bean
    public RouterFunction<ServerResponse> routesStatement(SubjectComponentHandler subjectComponentHandler, GradeComponentHandler gradeComponentHandler) {
        return RouterFunctions
                .nest(RequestPredicates.path("/statement"),
                        RouterFunctions
                                .route(RequestPredicates.GET("/aproved"),gradeComponentHandler::getStudentsAproved)
                                .andRoute(RequestPredicates.GET("/failed"),gradeComponentHandler::getStudentsNotAproved)
                );
    }

}
