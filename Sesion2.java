import java.util.List;
import java.util.stream.Collectors;

public class Sesion2 {

    //1. Crea una función pura que tome una lista de números y devuelva una nueva lista con el cuadrado de cada número .
    public static List<Integer> squareList(List<Integer> numbers) {
        return numbers.stream()
                .map(number -> number * number) // Calcula el cuadrado de cada número
                .collect(Collectors.toList()); // Recoge los resultados en una nueva lista
    }


    public static void main(String[] args) {
        //2. Crea una lista de números
        List<Integer> numbers = List.of(1, 2, 3, 4, 5);
        //3. Imprime la lista original
        System.out.println("Lista original: " + numbers);
        //4. Calcula el cuadrado de cada número
        List<Integer> squaredNumbers = squareList(numbers);
        //5. Imprime la lista con los cuadrados
        System.out.println("Lista con cuadrados: " + squaredNumbers);
    }
}
