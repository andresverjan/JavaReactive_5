package com.lab.academicsystem.service;

import com.lab.academicsystem.model.Subject;
import com.lab.academicsystem.repository.SubjectRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ManageSubjectService {

    @Autowired
    private SubjectRespository subjectRespository;

    public Mono<Subject> createSubject(Subject subject){
        return subjectRespository.save(subject)
                .doOnNext(p -> System.out.println("Materia guardada: "+ p));
    }

    public Flux<Subject> getAllSubjects(){
        return subjectRespository.findAll()
                .doOnNext(p -> System.out.println("Lista de Materias: "+ p));
    }

    public Mono<Subject> getSubjectById(Long id){
        if(id == null){
            return Mono.error(new IllegalArgumentException("Materia no encontrada con id: " + id));
        }
        return subjectRespository.findById(id)
                .doOnNext(p -> System.out.println("Materia Encontrada: "+ p));
    }

    public Mono<Subject> updateSubject(Subject subject){
        if(subject.getId() != null){
            return subjectRespository.save(subject)
                    .doOnNext(p -> System.out.println("Materia actualizada: "+ p));
        }else{
            return Mono.error(new IllegalArgumentException("No es posible actualizar la materia ingresada"));

        }

    }

    public Mono<Void> deleteSubject(Long id){
        if(id == null){
            return Mono.error(new NullPointerException("el id no puede ser nulo: " + id));
        }
        return subjectRespository.deleteById(id)
                .doOnNext(p -> System.out.println("Materia eliminada: "+ p + " id: " + id));
    }
}
