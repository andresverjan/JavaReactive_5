package com.cedesistemas.sesion6;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Persona {
    private String nombre;

    private String apellido;

    private String telefono;

    private int edad;

    private String signo;
}
