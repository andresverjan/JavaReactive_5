package com.lab.academicsystem.service;

import com.lab.academicsystem.model.Grade;
import com.lab.academicsystem.model.GradeSummary;
import com.lab.academicsystem.repository.GradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ManageGradeService {

    @Autowired
    private GradeRepository gradeRepository;

    public Mono<Grade> createGrade(Grade grade){
        return gradeRepository.save(grade)
                .doOnNext(p -> System.out.println("Estudiante guardada: "+ p));
    }

    public Flux<Grade> getAllGrades(){
        return gradeRepository.findAll()
                .doOnNext(p -> System.out.println("Lista de Estudiantes: "+ p));
    }

    public Mono<Grade> getGradeById(Long id){
        if(id == null){
            return Mono.error(new IllegalArgumentException("Estudiante no encontrada con id: " + id));
        }
        return gradeRepository.findById(id)
                .doOnNext(p -> System.out.println("Estudiante Encontrada: "+ p));
    }

    public Mono<Grade> updateGrade(Grade grade){
        if(grade.getId() != null){
            return gradeRepository.save(grade)
                    .doOnNext(p -> System.out.println("Estudiante actualizada: "+ p));
        }else{
            return Mono.error(new IllegalArgumentException("No es posible actualizar el estudiante ingresado"));

        }

    }

    public Mono<Void> deleteGrade(Long id){
        if(id == null){
            return Mono.error(new NullPointerException("el id no puede ser nulo: " + id));
        }
        return gradeRepository.deleteById(id)
                .doOnNext(p -> System.out.println("Estudiante eliminada: "+ p + " id: " + id));
    }

    public Flux<GradeSummary> getStudentsAproved() {
        return gradeRepository.findStudentsAproved()
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Ningun estudiante aprobó")));
    }

    public Flux<GradeSummary> getStudentsNotAproved() {
        return gradeRepository.findStudentsNotAproved()
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Ningun estudiante aprobó")));
    }







}
