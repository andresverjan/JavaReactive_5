package org.example.repository;

import org.example.model.Student;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface StudentRepository extends ReactiveCrudRepository<Student, Long> {
    Flux<Student> findAllByPromedioGreaterThan(Double promedio);

    Flux<Student> findAllByPromedioLessThanEqual(Double promedio);

}
