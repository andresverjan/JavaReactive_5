package org.example;

public class Persona {
    private String nombre;
    private String ciudad;
    private String[] direcciones; // Array de direcciones
    private double saldo;


    public Persona(String nombre, String ciudad, String[] direcciones, double saldo) {

        this.nombre = nombre;
        this.ciudad = ciudad;
        this.direcciones = direcciones;
        this.saldo = saldo;

    }


    public String getNombre() {
        return nombre;
    }

    public String getCiudad() {
        return ciudad;
    }


    public String[] getDirecciones() {
        return direcciones;
    }


    public double getSaldo() {
        return saldo;
    }


    @Override

    public String toString() {
        return "Persona{" +
                "nombre='" + nombre + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", saldo=" + saldo +
                '}';
    }
}
