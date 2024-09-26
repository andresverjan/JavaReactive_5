import java.util.ArrayList;
import java.util.List;

//3. Crear una función pura que filtre los números impares de una lista.
public class FiltroPares {

    List<Integer> filtroPares(List<Integer> lista){
        List<Integer> pares = new ArrayList<>();
        for (int num : lista) {
            if(num % 2 == 0){
                pares.add(num);
            }
        }
        return pares;
    }
    public static void main(String[] args) {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5);
        FiltroPares main = new FiltroPares();
        List<Integer> result = main.filtroPares(numbers);
        System.out.println("Pares: " + result);
    }
}