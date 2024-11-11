package com.cedesistemas.crudrouterfunctions.component.studentssubjects;

import com.cedesistemas.crudrouterfunctions.interfaces.StudentSubjectServiceInterface;
import com.cedesistemas.crudrouterfunctions.model.StudentSubject;
import com.cedesistemas.crudrouterfunctions.model.StudentSubjectGrade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class StudentsSubjectsComponentHandler {

    private final StudentSubjectServiceInterface studentSubjectServiceInterface;

    public Mono<ServerResponse> getAllStudentsSubjects(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(studentSubjectServiceInterface.getAllStudentsSubjects(), StudentSubject.class);
    }

    public Mono<ServerResponse> getStudentSubjectById(ServerRequest request) {
        var id = Integer.parseInt(request.pathVariable("id"));
        return ServerResponse.ok().body(studentSubjectServiceInterface.getStudentSubjectById(id), StudentSubject.class);
    }

    public Mono<ServerResponse> saveStudentSubject(ServerRequest request) {
        return request.bodyToMono(StudentSubject.class)
                .flatMap(studentSubjectServiceInterface::saveStudentSubject)
                .flatMap(studentSubject -> ServerResponse.ok().bodyValue(studentSubject));
    }

    public Mono<ServerResponse> updateStudentSubject(ServerRequest request) {
        return request.bodyToMono(StudentSubject.class)
                .flatMap(studentSubjectServiceInterface::updateStudentSubjects)
                .flatMap(studentSubject -> ServerResponse.ok().bodyValue(studentSubject));
    }

    public Mono<ServerResponse> deleteStudentSubject(ServerRequest request) {
        var id = Integer.parseInt(request.pathVariable("id"));
        return studentSubjectServiceInterface.deleteStudentSubjects(id)
                .then(ServerResponse.ok().build());
    }

    public Mono<ServerResponse> getAllWithGradesAboveThree(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(studentSubjectServiceInterface.getAllWithGradesAboveThree(), StudentSubjectGrade.class);
    }

    public Mono<ServerResponse> getAllWithGradesEqualsThree(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(studentSubjectServiceInterface.getAllWithGradesEqualsThree(), StudentSubjectGrade.class);
    }
}
