package com.crud_basico.crud_basico.repository;

import com.crud_basico.crud_basico.model.Nota;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface NotaRepository extends ReactiveCrudRepository<Nota, Long> {

    Flux<Nota> findNotasByMateriaId(Long materiaId);
}
