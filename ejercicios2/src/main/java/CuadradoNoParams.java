import java.util.ArrayList;
import java.util.List;
//2. Crear una funcion No que tome una lista de números y devuelva una nueva lista con el cuadrado de cada número.
public class CuadradoNoParams {
    static List<Integer> numbers;
    List<Integer>  cuadrado(){
        for (int pos =0; pos < numbers.size(); pos++) {
            numbers.set(pos, numbers.get(pos) * numbers.get(pos));
        }
        return numbers;
    }


    public static void main(String[] args) {
        numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(4);
        numbers.add(5);

        CuadradoNoParams main = new CuadradoNoParams();
        System.out.println("Cuadrados: " +  main.cuadrado());
    }
}
