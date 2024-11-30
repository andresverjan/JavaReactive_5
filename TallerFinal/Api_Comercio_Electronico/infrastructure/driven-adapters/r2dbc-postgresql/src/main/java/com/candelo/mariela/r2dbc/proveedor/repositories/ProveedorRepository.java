package com.candelo.mariela.r2dbc.proveedor.repositories;

import com.candelo.mariela.r2dbc.proveedor.entities.ProveedorEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ProveedorRepository  extends ReactiveCrudRepository<ProveedorEntity, Integer> {

    @Query("""
            insert into proveedor (id,nombre, correo, telefono, direccion, documento_type)
            values (:id, :nombre, :correo, :telefono, :direccion, :documento_type)        
    """)
    Mono<ProveedorEntity> saveProveedor(@Param("id") Integer id,
                                        @Param("nombre") String nombre,
                                        @Param("correo") String correo,
                                        @Param("telefono") String telefono,
                                        @Param("direccion") String direccion,
                                        @Param("documento_type") String documento_type);
}
