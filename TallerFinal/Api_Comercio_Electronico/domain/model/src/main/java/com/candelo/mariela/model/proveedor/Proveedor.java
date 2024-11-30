package com.candelo.mariela.model.proveedor;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Proveedor {

    private String nombre;
    private String correo;
    private String telefono;
    private String direccion;
    private String documento_type;
    private int documentNumber;
}
