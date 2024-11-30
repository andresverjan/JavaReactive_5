package com.candelo.mariela.usecase.proveedor;

import com.candelo.mariela.model.proveedor.Proveedor;
import com.candelo.mariela.model.proveedor.gateways.ProveedorGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ProveedorUseCase {

    private final ProveedorGateway proveedorGateway;

    public Mono<Proveedor> saveProveedor(Proveedor proveedor) {
        return proveedorGateway.saveProveedor(proveedor);
    }

    public Mono<Proveedor> updateProveedor(Proveedor proveedor) {
        return proveedorGateway.updateProveedor(proveedor);
    }

    public Mono<Void> deleteProveedorByDocumentNumber(int documentNumber) {
        return proveedorGateway.deleteProveedorByDocumentNumber(documentNumber);
    }

    public Mono<Proveedor> getProveedorByDocumentNumber(int documentNumber) {
        return proveedorGateway.getProveedorByDocumentNumber(documentNumber);
    }
}
