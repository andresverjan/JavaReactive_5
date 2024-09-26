import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, world!");
        List<Integer> numbers = List.of(1, 2, 3, 4, 5);
        List<Integer> squareList = new Main().forSquareList(numbers);
        List<Integer> ramdomSquareList = new Main().ramdomSquareList(numbers);
        List<Integer> filterOddNumbers = new Main().filterOddNumbers(numbers);
        int factorial = new Main().factorial(5);
        List<Integer> eventNumbers = new Main().filterEvenNumbers(numbers);
        System.out.println("1. cuadrado: "+squareList);
        System.out.println("2. cuadrado aleatorio: "+ramdomSquareList);
        System.out.println("3. impares: "+filterOddNumbers);
        System.out.println("4. factorial: "+factorial);
        System.out.println("5. orden superior: "+eventNumbers);
    }

    public List<Integer> forSquareList(List<Integer> numbers) {
        List<Integer> result = new ArrayList<>();
        for (int number : numbers) {
            result.add(number * number);
        }
        return result;
    }
    public List<Integer> funtionalSquareList(List<Integer> numbers) {
        return numbers.stream()
                .map(number -> number * number)
                .collect(Collectors.toList());
    }
    public List<Integer> ramdomSquareList(List<Integer> numbers) {
        List<Integer> result = new ArrayList<>();
        for (int number : numbers) {
            result.add(number * number);
        }
        Collections.shuffle(result);

        return result;
    }

    public List<Integer> filterOddNumbers(List<Integer> numbers) {
        List<Integer> result = new ArrayList<>();

        for (int num : numbers) {
            if (num % 2 != 0) {
                result.add(num);
            }
        }
        return result;
    }
    public List<Integer> funtionalFilterOddNumbers(List<Integer> numbers) {
        return numbers.stream()
                .filter(number -> number % 2 != 0)
                .collect(Collectors.toList());
    }
    public int factorial(int n) {
        if (n == 0 || n == 1) {
            return 1;
        } else {
            return n * factorial(n - 1);
        }
    }

    public List<Integer> filterEvenNumbers(List<Integer> numbers) {
        return numbers.stream()
                .filter(number -> number % 2 == 0)
                .collect(Collectors.toList());
    }
}
