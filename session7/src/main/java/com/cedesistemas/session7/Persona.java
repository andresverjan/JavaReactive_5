package com.cedesistemas.session7;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Persona {
    private String nombre;

    private String ciudad;

    private String[] direcciones; // Array de direcciones

    private double saldo;

    private String estadoCuenta;

    @Override

    public String toString() {

        return "Persona{" +

                "nombre='" + nombre + '\'' +

                ", ciudad='" + ciudad + '\'' +

                ", saldo=" + saldo + '\'' +

                ", estadoCuenta=" + estadoCuenta + '\'' +

                '}';

    }
}
