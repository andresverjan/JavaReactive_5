import java.math.BigInteger;

public class FuncionPuraFactorial {

  public static void main(String[] args) {
        int numero = 5;
        BigInteger resultado = factorial(numero);
        System.out.println("El factorial de " + numero + " es: " + resultado);
    }

    // Función recursiva para calcular el factorial de cualquier número entero positivo
    public static BigInteger factorial(int n) {
        if (n == 0 || n == 1) {
            return BigInteger.ONE;  
        } else {
            return BigInteger.valueOf(n).multiply(factorial(n - 1));  
        }
    }
  
}
