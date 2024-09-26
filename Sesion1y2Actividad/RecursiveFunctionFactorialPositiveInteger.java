public class RecursiveFunctionFactorialPositiveInteger {

    public static int factorial(int number) {
        if (number == 0) {
            return 1;
        }
        return number * factorial(number - 1);
    }

    public static void main(String[] args) {
        int number = 9;
        int result = factorial(number);

        System.out.println("El factorial de " + number + " es: " + result);
    }
}
