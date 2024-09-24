import java.util.List;

public class PureFunctionExample {

    public static void main(String[] args) {
        var numbers = List.of(1, 2, 3, 4, 5);
        var total = sum(numbers);
        System.out.println("Sum: " + total);

    }

    public static int sum(List<Integer> numbers) {
        var total = 0;
        for (var number : numbers) {
            total += number;
        }
        return total;
    }
}
