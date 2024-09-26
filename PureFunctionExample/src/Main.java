import java.util.ArrayList;
import java.util.List;

public class Main {

    private static List<Integer> result = new ArrayList<>();
    public static int sum(List<Integer> numbers){
        int total = 0;
        for(int num: numbers) {
            total += num;
        }
        return total;
    }

    public static List<Integer> square(List<Integer> numbers){
        int total = 0;
        List<Integer> list = new ArrayList<>();
        for(int num: numbers) {
            total =  num * num;
            list.add(total);
        }
        return list;
    }
    public static void squarenoFunction(List<Integer> numbers){
        int total = 0;
        List<Integer> list = new ArrayList<>();
        for(int num: numbers) {
            total =  num * num;
            result.add(total);
            System.out.println(total);
        }
    }

    public static List<Integer> filter(List<Integer> numbers){
        int total = 0;
        List<Integer> list = new ArrayList<>();
        for(int num: numbers) {
            if (num%2!=0 ){
                list.add(num);
            }
        }
        return list;
    }

    public static int factorial(int num){
        if(num == 0){
            return 1;
        }
        else
            return num * factorial(num-1);
    }

    public static List<Integer> funcionSuperior(List<Integer> numbers){
        return filter(numbers);
    }

    public static void main(String[] args) {
        /*Lista*/
        List<Integer> numbers = List.of(1,2,3,4,5);


        //int result = sum(numbers);
        //squarenoFunction(numbers);



        /*factorial*/
        //int res = factorial(5);
        //System.out.println(res);
        /*factorial*/


        //List<Integer> resultado = filter(numbers);
/*
        for(int num: resultado){
            System.out.println("Resultado: " + num);
        }
*/

    }
}