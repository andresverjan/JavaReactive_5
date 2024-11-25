package api.service;

import api.model.Cliente;
import api.model.Proveedor;
import api.repository.ProveedorRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProveedorService {
    private final ProveedorRepository proveedorRepository;

    public ProveedorService(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    public Flux<Proveedor> getProveedores(){
        return proveedorRepository.findAll();
    }
    public Mono<Proveedor> buscarPorId(Long id) {
        return proveedorRepository.findById(id);
    }

    public Mono<Proveedor> crear(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    public Mono<String> actualizar(Proveedor proveedor) {
        if (proveedor.getId() != null) {
            return proveedorRepository.save(proveedor)
                    .doOnNext(p -> System.out.println("Proveedor actualizado: " + p))
                    .then(Mono.just("Proveedor actualizado con éxito"));
        } else {
            return Mono.just("El proveedor no tiene un ID válido");
        }
    }

    public Mono<Void> eliminar(Long id) {
        return proveedorRepository.deleteById(id);
    }
}
