package com.cedesistemas.crudrouterfunctions.component.grades;

import com.cedesistemas.crudrouterfunctions.interfaces.GradeServiceInterface;
import com.cedesistemas.crudrouterfunctions.model.Grade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class GradeComponentHandler {

    private final GradeServiceInterface gradeServiceInterface;

    public Mono<ServerResponse> getAllGrades(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(gradeServiceInterface.getAllGrades(), Grade.class);
    }

    public Mono<ServerResponse> getGradeById(ServerRequest request) {
        var id = Integer.parseInt(request.pathVariable("id"));
        return ServerResponse.ok().body(gradeServiceInterface.getGradeById(id), Grade.class);
    }

    public Mono<ServerResponse> saveGrade(ServerRequest request) {
        return request.bodyToMono(Grade.class)
                .flatMap(gradeServiceInterface::saveGrade)
                .flatMap(grade -> ServerResponse.ok().bodyValue(grade));
    }

    public Mono<ServerResponse> updateGrade(ServerRequest request) {
        return request.bodyToMono(Grade.class)
                .flatMap(gradeServiceInterface::updateGrade)
                .flatMap(grade -> ServerResponse.ok().bodyValue(grade));
    }

    public Mono<ServerResponse> deleteGrade(ServerRequest request) {
        var id = Integer.parseInt(request.pathVariable("id"));
        return gradeServiceInterface.deleteGrade(id)
                .then(ServerResponse.ok().build());
    }
}
