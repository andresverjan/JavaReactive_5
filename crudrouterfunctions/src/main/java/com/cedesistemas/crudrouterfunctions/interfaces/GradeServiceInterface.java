package com.cedesistemas.crudrouterfunctions.interfaces;

import com.cedesistemas.crudrouterfunctions.model.Grade;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GradeServiceInterface {
    Mono<Grade> saveGrade(Grade grade);

    Mono<Grade> getGradeById(Integer id);

    Flux<Grade> getAllGrades();

    Mono<Grade> updateGrade(Grade grade);

    Mono<Void> deleteGrade(Integer id);
}
