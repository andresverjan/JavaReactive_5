package org.example.activityFour;

public class Person {

    private String name;

    private String city;

    private String[] directions; // Array de direcciones

    private double balance;


    public Person(String name, String city, String[] directions, double balance) {

        this.name = name;

        this.city = city;

        this.directions = directions;

        this.balance = balance;

    }


    public String getName() {

        return name;

    }


    public String getCity() {

        return city;

    }


    public String[] getDirections() {

        return directions;

    }


    public double getBalance() {

        return balance;

    }


    @Override

    public String toString() {

        return "Persona{" +

                "nombre='" + name + '\'' +

                ", ciudad='" + city + '\'' +

                ", saldo=" + balance +

                '}';

    }

}

