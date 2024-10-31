package curso.java.reactivo.repository;

import curso.java.reactivo.model.Person;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends ReactiveCrudRepository<Person, Long> {
}
