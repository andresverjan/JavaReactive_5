package org.example;

import java.sql.SQLOutput;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    private static int contador = 0;

    public static List<Integer> numCuadrados(List<Integer> num) {
        return num.stream()
                .map(numero -> numero * numero)
                .collect(Collectors.toList());
    }

    public static List<Integer> calCuadrados(List<Integer> num) {
        contador++;
        return num.stream()
                .map(numero -> numero * numero)
                .collect(Collectors.toList());
    }

    public static List<Integer> filtrarNun(List<Integer> num) {
        return num.stream()
                .filter(numero -> numero % 2 != 0)
                .map(numero -> numero * 2 )
                .collect(Collectors.toList());
    }

    public static int recursionFactorial(int n){
        // calcular el factorial de un número ejemplo n! = n * (n-1)!
        if (n < 0) {
            throw new IllegalArgumentException("El factorial no existe para numeros negativos.");
        }
        if (n == 0 || n == 1){
            return 1;
        } else {
            return n * recursionFactorial(n -1);
        }
    }

    public static List<Integer> funcionOrdenSuperior(List<Integer> integerList, Predicate<Integer> predicado){
        return integerList.stream()
                .filter(predicado)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        List<Integer> numeros = List.of(1,3,5,7,9, 10, 11, 12, 13);
        List<Integer> resultCuadrados = numCuadrados(numeros);
        List<Integer> calCuadrados = calCuadrados(numeros);
        List<Integer> filtrarImpares = filtrarNun(numeros);

        //1. función pura que tome una lista de números y devuelva una nueva lista con el cuadrado de cada número
        System.out.println("\nFuncion pura: Lista de numeros cuadrados" + resultCuadrados);

        //.2 funcion que no sea Pura que tome una lista de números y devuelva una nueva lista con el cuadrado de cada número
        System.out.println("\nFuncion NO PURA: Lista de numeros cuadrados" + calCuadrados + " contador= " + contador);

        //3. Función pura que filtre los números impares de una lista.
        System.out.println("\nfunción pura que filtre los números impares " + filtrarImpares);

        //4. función recursiva que calcule el factorial de un número entero positive
            // si n = 0 retorna 1 si n es un numero negativo retorna una excepcion
        System.out.println("\nfactorial numero positivo " + recursionFactorial(5));

        //5. funcion que se encarga de verificar los numeros pares de una lista, usando funciones de orden superior.
        System.out.println("\nNumeros pares: " + funcionOrdenSuperior(
                numeros,
                x -> x % 2 == 0
                ));

    }
}