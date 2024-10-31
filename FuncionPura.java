    import java.util.List;

    public class FuncionPura {

        public static void main(String[] args) {
            var numbers = List.of(1, 2, 3, 4, 5);
            var total = suma(numbers);
            System.out.println("Suma: " + total);

        }

        public static int suma(List<Integer> numbers) {
            var total = 0;
            for (var number : numbers) {
                total += number;
            }
            return total;
        }
    }
