package com.cedesistemas.crudrouterfunctions.services;

import com.cedesistemas.crudrouterfunctions.exceptions.NotFoundException;
import com.cedesistemas.crudrouterfunctions.interfaces.SubjectServiceInterface;
import com.cedesistemas.crudrouterfunctions.model.Subject;
import com.cedesistemas.crudrouterfunctions.repository.CrudSubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SubjectService implements SubjectServiceInterface {

    private final CrudSubjectRepository crudSubjectRepository;

    @Override
    public Mono<Subject> saveSubject(Subject subject) {
        return crudSubjectRepository.save(subject);
    }

    @Override
    public Mono<Subject> getSubjectById(Integer id) {
        return crudSubjectRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Subject not found with id: " + id)));
    }

    @Override
    public Flux<Subject> getSubjects() {
        return crudSubjectRepository.findAll()
                .switchIfEmpty(Flux.error(new NotFoundException("No subjects found")));
    }

    @Override
    public Mono<Subject> updateSubjects(Subject student) {
        return crudSubjectRepository.findAll()
                .switchIfEmpty(Flux.error(new NotFoundException("No subjects found")))
                .then(crudSubjectRepository.save(student));
    }

    @Override
    public Mono<Void> deleteSubjects(Integer id) {
        return crudSubjectRepository.findAll()
                .switchIfEmpty(Flux.error(new NotFoundException("No subjects found")))
                .then(crudSubjectRepository.deleteById(id));
    }
}
