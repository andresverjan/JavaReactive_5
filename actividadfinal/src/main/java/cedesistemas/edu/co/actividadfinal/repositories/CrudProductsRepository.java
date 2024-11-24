package cedesistemas.edu.co.actividadfinal.repositories;

import cedesistemas.edu.co.actividadfinal.models.Products;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CrudProductsRepository extends ReactiveCrudRepository<Products, Integer> {
}
