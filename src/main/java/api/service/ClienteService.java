package api.service;

import api.model.Carrito;
import api.model.Cliente;
import api.repository.CarritoRepository;
import api.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final CarritoRepository carritoRepository;

    public ClienteService(ClienteRepository clienteRepository,
                          CarritoRepository carritoRepository)
    {
        this.clienteRepository = clienteRepository;
        this.carritoRepository = carritoRepository;
    }

    public Flux<Cliente> getClientes(){
        return clienteRepository.findAll();
    }
    public Mono<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    public Mono<Cliente> crear(Cliente cliente) {
        return clienteRepository.save(cliente)  // Guardar el cliente primero
                .flatMap(savedCliente -> {
                    Carrito nuevoCarrito = new Carrito(savedCliente.getId());
                    return carritoRepository.save(nuevoCarrito)
                            .flatMap(savedCarrito -> {
                                savedCliente.setCarrito(savedCarrito); // Asociar el carrito al cliente
                                return Mono.just(savedCliente); // Retornar el cliente con su carrito creado
                            });
                });
    }
    public Mono<String> actualizar(Cliente usuario) {
        if (usuario.getId() != null) {
            return clienteRepository.save(usuario)
                    .doOnNext(u -> System.out.println("Usuario actualizado: " + u))
                    .then(Mono.just("Usuario actualizado con éxito"));
        } else {
            return Mono.just("El usuario no tiene un ID válido");
        }
    }

    public Mono<Void> eliminar(Long id) {
        return clienteRepository.deleteById(id);
    }
}
