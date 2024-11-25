package api.repository;


import api.model.Categoria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CategoriaRepository extends ReactiveCrudRepository<Categoria,Long> {
}
