package com.candelo.mariela.model.cliente;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Cliente {

    private String nombre;
    private String correo;
    private String telefono;
    private String direccion;
    private String documento_type;
    private int documentNumber;
}
