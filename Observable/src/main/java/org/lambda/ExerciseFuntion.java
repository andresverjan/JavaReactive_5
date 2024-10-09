package org.lambda;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ExerciseFuntion {

    private static List<Integer> externalList = new ArrayList<>();

    public static void main(String[] args) {

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        List<Integer> squaredPure = listSquaredPure(numbers);
        System.out.println("Squared pure: " + squaredPure);

        List<Integer> squaredImpure = listSquaredImpure(numbers);
        System.out.println("Squared impure: " + squaredImpure);
        System.out.println("External list: " + externalList);

        List<Integer> oddNumbers = filterOddNumbers(numbers);
        System.out.println("Odd numbers: " + oddNumbers);

        int num = 5;
        BigInteger fact = factorial(num);
        System.out.println("Factorial of " + num + ": " + fact);

        List<Integer> evenNumbers = filterEvenNumbers(numbers);
        System.out.println("Even numbers: " + evenNumbers);
    }

    public static List<Integer> listSquaredPure(List<Integer> numbers) {
        return numbers.stream()
                .map(number -> number * number)
                .toList();
    }

    public static List<Integer> listSquaredImpure(List<Integer> numbers) {
        List<Integer> result = new ArrayList<>();
        for (Integer number : numbers) {
            int squared = number * number;
            result.add(squared);
            externalList.add(squared);
        }
        return result;
    }

    public static List<Integer> filterOddNumbers(List<Integer> numbers) {
        return numbers.stream()
                .filter(number -> number % 2 != 0)
                .toList();
    }

    private static BigInteger factorial(int n) {
        if (n == 0) {
            return BigInteger.ONE;
        }
        return BigInteger.valueOf(n).multiply(factorial(n - 1));
    }

    public static List<Integer> filterEvenNumbers(List<Integer> numbers) {
        return numbers.stream()
                .filter(number -> number % 2 == 0)
                .toList();
    }
}
