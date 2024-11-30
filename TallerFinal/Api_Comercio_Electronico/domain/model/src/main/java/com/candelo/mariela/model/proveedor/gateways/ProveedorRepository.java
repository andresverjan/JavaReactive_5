package com.candelo.mariela.model.proveedor.gateways;

import com.candelo.mariela.model.proveedor.Proveedor;
import reactor.core.publisher.Mono;

public interface ProveedorRepository {

    Mono<Proveedor> saveProveedor(Proveedor proveedor);

    Mono<Proveedor> updateProveedor(Proveedor proveedor);

    Mono<Void> deleteProveedorByDocumentNumber(int documentNumber);

    Mono<Proveedor> getProveedorByDocumentNumber(int documentNumber);
}
