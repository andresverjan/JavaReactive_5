import java.util.List;
import java.util.stream.Collectors;

public class FilterOddNumbers {
    public static List<Integer> filterOdd(List<Integer> numbers) {
        return numbers.stream()
                      .filter(number -> number % 2 != 0)
                      .map(number -> number)
                      .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        System.out.println(
            filterOdd(
                List.of(1, 2, 3, 4, 5, 6, 7, 8, 9)
            )
        );
    }
}
