package com.crud_basico.crud_basico.repository;

import com.crud_basico.crud_basico.model.Estudiante;
import com.crud_basico.crud_basico.model.Materia;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface MateriaRepository extends ReactiveCrudRepository<Materia, Long> {

    Flux<Materia> findByEstudianteId(Long estudianteId);
}
