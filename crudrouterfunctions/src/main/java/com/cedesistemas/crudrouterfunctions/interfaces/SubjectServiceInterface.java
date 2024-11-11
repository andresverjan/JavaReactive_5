package com.cedesistemas.crudrouterfunctions.interfaces;

import com.cedesistemas.crudrouterfunctions.model.Subject;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SubjectServiceInterface {
    Mono<Subject> saveSubject(Subject subject);

    Mono<Subject> getSubjectById(Integer id);

    Flux<Subject> getSubjects();

    Mono<Subject> updateSubjects(Subject student);

    Mono<Void> deleteSubjects(Integer id);
}
