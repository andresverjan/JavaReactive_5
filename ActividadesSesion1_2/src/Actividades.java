import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Actividades {


    private static int totalCount = 0;

    public static List PrimeraFuncion(List<Integer> lista){
        List<Integer> nuevaLista = new ArrayList<>();
        for (int num : lista){
            nuevaLista.add(num*num);
        }
        return nuevaLista;
    }

    public static List<Integer> SegundaFuncion(List<Integer> lista) {
        List<Integer> nuevaLista = new ArrayList<>();
        for (int num : lista){
            nuevaLista.add(num*num);
            totalCount++;
        }
        return nuevaLista;
    }

    public static List<Integer> TerceraFuncion(List<Integer> lista) {
        return lista.stream()
                .filter(n -> n % 2 != 0)
                .collect(Collectors.toList());
    }

    public static int CuartaFuncion(int n) {
        if (n == 0 || n == 1) {
            return 1;
        }
        return n * CuartaFuncion(n - 1);
    }

    public static List<Integer> QuintaFuncion(List<Integer> lista) {
        return lista.stream()
                .filter(numero -> numero % 2 == 0)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        List<Integer> numbers = List.of(1,2,3,4);
        List resultadoPF = PrimeraFuncion(numbers);
        System.out.println(resultadoPF);
        System.out.println("Variable glogal:" + totalCount);
        List resultadoSF = SegundaFuncion(numbers);
        System.out.println(resultadoSF);
        System.out.println("Variable glogal:" + totalCount);
        List resultadoTF = TerceraFuncion(numbers);
        System.out.println(resultadoTF);
        int resultadoCF = CuartaFuncion(totalCount);
        System.out.println(resultadoCF);
        List resultadoQF = QuintaFuncion(numbers);
        System.out.println(resultadoQF);
    }



}
