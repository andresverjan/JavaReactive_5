import java.util.ArrayList;
import java.util.List;

public class FunctionPureListNumbers {
    public static List<Integer> calculateSquares(List<Integer> numbers) {
        List<Integer> squares = new ArrayList<>();

        for (Integer number : numbers) {
            squares.add(number * number);
        }

        return squares;
    }

    public static void main(String[] args) {
        List<Integer> numbers = List.of(4, 8, 20, 15, 12);
        List<Integer> result = calculateSquares(numbers);
        System.out.println("Lista numeros: " + numbers);
        System.out.println("Lista numeros al cuadrado: " + result);
    }
}