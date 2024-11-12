package com.lab.academicsystem.repository;

import com.lab.academicsystem.model.Student;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends ReactiveCrudRepository<Student, Long> {
}
