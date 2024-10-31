package com.artifactory.FlowCombination;

public class Persona {
    private String nombre;

    private String ciudad;

    private String[] direcciones; // Array de direcciones

    private double saldo;

    private String estado;

    public Persona() {

    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Persona(String nombre, String ciudad, String[] direcciones, double saldo, String estado) {

        this.nombre = nombre;

        this.ciudad = ciudad;

        this.direcciones = direcciones;

        this.saldo = saldo;

        this.estado = estado;

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

    public void setSaldo(double saldo) {
        this.saldo = saldo;
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
