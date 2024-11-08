package curso.java.reactivo.repository;

import curso.java.reactivo.model.Materia;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MateriaRepository extends ReactiveCrudRepository<Materia, Long> {
}
