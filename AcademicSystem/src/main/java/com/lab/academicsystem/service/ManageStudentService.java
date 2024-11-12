package com.lab.academicsystem.service;

import com.lab.academicsystem.model.Student;
import com.lab.academicsystem.model.Subject;
import com.lab.academicsystem.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ManageStudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Mono<Student> createStudent(Student student){
        return studentRepository.save(student)
                .doOnNext(p -> System.out.println("Estudiante guardada: "+ p));
    }

    public Flux<Student> getAllStudents(){
        return studentRepository.findAll()
                .doOnNext(p -> System.out.println("Lista de Estudiantes: "+ p));
    }

    public Mono<Student> getStudentById(Long id){
        if(id == null){
            return Mono.error(new IllegalArgumentException("Estudiante no encontrada con id: " + id));
        }
        return studentRepository.findById(id)
                .doOnNext(p -> System.out.println("Estudiante Encontrada: "+ p));
    }

    public Mono<Student> updateStudent(Student student){
        if(student.getId() != null){
            return studentRepository.save(student)
                    .doOnNext(p -> System.out.println("Estudiante actualizada: "+ p));
        }else{
            return Mono.error(new IllegalArgumentException("No es posible actualizar el estudiante ingresado"));

        }

    }

    public Mono<Void> deleteStudent(Long id){
        if(id == null){
            return Mono.error(new NullPointerException("el id no puede ser nulo: " + id));
        }
        return studentRepository.deleteById(id)
                .doOnNext(p -> System.out.println("Estudiante eliminada: "+ p + " id: " + id));
    }
}
