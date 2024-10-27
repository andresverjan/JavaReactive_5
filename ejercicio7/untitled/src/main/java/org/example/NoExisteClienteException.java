package org.example;

public class NoExisteClienteException extends Exception {
    String nombre;

    public NoExisteClienteException(String nombre) {
        super("El cliente no existe");
        this.nombre = nombre;
    }

    public String getPersona() {
        return nombre;
    }
}
