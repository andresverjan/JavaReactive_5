import java.util.List;

public class Main {
    public static int sum(List<Integer> numbers){
        int total = 0;
        for(int num: numbers) {
            total += num;
        }
        return total;
    }
    public static void main(String[] args) {
        List<Integer> numbers = List.of(1,2,3,4,5);
        int result = sum(numbers);
        System.out.println("Suma: " + result);
    }
}