package com.cedesistemas.crudrouterfunctions.component;

import com.cedesistemas.crudrouterfunctions.component.grades.GradeComponentHandler;
import com.cedesistemas.crudrouterfunctions.component.students.StudentComponentHandler;
import com.cedesistemas.crudrouterfunctions.component.studentssubjects.StudentsSubjectsComponentHandler;
import com.cedesistemas.crudrouterfunctions.component.subjects.SubjectComponentHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> routes(StudentComponentHandler handler) {

        return RouterFunctions
                .nest(RequestPredicates.path("/router-student"),
                        RouterFunctions
                                .route(RequestPredicates.GET("/get-all"), handler::getAllStudents)
                                .andRoute(RequestPredicates.GET("/get-by-id/{id}"), handler::getStudentById)
                                .andRoute(RequestPredicates.POST("/save"), handler::saveStudent)
                                .andRoute(RequestPredicates.PUT("/update"), handler::updateStudent)
                                .andRoute(RequestPredicates.DELETE("/delete/{id}"), handler::deleteStudent)
                );
    }

    @Bean
    public RouterFunction<ServerResponse> routesGrades(GradeComponentHandler handler) {

        return RouterFunctions
                .nest(RequestPredicates.path("/router-grades"),
                        RouterFunctions
                                .route(RequestPredicates.GET("/get-all"), handler::getAllGrades)
                                .andRoute(RequestPredicates.GET("/get-by-id/{id}"), handler::getGradeById)
                                .andRoute(RequestPredicates.POST("/save"), handler::saveGrade)
                                .andRoute(RequestPredicates.PUT("/update"), handler::updateGrade)
                                .andRoute(RequestPredicates.DELETE("/delete/{id}"), handler::deleteGrade)
                );
    }

    @Bean
    public RouterFunction<ServerResponse> routerSubjects(SubjectComponentHandler handler) {

        return RouterFunctions
                .nest(RequestPredicates.path("/router-subjects"),
                        RouterFunctions
                                .route(RequestPredicates.GET("/get-all"), handler::getAllSubjects)
                                .andRoute(RequestPredicates.GET("/get-by-id/{id}"), handler::getSubjectById)
                                .andRoute(RequestPredicates.POST("/save"), handler::saveSubject)
                                .andRoute(RequestPredicates.PUT("/update"), handler::updateSubject)
                                .andRoute(RequestPredicates.DELETE("/delete/{id}"), handler::deleteSubject)
                );
    }

    @Bean
    public RouterFunction<ServerResponse> routerStudentsSubjects (StudentsSubjectsComponentHandler handler) {
        return RouterFunctions
                .nest(RequestPredicates.path("/router-students-subjects"),
                        RouterFunctions
                                .route(RequestPredicates.GET("/get-all"), handler::getAllStudentsSubjects)
                                .andRoute(RequestPredicates.GET("/get-by-id/{id}"), handler::getStudentSubjectById)
                                .andRoute(RequestPredicates.POST("/save"), handler::saveStudentSubject)
                                .andRoute(RequestPredicates.PUT("/update"), handler::updateStudentSubject)
                                .andRoute(RequestPredicates.DELETE("/delete/{id}"), handler::deleteStudentSubject)
                                .andRoute(RequestPredicates.GET("/get-average-greater-3"), handler::getAllWithGradesAboveThree)
                                .andRoute(RequestPredicates.GET("/get-average-equals-3"), handler::getAllWithGradesEqualsThree)
                );
    }
}
