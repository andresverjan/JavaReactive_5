import java.util.ArrayList;
import java.util.List;

public class FuncionNoPuraCuadradoDeNumeros {

  static int contNum = 0;
  public static void main(String[] args) {
        
        List<Integer> numeros = List.of(1, 2, 3, 4, 5);
        List<Integer> cuadrados = obtenerCuadrados(numeros);
        System.out.println(cuadrados);  // Salida: [1, 4, 9, 16, 25]

        
      
  }

  // Función no pura que devuelve una nueva lista con el cuadrado de cada número
  public  static List<Integer> obtenerCuadrados(List<Integer> numeros){
    List<Integer> cuadrados = new ArrayList<>();
    for (Integer numero : numeros) {
        cuadrados.add(numero * numero);  // Se añade el cuadrado de cada número
      contNum++; 
      System.out.println("El contador va en: "+ contNum); 
    }
    return cuadrados;
}
    
  
}
