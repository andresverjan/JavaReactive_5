package curso.java.reactivo.repository;

import curso.java.reactivo.model.Estudiante;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstudianteRepository extends ReactiveCrudRepository<Estudiante, Long> {
}
