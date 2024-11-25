package api.repository;


import api.model.Cliente;
import api.model.Proveedor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ClienteRepository extends ReactiveCrudRepository<Cliente,Long> {
    
}
