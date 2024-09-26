import java.util.List;

public class NoPureFunction {
    public static void squareNoPure(List<Integer> listNumbers) {
        for (int i = 0; i < listNumbers.size(); i++) {
            listNumbers.set(i, listNumbers.get(i) * listNumbers.get(i));
        }
    }

    public static void main(String[] args) {
        List<Integer> listModified = new ArrayList<>(
            List.of(1, 2, 3, 4, 5)
        );
        squareNoPure(listModified);
        System.out.println(listModified);
    }
}