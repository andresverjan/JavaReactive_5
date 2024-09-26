import java.util.function.Function;

public class RecursiveFunction {

    public static int factorial(int n) {
        return n == 0 ? 1 : (n * factorial(n - 1));
    };

    public static void main(String[] args) {
        int number = 5;
        System.out.println("El factorial de " + number + " es: " + factorial( number ));
    }
}
