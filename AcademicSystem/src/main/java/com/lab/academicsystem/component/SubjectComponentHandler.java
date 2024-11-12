package com.lab.academicsystem.component;

import com.lab.academicsystem.model.Subject;
import com.lab.academicsystem.service.ManageSubjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class SubjectComponentHandler {
    
    private final ManageSubjectService manageSubjectService;

    public Mono<ServerResponse> createSubject(ServerRequest request) {
        return request.bodyToMono(Subject.class)
                .flatMap(manageSubjectService::createSubject)
                .flatMap(s -> ServerResponse.ok()
                        .body(Mono.just(s), Subject.class)
                );
    }

    public Mono<ServerResponse> getAllSubject(ServerRequest request) {
        Flux<Subject> subjects = manageSubjectService.getAllSubjects();
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(subjects, Subject.class);
    }

    public Mono<ServerResponse> getSubjectById(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return manageSubjectService.getSubjectById(id)
                .flatMap(s -> ServerResponse.ok()
                        .bodyValue(s))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> updateSubject(ServerRequest request) {
        return request.bodyToMono(Subject.class)
                .flatMap(manageSubjectService::updateSubject)
                .flatMap(s -> ServerResponse.ok()
                        .bodyValue(s))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deleteSubject(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return manageSubjectService.deleteSubject(id)
                .then(ServerResponse.noContent().build())
                .doOnSubscribe(subscription -> System.out.println("Estudiante borrada con el id: " +id))
                .doOnError(error -> System.err.println(error.getMessage()))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
