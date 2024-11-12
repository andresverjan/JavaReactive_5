package org.example.activitySix;

import org.example.activitySix.model.Estudiante;
import org.example.activitySix.model.EstudianteConMateriasYNotas;
import org.example.activitySix.model.Materia;
import org.example.activitySix.service.EstudianteService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class StudentHandler {

    private final EstudianteService studentService;

    public StudentHandler(EstudianteService studentService) {
        this.studentService = studentService;
    }

    public Mono<ServerResponse> createStudent(ServerRequest request) {
        return request.bodyToMono(Estudiante.class)
                .doOnNext(est -> System.out.println("Creando estudiante: " + est))
                .flatMap(studentService::crearEstudiante)
                .flatMap(est -> {
                    System.out.println("Estudiante creado: " + est);
                    return ServerResponse.ok().bodyValue(est);
                })
                .switchIfEmpty(ServerResponse.badRequest().build());
    }

    public Mono<ServerResponse> createSubject(ServerRequest request) {
        var studentId = Long.parseLong(request.pathVariable("id"));
        return request.bodyToMono(Materia.class)
                .doOnNext(materia -> System.out.println("Registrando materia: " + materia + " para estudiante con ID " + studentId))
                .flatMap(materia -> studentService.registrarMateria(studentId, materia))
                .flatMap(materia -> {
                    System.out.println("Materia registrada: " + materia);
                    return ServerResponse.ok().bodyValue(materia);
                })
                .switchIfEmpty(ServerResponse.badRequest().build());
    }

    public Mono<ServerResponse> getAllStudents(ServerRequest request) {
        System.out.println("Listando todos los estudiantes con materias...");
        return ServerResponse.ok()
                .body(studentService.listarEstudiantesConMaterias(), EstudianteConMateriasYNotas.class)
                .doOnNext(est -> System.out.println("Estudiantes listados: " + est));
    }

    public Mono<ServerResponse> getStudentById(ServerRequest request) {
        var id = Long.parseLong(request.pathVariable("id"));
        System.out.println("Obteniendo estudiante con ID: " + id);
        return studentService.obtenerEstudianteConMateriasYNotas(id)
                .flatMap(est -> {
                    System.out.println("Estudiante obtenido: " + est);
                    return ServerResponse.ok().bodyValue(est);
                })
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> updateStudent(ServerRequest request) {
        var id = Long.parseLong(request.pathVariable("id"));
        return request.bodyToMono(Estudiante.class)
                .doOnNext(est -> System.out.println("Actualizando estudiante con ID " + id + ": " + est))
                .flatMap(student -> studentService.actualizarEstudiante(id, student))
                .flatMap(est -> {
                    System.out.println("Estudiante actualizado: " + est);
                    return ServerResponse.ok().bodyValue(est);
                })
                .onErrorResume(e -> ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> updateNotes(ServerRequest request) {
        var studentId = Long.parseLong(request.pathVariable("estudianteId"));
        var subjectId = Long.parseLong(request.pathVariable("materiaId"));
        return request.bodyToMono(Materia.class)
                .doOnNext(materia -> System.out.println("Actualizando notas para materia con ID " + subjectId + " de estudiante con ID " + studentId + ": " + materia))
                .flatMap(materia -> studentService.actualizarNotasMateria(studentId, subjectId, materia))
                .flatMap(updatedMateria -> {
                    System.out.println("Notas actualizadas para materia: " + updatedMateria);
                    return ServerResponse.ok().bodyValue(updatedMateria);
                })
                .onErrorResume(IllegalArgumentException.class, e -> ServerResponse.badRequest().bodyValue(e.getMessage()))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deleteStudent(ServerRequest request) {
        var studentId = Long.parseLong(request.pathVariable("id"));
        System.out.println("Eliminando estudiante con ID: " + studentId);
        return studentService.eliminarEstudiante(studentId)
                .then(ServerResponse.noContent().build())
                .onErrorResume(e -> ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> reportApprovedStudents(ServerRequest request) {
        System.out.println("Generando reporte de estudiantes aprobados...");
        return ServerResponse.ok()
                .body(studentService.reporteEstudiantesAprobados(), Map.class)
                .doOnNext(reporte -> System.out.println("Reporte de aprobados: " + reporte))
                .switchIfEmpty(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> reportFailedStudents(ServerRequest request) {
        System.out.println("Generando reporte de estudiantes reprobados...");
        return studentService.reporteEstudiantesReprobados()
                .doOnNext(reporte -> System.out.println("Reporte generado: " + reporte))
                .collectList()
                .flatMap(reportes -> {
                    if (reportes.isEmpty()) {
                        System.out.println("No se encontraron estudiantes reprobados.");
                        return ServerResponse.noContent().build();
                    } else {
                        System.out.println("Reporte de reprobados: " + reportes);
                        return ServerResponse.ok().bodyValue(reportes);
                    }
                });
    }
}