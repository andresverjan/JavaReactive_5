package cedesistemas.edu.co.actividadfinal.repositories;

import cedesistemas.edu.co.actividadfinal.models.PurchaseOrdersDetails;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CrudPurchaseOrderDetailsRepository extends ReactiveCrudRepository<PurchaseOrdersDetails, Integer> {
}
