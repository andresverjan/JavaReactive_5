package com.lab.academicsystem.repository;

import com.lab.academicsystem.model.Subject;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRespository extends ReactiveCrudRepository<Subject, Long> {
}
