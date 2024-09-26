import java.util.ArrayList;
import java.util.List;

public class FunctionPureListOdd {

    public static List<Integer> filterOddNumbers(List<Integer> numbers) {
        List<Integer> oddNumbers = new ArrayList<>();

        for (Integer number : numbers) {
            if (number % 2 != 0) {
                oddNumbers.add(number);
            }
        }

        return oddNumbers;
    }

    public static void main(String[] args) {
        List<Integer> numbers = List.of(13, 8, 105, 15, 12);
        List<Integer> result = filterOddNumbers(numbers);
        System.out.println("Lista numeros: " + numbers);
        System.out.println("Lista numeros impares: " + result);
    }
}
