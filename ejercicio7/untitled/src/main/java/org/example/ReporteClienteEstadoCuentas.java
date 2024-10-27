package org.example;

import lombok.Setter;

@Setter
public class ReporteClienteEstadoCuentas {

    private String nombre;

    private String estadoCuenta;



    public ReporteClienteEstadoCuentas(String nombre, String estadoCuenta) {

        this.nombre = nombre;

        this.estadoCuenta = estadoCuenta;

    }

 

    public String getNombre() {

        return nombre;

    }


    public String getEstadoCuenta() {

        return estadoCuenta;

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ReporteClienteEstadoCuentas that = (ReporteClienteEstadoCuentas) obj;
        return nombre.equals(that.nombre) && estadoCuenta.equals(that.estadoCuenta);
    }

    @Override

    public String toString() {

        return "EstadoCuenta{" +

                ", Nombre='" + nombre + '\'' +

                ", Estado=" + estadoCuenta +

                '}';

    }

}