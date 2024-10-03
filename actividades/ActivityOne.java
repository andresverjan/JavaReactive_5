package actividades;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ActivityOne {

    public static void main(String[] args) {

        System.out.print("Punto 1 \n");
        System.out.print("Lista Numeros Cuadrados Funcion Pura \n");
        var numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        var squared = squareListPureFunction(numbers);
        System.out.println("Lista original funcion pura: " + numbers);
        System.out.println("Lista de cuadrados funcion pura: " + squared);
        System.out.print("\n");


        System.out.print("Punto 2 \n");
        System.out.print("Lista Numeros Cuadrados Funcion no Pura \n");
        var numbers1 = new ArrayList<>(List.of(1, 2, 3, 4, 5));
        squareListNoPureFunction(numbers1);
        System.out.println("Suma de los cuadrados: " + sumOfSquares);
        squareListNoPureFunction(numbers1);
        System.out.println("Suma de los cuadrados : " + sumOfSquares);
        System.out.print("\n");


        System.out.print("Punto 3 \n");
        System.out.print("Lista Numeros impares Funcion Pura\n");
        var oddNumbers = filterOddNumbers(numbers);
        System.out.println("Números impares: " + oddNumbers);
        System.out.print("\n");


        System.out.print("Punto 4\n");
        System.out.print("Factorial funcion recursiva\n");
        var number = 5;
        var result = factorial(number);
        System.out.println("El factorial de " + number + " es: " + result);
        System.out.print("\n");

        System.out.print("Punto 5\n");
        System.out.print("Funcionl orden superior para verificar numeros pares\n");
        var evenNumbers = filterEvenNumbers(numbers);
        System.out.println("Números pares: " + evenNumbers);


    }

    public static List<Integer> squareListPureFunction(List<Integer> numbers) {
        List<Integer> squaredList = new ArrayList<>();
        for (Integer num : numbers) {
            squaredList.add(num * num);
        }
        return squaredList;
    }

    public static int sumOfSquares = 0;

    public static void squareListNoPureFunction(List<Integer> numbers) {
        for (int i = 0; i < numbers.size(); i++) {
            int squared = numbers.get(i) * numbers.get(i);
            numbers.set(i, squared);
            sumOfSquares += squared;
        }
        System.out.printf("Lista de cuadrados: %s%n", numbers);
    }

    public static List<Integer> filterOddNumbers(List<Integer> numbers) {
        List<Integer> oddNumbers = new ArrayList<>();
        for (Integer number : numbers) {
            if (number % 2 != 0) {
                oddNumbers.add(number);
            }
        }
        return oddNumbers;
    }


    public static int factorial(int n) {
        return n <= 0 ? 1 : n * factorial(n - 1);
    }

    public static List<Integer> filterEvenNumbers(List<Integer> numbers) {
        return numbers.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());
    }

}
