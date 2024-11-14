package org.example.component;

import org.example.model.Subject;
import org.example.service.SubjectService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class SubjectComponentHandler {

    private final SubjectService subjectService;

    public SubjectComponentHandler(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    public Mono<ServerResponse> createSubject(ServerRequest request) {
        return request.bodyToMono(Subject.class)
                .flatMap(subjectService::createSubject)
                .flatMap(materia -> ServerResponse.ok().bodyValue(materia));
    }

    public Mono<ServerResponse> getAllSubject(ServerRequest request) {
        return ServerResponse.ok().body(subjectService.getAllSubjects(), Subject.class);
    }

    public Mono<ServerResponse> getSubjectById(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return subjectService.getSubjectById(id)
                .flatMap(subject -> ServerResponse.ok().bodyValue(subject))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> updateSubject(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return request.bodyToMono(Subject.class)
                .flatMap(subject -> subjectService.updateSubject(id, subject))
                .flatMap(subject -> ServerResponse.ok().bodyValue(subject))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deleteSubject(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return subjectService.deleteSubject(id)
                .then(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> addSubjectToStudent(ServerRequest request) {
        Long estudianteId = Long.valueOf(request.pathVariable("estudianteId"));
        return request.bodyToMono(Subject.class)
                .flatMap(subject -> subjectService.addSubjectToStudent(estudianteId, subject))
                .flatMap(student -> ServerResponse.ok().bodyValue(student))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

}