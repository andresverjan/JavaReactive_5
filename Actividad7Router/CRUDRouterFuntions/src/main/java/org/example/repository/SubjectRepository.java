package org.example.repository;

import org.example.model.Subject;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends ReactiveCrudRepository<Subject, Long> {

}
