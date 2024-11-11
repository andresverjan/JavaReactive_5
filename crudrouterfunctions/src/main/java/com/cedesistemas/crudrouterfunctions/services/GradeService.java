package com.cedesistemas.crudrouterfunctions.services;

import com.cedesistemas.crudrouterfunctions.exceptions.NotFoundException;
import com.cedesistemas.crudrouterfunctions.interfaces.GradeServiceInterface;
import com.cedesistemas.crudrouterfunctions.model.Grade;
import com.cedesistemas.crudrouterfunctions.repository.CrudGradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GradeService implements GradeServiceInterface {
    private final CrudGradeRepository crudGradeRepository;

    @Override
    public Mono<Grade> saveGrade(Grade grade) {
        return crudGradeRepository.save(grade);
    }

    @Override
    public Mono<Grade> getGradeById(Integer id) {
        return crudGradeRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Grade not found with id: " + id)));
    }

    @Override
    public Flux<Grade> getAllGrades() {
        return crudGradeRepository.findAll()
                .switchIfEmpty(Flux.error(new NotFoundException("No grades found")));
    }

    @Override
    public Mono<Grade> updateGrade(Grade grade) {
        return crudGradeRepository.findAll()
                .switchIfEmpty(Flux.error(new NotFoundException("No grades found")))
                .then(crudGradeRepository.save(grade));
    }

    @Override
    public Mono<Void> deleteGrade(Integer id) {
        return crudGradeRepository.findAll()
                .switchIfEmpty(Flux.error(new NotFoundException("No grades found")))
                .then(crudGradeRepository.deleteById(id));
    }
}
