package org.manejoerrores;

public class Transaccion {
    private String nombre;
    private double monto;

    public Transaccion(String nombre, double monto) {
        this.nombre = nombre;
        this.monto = monto;
    }

    public String getNombre() {
        return nombre;
    }

    public double getMonto() {
        return monto;
    }
}
