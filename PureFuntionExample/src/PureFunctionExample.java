import java.util.List;

public class PureFunctionExample {

    public static int sum(List<Integer> numbers) {
        int total = 0;
        for (int number : numbers) {
            total += number;
        }
        return total;
    }

    public static void main(String[] args) {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5);
        int result = sum(numbers);
        System.out.println("Sum: " + result);
    }
}