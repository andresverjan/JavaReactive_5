import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

//5. Crear una funcion uqe se encargue de verificar los numeros pares de una lista, usando funciones de orden superior.
public class FuncionSuperiorFiltroPares {

    Function<Integer, Boolean> filtroPares = (num) -> num % 2 == 0;

    List<Integer> filtro(List<Integer> lista, Function<Integer, Boolean> filtroPares){
        List<Integer> pares = new ArrayList<>();
        for (int num : lista) {
            if(filtroPares.apply(num)){
                pares.add(num);
            }
        }
        return pares;
    }
    public static void main(String[] args) {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5);
        FuncionSuperiorFiltroPares main = new FuncionSuperiorFiltroPares();
        List<Integer> result = main.filtro(numbers, main.filtroPares);
        System.out.println("Pares: " + result);
    }
}