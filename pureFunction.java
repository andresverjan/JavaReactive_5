import java.util.ArrayList;
import java.util.List;

public class pureFunction {

    /*El resultado siempre es el mismo si las entradas son las mismas en diferentes ejecuciones.
    No llama ni modifica variables o parametros fuera de su scope
    No modifica  variables globales
    No cambia estados de un objeto*/
    public static int suma(List<Integer> numbers) {
        int total = 0;
        for(int number : numbers) {
            total += number;
        }
        return total;
    }

    public static void main(String[] args) {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        int result = suma(numbers);
        System.out.println("Suma: " + result);

    }
}
