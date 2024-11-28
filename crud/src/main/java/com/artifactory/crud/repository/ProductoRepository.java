package com.artifactory.crud.repository;

import com.artifactory.crud.model.Cliente;
import com.artifactory.crud.model.Compra;
import com.artifactory.crud.model.Producto;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductoRepository extends ReactiveCrudRepository<Producto, Long> {

    @Query(" select * from producto  where idproducto = :idproduct and categoria = :categoria")
    Flux<Producto> findByCategoriaById(Long idproduct, String categoria );
}
