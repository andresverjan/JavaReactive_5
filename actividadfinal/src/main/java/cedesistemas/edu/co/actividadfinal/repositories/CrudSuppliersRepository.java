package cedesistemas.edu.co.actividadfinal.repositories;

import cedesistemas.edu.co.actividadfinal.models.Suppliers;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CrudSuppliersRepository extends ReactiveCrudRepository<Suppliers, Integer> {
}
