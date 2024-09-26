import java.util.ArrayList;
import java.util.List;

//4. Crea una función recursiva que calcule el factorial de un número entero positivo.
public class Factorial {

    Integer factorial(Integer lista){
        if(lista == 1){
            return 1;
        }
        return lista * factorial(lista - 1);
    }
    public static void main(String[] args) {

        Factorial main = new Factorial();
        Integer result = main.factorial(5);
        System.out.println("Facorial: " + result);
    }
}