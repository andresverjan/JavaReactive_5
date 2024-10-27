package org.example;

import lombok.Setter;

@Setter
public class ReporteClienteEstadoSaldo {

    private String nombre;

    private double saldo;



    public ReporteClienteEstadoSaldo(String nombre, double saldo) {

        this.nombre = nombre;

        this.saldo = saldo;

    }

 

    public String getNombre() {

        return nombre;

    }


    public double getSaldo() {

        return saldo;

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ReporteClienteEstadoSaldo that = (ReporteClienteEstadoSaldo) obj;
        return nombre.equals(that.nombre) && saldo == saldo;
    }

    @Override

    public String toString() {

        return "EstadoCuenta{" +

                ", Nombre='" + nombre + '\'' +

                ", Saldo=" + saldo +

                '}';

    }

}