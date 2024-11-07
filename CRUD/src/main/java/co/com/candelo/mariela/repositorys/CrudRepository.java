package co.com.candelo.mariela.repositorys;

import co.com.candelo.mariela.model.Person;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CrudRepository extends ReactiveCrudRepository<Person, Integer> {
}
