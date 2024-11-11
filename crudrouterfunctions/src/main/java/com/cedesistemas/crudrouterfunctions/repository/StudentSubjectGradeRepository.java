package com.cedesistemas.crudrouterfunctions.repository;

import com.cedesistemas.crudrouterfunctions.model.StudentSubjectGrade;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface StudentSubjectGradeRepository extends ReactiveCrudRepository<StudentSubjectGrade, Integer> {

    @Query("""
                SELECT
                    e.nombre AS estudiante,
                    m.nombre AS materia,
                    n.nota,
                    n.descripcion AS descripcion_nota
                FROM
                    programacionreactiva."STUDENTS" e
                JOIN
                    programacionreactiva."STUDENTS_SUBJECTS" em ON e.id = em.estudiante_id
                JOIN
                    programacionreactiva."SUBJECTS" m ON em.materia_id = m.id
                JOIN
                    programacionreactiva."GRADES" n ON em.id = n.estudiante_materia_id
                WHERE
                    n.nota > 3
                ORDER BY
                    e.nombre, m.nombre, n.descripcion
            """)
    Flux<StudentSubjectGrade> findAllWithGradesAboveThree();

    @Query("""
                SELECT
                    e.nombre AS estudiante,
                    m.nombre AS materia,
                    n.nota,
                    n.descripcion AS descripcion_nota
                FROM
                    programacionreactiva."STUDENTS" e
                JOIN
                    programacionreactiva."STUDENTS_SUBJECTS" em ON e.id = em.estudiante_id
                JOIN
                    programacionreactiva."SUBJECTS" m ON em.materia_id = m.id
                JOIN
                    programacionreactiva."GRADES" n ON em.id = n.estudiante_materia_id
                WHERE
                    n.nota = 3
                ORDER BY
                    e.nombre, m.nombre, n.descripcion
            """)
    Flux<StudentSubjectGrade> findAllWithGradesEqualsThree();
}
