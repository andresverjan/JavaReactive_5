import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App {

    static StringBuilder message = new StringBuilder();

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        System.out.println("Number" + numbers);
        square(numbers);
        odd(numbers);
        squareNot(numbers);
        int random = (int) (Math.random() * 10);
        System.out.println("Factorial of: " + random +" " +"= " + factorial(random));

        List<Integer> evenNumbers = Arrays.asList(1, 2, 3, 4, 5).stream().filter(n -> n % 2 == 0).toList();
        System.out.println("Even numbers: " + evenNumbers);
    }

    public static void square(List<Integer> numbers) {
        List<Integer> square = new ArrayList<>();
        for (var number : numbers) {
            square.add(number * number);
        }
        System.out.println("List Square: " + square);
    }

    public static void squareNot(List<Integer> numbers) {
        int random = (int) (Math.random() * 10);
        message.append("Hello Square: " + random);
        List<Integer> square = new ArrayList<>();
        for (var number : numbers) {
            square.add(number * number);
        }
        System.out.println("List Square: " + square + " " + message);
    }

    public static void odd(List<Integer> numbers){
        List<Integer> odd = new ArrayList<>();
        for (var number : numbers) {
            if(number % 2 != 0){
                odd.add(number);
            }
        }
        System.out.println("List of odd numbers: " + odd);
    }

    public static int factorial(int number) {
        if (number >= 1)
            return number * factorial(number - 1);
        else
            return 1;
    }
}
