package com.cedesistemas.crudrouterfunctions.repository;

import com.cedesistemas.crudrouterfunctions.model.Student;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CrudStudentRepository extends ReactiveCrudRepository<Student, Integer> {
}
