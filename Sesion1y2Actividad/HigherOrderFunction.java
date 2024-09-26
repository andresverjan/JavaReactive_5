import java.util.List;
import java.util.stream.Collectors;

public class HigherOrderFunction {

    public static List<Integer> filterPairs(List<Integer> numbers) {
        return numbers.stream()
                .filter(number -> number % 2 == 0)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> result = filterPairs(numbers);
        System.out.println("Números pares: " + result);
    }
}

