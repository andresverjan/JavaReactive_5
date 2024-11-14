package org.example.component;

import org.example.model.Student;
import org.example.service.StudentService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class StudentComponentHandler {

    private final StudentService studentService;

    public StudentComponentHandler(StudentService studentService) {
        this.studentService = studentService;
    }

    public Mono<ServerResponse> createStudent(ServerRequest request) {
        return request.bodyToMono(Student.class)
                .flatMap(studentService::createStudent)
                .flatMap(estudiante -> ServerResponse.ok().bodyValue(estudiante));
    }

    public Mono<ServerResponse> getAllStudents(ServerRequest request) {
        return ServerResponse.ok().body(studentService.getAllStudent(), Student.class);
    }

    public Mono<ServerResponse> getStudentById(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return studentService.getStudentById(id)
                .flatMap(estudiante -> ServerResponse.ok().bodyValue(estudiante))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> updateStudent(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return request.bodyToMono(Student.class)
                .flatMap(estudiante -> studentService.updateStudent(id, estudiante))
                .flatMap(estudiante -> ServerResponse.ok().bodyValue(estudiante))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deleteStudent(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return studentService.deleteStudents(id)
                .then(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> getStudentsAprobados(ServerRequest request) {
        return ServerResponse.ok().body(studentService.getStudentsAprobados(), Student.class);
    }

    public Mono<ServerResponse> getStudentsReprobados(ServerRequest request) {
        return ServerResponse.ok().body(studentService.getStudentsReprobados(), Student.class);
    }

}