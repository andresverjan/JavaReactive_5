package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    int arraySize = 10; // Tamaño del arreglo
    List<Integer> randomArray; // Lista de números
    Random random = new Random();

    //1. Crea una función pura que tome una lista de números y devuelva una nueva lista con el cuadrado de cada número.
    public List<Integer> squareList(List<Integer> numbers) {
        return numbers.stream()
                .map(number -> number * number)
                .collect(Collectors.toList());
    }

    //2. Crear una funcion que no sea Pura que tome una lista de números y devuelva una nueva lista con el cuadrado de cada número.
    public List<Integer> squareListNotPure() {
        //Generamos una lista random
        for (int i = 0; i < arraySize; i++) {
            randomArray.add(random.nextInt(100));
        }

        randomArray.replaceAll(number -> number * number);
        return randomArray;
    }

    //3. Crear una función pura que filtre los números impares de una lista.
    public List<Integer> filterOddNumbers(List<Integer> numbers) {
        return numbers.stream()
                .filter(number -> number % 2 != 0)
                .collect(Collectors.toList());
    }

    //4. Crea una función recursiva que calcule el factorial de un número entero positivo.
    public int factorial(int number) {
        if (number == 0) {
            return 1;
        } else {
            return number * factorial(number - 1);
        }
    }

    //5. Crear una funcion uqe se encargue de verificar los numeros pares de una lista, usando funciones de orden superior.
    public List<Integer> filterEvenNumbers(List<Integer> numbers) {
        return numbers.stream()
                .filter(number -> number % 2 == 0)
                .collect(Collectors.toList());
    }

}
