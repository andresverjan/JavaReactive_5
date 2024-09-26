import java.util.List;
import java.util.stream.Collectors;

public class FuncionPuraFiltroImpares {
  public static void main(String[] args) {
        List<Integer> numeros = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
        List<Integer> impares = filtrarImpares(numeros);
        System.out.println(impares);  
    }

    public static List<Integer> filtrarImpares(List<Integer> numeros) {
        return numeros.stream()     
                .filter(numero -> numero % 2 != 0) 
                .collect(Collectors.toList());  
    }  
  

}
