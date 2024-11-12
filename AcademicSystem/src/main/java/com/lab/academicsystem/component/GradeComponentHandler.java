package com.lab.academicsystem.component;

import com.lab.academicsystem.model.Grade;
import com.lab.academicsystem.model.GradeSummary;
import com.lab.academicsystem.service.ManageGradeService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class GradeComponentHandler {

    private final ManageGradeService manageGradeService;

    public Mono<ServerResponse> createGrade(ServerRequest request) {
        return request.bodyToMono(Grade.class)
                .flatMap(manageGradeService::createGrade)
                .flatMap(s -> ServerResponse.ok()
                        .body(Mono.just(s), Grade.class)
                );
    }

    public Mono<ServerResponse> getAllGrade(ServerRequest request) {
        Flux<Grade> grades = manageGradeService.getAllGrades();
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(grades, Grade.class);
    }

    public Mono<ServerResponse> getGradeById(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return manageGradeService.getGradeById(id)
                .flatMap(grade -> ServerResponse.ok()
                        .bodyValue(grade))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> updateGrade(ServerRequest request) {
        return request.bodyToMono(Grade.class)
                .flatMap(manageGradeService::updateGrade)
                .flatMap(s -> ServerResponse.ok()
                        .bodyValue(s))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deleteGrade(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return manageGradeService.deleteGrade(id)
                .then(ServerResponse.noContent().build())
                .doOnSubscribe(subscription -> System.out.println("Estudiante borrada con el id: " +id))
                .doOnError(error -> System.err.println(error.getMessage()))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getStudentsAproved(ServerRequest request) {
        Flux<GradeSummary> grades = manageGradeService.getStudentsAproved();
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(grades, GradeSummary.class);
    }

    public Mono<ServerResponse> getStudentsNotAproved(ServerRequest request) {
        Flux<GradeSummary> grades = manageGradeService.getStudentsNotAproved();
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(grades, GradeSummary.class);
    }
}
