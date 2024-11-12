package org.example.activitySix.routerFunction;

import org.example.activitySix.StudentHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;


@Configuration
public class StudentRouter {

    @Bean
    public RouterFunction<ServerResponse> routeStudents(StudentHandler studentHandler) {
        return RouterFunctions.route()
                .GET("/estudiantes", studentHandler::getAllStudents)
                .GET("/estudiantes/aprobados", studentHandler::reportApprovedStudents)      // Obtener reporte de estudiantes aprobados// Listar todos los estudiantes
                .GET("/estudiantes/reprobados", studentHandler::reportFailedStudents)    // Obtener reporte de estudiantes reprobados
                .GET("/estudiantes/{id}", studentHandler::getStudentById)         // Obtener estudiante por ID
                .POST("/estudiantes", studentHandler::createStudent)                    // Crear un nuevo estudiante
                .PUT("/estudiantes/{id}", studentHandler::updateStudent)           // Actualizar un estudiante por ID
                .DELETE("/estudiantes/{id}", studentHandler::deleteStudent)          // Eliminar un estudiante por ID
                .POST("/estudiantes/{id}/materias", studentHandler::createSubject)     // Registrar una materia a un estudiante
                .PUT("/estudiantes/{estudianteId}/materias/{materiaId}", studentHandler::updateNotes) // Actualizar notas de una materia
                .build();
    }
}