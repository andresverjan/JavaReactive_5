package org.example.manipulacionFlujosBasico;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Persona {
    private String nombre;
    private String apellido;
    private String telefono;
    private int edad;
    private String signo;

}
