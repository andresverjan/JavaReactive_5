import java.util.ArrayList;
import java.util.List;

public class FuncionPuraCuadradoDeNumeros {
  public static void main(String[] args) {
        List<Integer> numeros = List.of(1, 2, 3, 4, 5);
        List<Integer> cuadrados = obtenerCuadrados(numeros);
        System.out.println(cuadrados);  // Salida: [1, 4, 9, 16, 25]
    }

    // Función pura que devuelve una nueva lista con el cuadrado de cada número
    public static List<Integer> obtenerCuadrados(List<Integer> numeros) {           

        List<Integer> cuadrados = new ArrayList<>();
        for (Integer numero : numeros) {
            cuadrados.add(numero * numero);  // Se añade el cuadrado de cada número
        }
        return cuadrados;
    }
}
