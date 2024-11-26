package cedesistemas.edu.co.actividadfinal.repositories;

import cedesistemas.edu.co.actividadfinal.models.SalesOrdersDetails;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CrudSaleOrderDetailsRepository extends ReactiveCrudRepository<SalesOrdersDetails, Integer> {
}
