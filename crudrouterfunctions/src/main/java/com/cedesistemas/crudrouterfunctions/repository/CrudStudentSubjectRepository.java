package com.cedesistemas.crudrouterfunctions.repository;

import com.cedesistemas.crudrouterfunctions.model.StudentSubject;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CrudStudentSubjectRepository extends ReactiveCrudRepository<StudentSubject, Integer> {
}
