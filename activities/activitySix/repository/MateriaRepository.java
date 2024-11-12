package org.example.activitySix.repository;

import org.example.activitySix.model.Materia;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface MateriaRepository extends ReactiveCrudRepository<Materia, Long> {
    Flux<Materia> findByEstudianteId(Long estudianteId);
}
    // Métodos específicos de Materia si es necesario

