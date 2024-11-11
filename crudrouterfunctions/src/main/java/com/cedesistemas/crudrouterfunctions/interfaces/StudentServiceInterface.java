package com.cedesistemas.crudrouterfunctions.interfaces;

import com.cedesistemas.crudrouterfunctions.model.Student;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StudentServiceInterface {
    Mono<Student> saveStudent(Student student);

    Mono<Student> getStudentById(Integer id);

    Flux<Student> getAllStudents();

    Mono<Student> updateStudent(Student student);

    Mono<Void> deleteStudent(Integer id);
}
