import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HigherOrderFunction {
    public static boolean esPar(int numero) {
        return numero % 2 == 0;
    }

    public static List<Integer> verificarPares(List<Integer> lista) {
        return lista.stream()
                    .filter(HigherOrderFunction::esPar)
                    .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        System.out.println(
            verificarPares(
                Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                )
        );
    }
}
