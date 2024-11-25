package api.repository;


import api.model.Producto;
import api.model.Proveedor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ProveedorRepository extends ReactiveCrudRepository<Proveedor,Long> {
    
}
