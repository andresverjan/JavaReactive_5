package com.lab;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Lab1 {
    /* 1.Crea una función pura que tome una lista de números y devuelva una
   nueva lista con el cuadrado de cada número */
    public static List<Integer> pureSquare(List<Integer> numbers) {
        List<Integer> square = new ArrayList<>();
        for (int number : numbers) {
            square.add(number*number);
        }return square;
    }

    /*2. Crear una funcion que no sea Pura que tome una lista de
    números y devuelva una nueva lista con el cuadrado de cada número. */
    static int counter=0;
    static List<Integer> impureSquare;

    public static List<Integer> impureSquare(List<Integer> numbers) {
        impureSquare = new ArrayList<>();
        for (int number : numbers) {
            counter ++;
            impureSquare.add(number*number+counter);
        }return impureSquare;
    }

    /*3. Crear una función pura que filtre los números impares de una lista.*/
    public static List<Integer> oddNumber(List<Integer> numbers) {
        return numbers.stream()
                .filter(number -> number % 2 != 0)
                .collect(Collectors.toList());
    }

    /*4. Crea una función recursiva que calcule el factorial de un número entero positivo.
    * factorial de un número ejemplo n! = n * (n-1)!
    * 5!=5x4x3x2x1=120 */
    public static int factorial(int number) {
        if (number == 0 || number == 1) {
            return 1;
        }else {
            return number * factorial(number - 1);
        }
    }

    /*5. Crear una funcion que se encargue de verificar los numeros pares de una lista,
    usando funciones de orden superior.
     - Almacenadas en variables
     - Pasadas como argumentos
     - Retornadas funciones */

    //funcion para saber si el numero es par
    static Predicate<Integer> filterEvenNumber = (num) -> num % 2 == 0;

    /*Programación iterativa
    public static List<Integer> filter(List<Integer> numbers, Predicate<Integer> filterEvenNumber){
        List<Integer> evenNumbers = new ArrayList<>();
        for (int num : numbers) {
            if(filterEvenNumber.test(num)){
                evenNumbers.add(num);
            }
        }
        return evenNumbers;
    }*/

    //opcion funcional
    public static List<Integer> filter(List<Integer> numbers, Predicate<Integer> filterEvenNumber) {
    return numbers.stream()
                  .filter(filterEvenNumber)
                  .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        List<Integer> numberList = List.of(1, 2, 3, 4, 5);

        System.out.println("Función Pura cuadrado: " +pureSquare(numberList));

        System.out.println("Función impura cuadrado: " +impureSquare(numberList));

        System.out.println("Función Pura númerosPares: " +oddNumber(numberList));

        System.out.print("Función Recursiva: ");
        for(int number : numberList) {
            System.out.print(factorial(number)+" ");
        }
        /*al  llamar el metodo filter, a su vez ejecuto la funcion filterEvenNumber, que es un parametro de entrada de
        la funcion filter */
        System.out.println("\nFunción de orden superior: " + filter(numberList,filterEvenNumber));
    }
}
