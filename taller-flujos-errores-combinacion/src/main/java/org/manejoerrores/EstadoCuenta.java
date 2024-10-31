package org.manejoerrores;

public class EstadoCuenta {
    private String direccion;
    private String estado;

    public EstadoCuenta(String direccion, String estado) {
        this.direccion = direccion;
        this.estado = estado;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return "EstadoCuenta{" +
                "direccion='" + direccion + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}