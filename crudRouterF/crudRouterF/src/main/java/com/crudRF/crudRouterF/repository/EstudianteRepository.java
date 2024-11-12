package com.crudRF.crudRouterF.repository;

import com.crudRF.crudRouterF.model.Estudiante;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface EstudianteRepository extends ReactiveCrudRepository<Estudiante,Long> {
}
