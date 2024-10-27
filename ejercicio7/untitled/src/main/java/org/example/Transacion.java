package org.example;

import lombok.Setter;

@Setter
public class Transacion {

    private String nombre;


    private double monto;



    public Transacion(String nombre, double monto) {
        this.nombre = nombre;
        this.monto = monto;

    }

 

    public String getNombre() {

        return nombre;

    }

 

    public double getMonto() {

        return monto;

    }

 

    @Override

    public String toString() {

        return "Persona{" +

                "nombre='" + nombre + '\'' +
                ", monto=" + monto +

                '}';

    }

}