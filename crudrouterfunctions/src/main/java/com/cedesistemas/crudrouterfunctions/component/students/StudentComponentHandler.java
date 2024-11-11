package com.cedesistemas.crudrouterfunctions.component.students;

import com.cedesistemas.crudrouterfunctions.interfaces.StudentServiceInterface;
import com.cedesistemas.crudrouterfunctions.model.Student;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class StudentComponentHandler {

    private final StudentServiceInterface studentServiceInterface;

    public Mono<ServerResponse> getAllStudents(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(studentServiceInterface.getAllStudents(), Student.class);
    }

    public Mono<ServerResponse> getStudentById(ServerRequest request) {
        var id = Integer.parseInt(request.pathVariable("id"));
        return ServerResponse.ok().body(studentServiceInterface.getStudentById(id), Student.class);
    }

    public Mono<ServerResponse> saveStudent(ServerRequest request) {
        return request.bodyToMono(Student.class)
                .flatMap(studentServiceInterface::saveStudent)
                .flatMap(student -> ServerResponse.ok().bodyValue(student));
    }

    public Mono<ServerResponse> updateStudent(ServerRequest request) {
        return request.bodyToMono(Student.class)
                .flatMap(studentServiceInterface::updateStudent)
                .flatMap(student -> ServerResponse.ok().bodyValue(student));
    }

    public Mono<ServerResponse> deleteStudent(ServerRequest request) {
        var id = Integer.parseInt(request.pathVariable("id"));
        return studentServiceInterface.deleteStudent(id)
                .then(ServerResponse.ok().build());
    }
}
