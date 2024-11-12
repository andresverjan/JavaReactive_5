package org.example.activitySix.repository;

import org.example.activitySix.model.Estudiante;
import org.example.activitySix.model.EstudianteConMateriasYNotas;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface EstudianteRepository extends ReactiveCrudRepository<Estudiante, Long> {
    @Query("""
            SELECT e.id AS estudiante_id, e.nombre AS estudiante_nombre, e.edad, 
                   m.id AS materia_id, m.nombre AS materia_nombre,
                   m.nota1, m.nota2, m.nota3
            FROM estudiante e
            LEFT JOIN materia m ON e.id = m.estudiante_id
            WHERE e.id = :estudianteId
            """)
    Flux<EstudianteConMateriasYNotas> findEstudianteConMateriasYNotas(Long estudianteId);
}


