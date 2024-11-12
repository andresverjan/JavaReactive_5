package com.lab.academicsystem.repository;

import com.lab.academicsystem.model.Grade;
import com.lab.academicsystem.model.GradeSummary;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface GradeRepository extends ReactiveCrudRepository<Grade, Long> {

    @Query("SELECT st.name AS student_name, sub.name AS subject_name, g.value AS grade_value " +
            "FROM grade g " +
            "INNER JOIN student st ON st.id = g.id_student " +
            "INNER JOIN subject sub ON sub.id = g.id_subject " +
            "WHERE g.value > 3")
    Flux<GradeSummary> findStudentsAproved();

    @Query("SELECT st.name AS student_name, sub.name AS subject_name, g.value AS grade_value " +
            "FROM grade g " +
            "INNER JOIN student st ON st.id = g.id_student " +
            "INNER JOIN subject sub ON sub.id = g.id_subject " +
            "WHERE g.value <= 3")
    Flux<GradeSummary> findStudentsNotAproved();
}
