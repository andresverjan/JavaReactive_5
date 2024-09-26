import java.util.List;
import java.util.ArrayList;

public class PureFunction {
    public static List<String> upperCaseFruits(List<String> listFuits) {
        List<String> result = new ArrayList<>();
        
        for (String fruit : listFuits) {
            result.add(fruit.toUpperCase());
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println( 
            upperCaseFruits(
                List.of("Apple", "Strawberry", "Pineapple", "Bannana", "Mango")
            ) 
        );
    }
}
