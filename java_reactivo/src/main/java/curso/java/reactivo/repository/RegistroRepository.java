package curso.java.reactivo.repository;

import curso.java.reactivo.model.Registro;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface RegistroRepository extends ReactiveCrudRepository<Registro, Long> {
    Flux<Registro> findByEstudianteId(Long estudianteId);
}
