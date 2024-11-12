package com.crud_basico.crud_basico.repository;

import com.crud_basico.crud_basico.model.Estudiante;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstudianteRepository extends ReactiveCrudRepository<Estudiante, Long> {
}
