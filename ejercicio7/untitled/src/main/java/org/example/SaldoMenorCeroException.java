package org.example;

public class SaldoMenorCeroException extends RuntimeException{
    Persona persona;
    public SaldoMenorCeroException(Persona persona) {
        super("Saldo menor a Cero");
        this.persona = persona;
    }

    public Persona getPersona() {
        return persona;
    }
}

