import java.util.ArrayList;
import java.util.List;
//1. Crea una función pura que tome una lista de números y
// devuelva una nueva lista con el cuadrado de cada número .
public class Cuadrados {

    List<Integer> cuadrado(List<Integer> lista){
        List<Integer> cuadrados = new ArrayList<>();
        for (int num : lista) {
            cuadrados.add(num * num);
        }
        return cuadrados;
    }
    public static void main(String[] args) {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5);
        Cuadrados main = new Cuadrados();
        List<Integer> result = main.cuadrado(numbers);
        System.out.println("Cuadrados: " + result);
    }
}