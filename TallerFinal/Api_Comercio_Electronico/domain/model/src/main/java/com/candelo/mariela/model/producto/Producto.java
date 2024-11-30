package com.candelo.mariela.model.producto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Producto {

    private UUID id;
    private String nombre;
    private String descripcion;
    private int precioUnitario;
    private int stock;
    private String categoria;
    private String imagenUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
