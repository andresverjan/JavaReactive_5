package co.com.bancolombia;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

/*****     Ejercicio 1 - Sesion 2 *****/
        System.out.println("-------------------------------------------------");
//        1. Crea una función pura que tome una lista de números y devuelva una nueva lista con el cuadrado de cada número.
        Main.pureFunctionSquareList(List.of(1, 2, 3, 4, 5)).forEach(System.out::println);
        System.out.println("-------------------------------------------------");

//        2. Crear una funcion que no sea Pura que tome una lista de números y devuelva una nueva lista con el cuadrado de cada número.
        Main.impureFunctionSquareList(List.of(1, 2, 3, 4, 5)).forEach(System.out::println);
        System.out.println("-------------------------------------------------");

//        3. Crear una función pura que filtre los números impares de una lista.
        Main.pureFunctionOddNumbers(List.of(1, 2, 3, 4, 5)).forEach(System.out::println);
        System.out.println("-------------------------------------------------");

//        4. Crea una función recursiva que calcule el factorial de un número entero positivo.
        System.out.println(Main.factorial(5));
        System.out.println("-------------------------------------------------");

//        5. Crear una funcion uqe se encargue de verificar los numeros pares de una lista, usando funciones de orden superior.
        Main.evenNumbers(List.of(1, 2, 3, 4, 5)).forEach(System.out::println);
        System.out.println("-------------------------------------------------");

    }

    public static List<Integer> pureFunctionSquareList(List<Integer> numbers) {
        return numbers.stream()
                .map(number -> number * number) // Calcula el cuadrado de cada número
                .collect(Collectors.toList()); // Recoge los resultados en una nueva lista
    }

    static int value = 4; //Variable externa

    public static List<Integer> impureFunctionSquareList(List<Integer> numbers) {
        return numbers.stream()
                .map(number -> value + number) // Suma el valor de la variable externa al numero
                .map(number -> number * number)// Calcula el cuadrado de cada número luego de sumarle el valor de la variable externa
                .collect(Collectors.toList()); // Recoge los resultados en una nueva lista
    }

    public static List<Integer> pureFunctionOddNumbers(List<Integer> numbers) {
        return numbers.stream()
                .filter(number -> number % 2 != 0) // Filtra los números impares
                .collect(Collectors.toList()); // Recoge los resultados en una nueva lista
    }

    public static int factorial(int n) {
        if (n == 0) {
            return 1;
        } else {
            return n * factorial(n - 1);
        }
    }

    public static List<Integer> evenNumbers(List<Integer> numbers) {
        Predicate<Integer> isEven = number -> number % 2 == 0; // Función lambda para verificar si un número es par
        return numbers.stream()
                .filter(isEven) // Filtra los números pares, aqui se toma la funcion lambda para aplicar el filtro
                .collect(Collectors.toList()); // Recoge los resultados en una nueva lista
    }
}