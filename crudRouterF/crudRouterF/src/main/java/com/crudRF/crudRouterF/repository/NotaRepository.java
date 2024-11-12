package com.crudRF.crudRouterF.repository;

import com.crudRF.crudRouterF.model.Estudiante;
import com.crudRF.crudRouterF.model.Nota;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface NotaRepository extends ReactiveCrudRepository<Nota,Long> {
}
