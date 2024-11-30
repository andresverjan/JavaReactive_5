package com.candelo.mariela.r2dbc.proveedor.adapters;

import com.candelo.mariela.model.proveedor.Proveedor;
import com.candelo.mariela.model.proveedor.gateways.ProveedorGateway;
import com.candelo.mariela.r2dbc.proveedor.entities.ProveedorEntity;
import com.candelo.mariela.r2dbc.proveedor.mappers.ProveedorMapper;
import com.candelo.mariela.r2dbc.proveedor.repositories.ProveedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class ProveedorAdapter implements ProveedorGateway {

    private final ProveedorRepository proveedorRepository;

    @Override
    public Mono<Proveedor> saveProveedor(Proveedor proveedor) {
        ProveedorEntity entity = ProveedorMapper.toProveedorEntity(proveedor);
        return proveedorRepository.saveProveedor(entity.getDocumentNumber(),
                entity.getNombre(),
                entity.getCorreo(),
                entity.getTelefono(),
                entity.getDireccion(),
                entity.getDocumento_type())
                .map(ProveedorMapper::toProveedor)
                .onErrorResume(error -> Mono.error(new RuntimeException("Error al guardar el proveedor" + error.getMessage())));
    }

    @Override
    public Mono<Proveedor> updateProveedor(Proveedor proveedor) {
        ProveedorEntity entity = ProveedorMapper.toProveedorEntity(proveedor);
        return proveedorRepository.save(entity)
                .map(ProveedorMapper::toProveedor)
                .onErrorResume(error -> Mono.error(new RuntimeException("Error al actualizar el proveedor" + error.getMessage())));
    }

    @Override
    public Mono<Void> deleteProveedorByDocumentNumber(int documentNumber) {
        return proveedorRepository.deleteById(documentNumber)
                .onErrorResume(error -> Mono.error(new RuntimeException("Error al eliminar el proveedor" + error.getMessage())));
    }

    @Override
    public Mono<Proveedor> getProveedorByDocumentNumber(int documentNumber) {
        return proveedorRepository.findById(documentNumber)
                .map(ProveedorMapper::toProveedor)
                .onErrorResume(error -> Mono.error(new RuntimeException("Error al obtener el proveedor" + error.getMessage())));
    }
}
