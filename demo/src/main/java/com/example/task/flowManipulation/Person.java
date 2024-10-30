package com.example.task.flowManipulation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    private String name;
    private String city;
    private String[] adresses; // Array de direcciones
    private double balance;

    @Override

    public String toString() {

        return "Persona{" +
                "nombre='" + name + '\'' +
                ", ciudad='" + city + '\'' +
                ", saldo=" + balance +
                '}';
    }
}
