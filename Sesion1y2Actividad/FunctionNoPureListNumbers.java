import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FunctionNoPureListNumbers {

    public static List<Integer> modificarConCuadradosAleatorios(List<Integer> numbers) {
        List<Integer> result = new ArrayList<>();
        Random random = new Random();

        for (Integer number : numbers) {
            int cuadrado = number * number;
            int aleatorio = random.nextInt(10);
            result.add(cuadrado + aleatorio);
        }

        return result;
    }

    public static void main(String[] args) {
        List<Integer> number = List.of(1, 2, 3, 4, 5);
        List<Integer> result = modificarConCuadradosAleatorios(number);

        System.out.println("Lista con cuadrados más número aleatorio: " + result);
    }
}
