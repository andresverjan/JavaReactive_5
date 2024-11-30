package com.candelo.mariela.r2dbc.cliente.repositories;

import com.candelo.mariela.r2dbc.cliente.entities.ClienteEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ClienteRepository extends ReactiveCrudRepository<ClienteEntity, Integer> {

    @Query("""
            insert into cliente (id,nombre, correo, telefono, direccion, documento_type)
            values (:id, :nombre, :correo, :telefono, :direccion, :documento_type)        
    """)
    Mono<ClienteEntity> saveCliente(@Param("id") Integer id,
                                    @Param("nombre") String nombre,
                                    @Param("correo") String correo,
                                    @Param("telefono") String telefono,
                                    @Param("direccion") String direccion,
                                    @Param("documento_type") String documento_type);
}
