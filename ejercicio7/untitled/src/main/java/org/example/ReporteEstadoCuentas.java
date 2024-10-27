package org.example;

import lombok.Setter;

@Setter
public class ReporteEstadoCuentas {

    private String nombre;

    private String direccion; // Array de direcciones

    private String estadoCuenta;



    public ReporteEstadoCuentas(String direccion, String nombre, String estadoCuenta) {

        this.nombre = nombre;


        this.direccion = direccion;

        this.estadoCuenta = estadoCuenta;

    }

 

    public String getNombre() {

        return nombre;

    }

 

    public String getDireccion() {

        return direccion;

    }

 

    public String getEstadoCuenta() {

        return estadoCuenta;

    }

 

 

    @Override

    public String toString() {

        return "EstadoCuenta{" +

                ", Nombre='" + nombre + '\'' +

                ", Estado=" + estadoCuenta +

                '}';

    }

}