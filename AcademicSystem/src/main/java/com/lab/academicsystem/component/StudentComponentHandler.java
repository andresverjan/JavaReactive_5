package com.lab.academicsystem.component;

import com.lab.academicsystem.model.Student;
import com.lab.academicsystem.service.ManageStudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class StudentComponentHandler {

    private final ManageStudentService manageStudentService;

    public Mono<ServerResponse> createStudent(ServerRequest request) {
        return request.bodyToMono(Student.class)
                .flatMap(manageStudentService::createStudent)
                .flatMap(s -> ServerResponse.ok()
                        .body(Mono.just(s), Student.class)
                );
    }

    public Mono<ServerResponse> getAllStudent(ServerRequest request) {
        Flux<Student> students = manageStudentService.getAllStudents();
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(students, Student.class);
    }

    public Mono<ServerResponse> getStudentById(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return manageStudentService.getStudentById(id)
                .flatMap(s -> ServerResponse.ok()
                        .bodyValue(s))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> updateStudent(ServerRequest request) {
        return request.bodyToMono(Student.class)
                .flatMap(manageStudentService::updateStudent)
                .flatMap(s -> ServerResponse.ok()
                        .bodyValue(s))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deleteStudent(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return manageStudentService.deleteStudent(id)
                .then(ServerResponse.noContent().build())
                .doOnSubscribe(subscription -> System.out.println("Estudiante borrada con el id: " +id))
                .doOnError(error -> System.err.println(error.getMessage()))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
