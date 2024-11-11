package com.cedesistemas.crudrouterfunctions.services;

import com.cedesistemas.crudrouterfunctions.exceptions.NotFoundException;
import com.cedesistemas.crudrouterfunctions.interfaces.StudentServiceInterface;
import com.cedesistemas.crudrouterfunctions.model.Student;
import com.cedesistemas.crudrouterfunctions.repository.CrudStudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class StudentService implements StudentServiceInterface {
    private final CrudStudentRepository crudStudentRepository;

    @Override
    public Mono<Student> saveStudent(Student student) {
        return crudStudentRepository.save(student);
    }

    @Override
    public Mono<Student> getStudentById(Integer id) {
        return crudStudentRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Grade not found with id: " + id)));
    }

    @Override
    public Flux<Student> getAllStudents() {
        return crudStudentRepository.findAll()
                .switchIfEmpty(Flux.error(new NotFoundException("No students found")));
    }

    @Override
    public Mono<Student> updateStudent(Student student) {
        return crudStudentRepository.findAll()
                .switchIfEmpty(Flux.error(new NotFoundException("No students found")))
                .then(crudStudentRepository.save(student));
    }

    @Override
    public Mono<Void> deleteStudent(Integer id) {
        return crudStudentRepository.findAll()
                .switchIfEmpty(Flux.error(new NotFoundException("No students found")))
                .then(crudStudentRepository.deleteById(id));
    }
}
