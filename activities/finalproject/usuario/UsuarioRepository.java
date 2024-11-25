package org.example.finalproject.usuario;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UsuarioRepository extends ReactiveCrudRepository<Usuario, Integer> {

    Mono<Usuario> findByNombre(String nombre);

}
