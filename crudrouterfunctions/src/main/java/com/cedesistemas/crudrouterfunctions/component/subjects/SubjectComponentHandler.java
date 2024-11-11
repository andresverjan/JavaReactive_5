package com.cedesistemas.crudrouterfunctions.component.subjects;

import com.cedesistemas.crudrouterfunctions.interfaces.SubjectServiceInterface;
import com.cedesistemas.crudrouterfunctions.model.Subject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class SubjectComponentHandler {

    private final SubjectServiceInterface subjectServiceInterface;

    public Mono<ServerResponse> getAllSubjects(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(subjectServiceInterface.getSubjects(), Subject.class);
    }

    public Mono<ServerResponse> getSubjectById(ServerRequest request) {
        var id = Integer.parseInt(request.pathVariable("id"));
        return ServerResponse.ok().body(subjectServiceInterface.getSubjectById(id), Subject.class);
    }

    public Mono<ServerResponse> saveSubject(ServerRequest request) {
        return request.bodyToMono(Subject.class)
                .flatMap(subjectServiceInterface::saveSubject)
                .flatMap(subject -> ServerResponse.ok().bodyValue(subject));
    }

    public Mono<ServerResponse> updateSubject(ServerRequest request) {
        return request.bodyToMono(Subject.class)
                .flatMap(subjectServiceInterface::updateSubjects)
                .flatMap(subject -> ServerResponse.ok().bodyValue(subject));
    }

    public Mono<ServerResponse> deleteSubject(ServerRequest request) {
        var id = Integer.parseInt(request.pathVariable("id"));
        return subjectServiceInterface.deleteSubjects(id)
                .then(ServerResponse.ok().build());
    }
}
