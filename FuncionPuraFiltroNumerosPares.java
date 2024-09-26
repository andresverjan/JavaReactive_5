import java.util.List;
import java.util.stream.Collectors;

public class FuncionPuraFiltroNumerosPares {
  
  public static void main(String[] args) {
        List<Integer> numeros = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> pares = filtrarPares(numeros);
        System.out.println("Los números pares son: " + pares);  
    }

    // Función que verifica y filtra los números pares usando funciones de orden superior
    public static List<Integer> filtrarPares(List<Integer> numeros) {
        return numeros.stream()                           
                      .filter(numero -> numero % 2 == 0)  
                      .collect(Collectors.toList());      
    }
}
