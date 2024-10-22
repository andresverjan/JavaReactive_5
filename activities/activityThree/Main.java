package activities.activityThree;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@RequiredArgsConstructor
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        Operations operations = new Operations();

        System.out.println("Punto 2 y 3: Filtrar personas mayores de 30 años y mostrar sus nombres:");
        var filter = operations.filterOver30();

        System.out.println("\nPunto y 5: Mostrar la primera persona:");
        var firstPerson = operations.showFirstPerson();

        System.out.println("\nPunto 6: Agrupar por signo zodiacal:");
        var groupBySign = operations.groupBySign();

        System.out.println("\nPunto 7: Obtener personas con edad 30:");
        var byAge = operations.getByAge(30);

        System.out.println("\nPunto 8: Obtener personas con signo 'Leo':");
        var bySign = operations.getBySign("Leo");

        System.out.println("\nPunto 9: Obtener persona por teléfono '123456789':");
        var byTel = operations.getByTel("123456789");

        System.out.println("\nPunto 10: Agregar nueva persona:");
        var newPerson = new Person("Carlos", "Ruiz", "111111111", 50, "Aries");
        var add = operations.add(newPerson);

        System.out.println("\nPunto 11: Eliminar persona 'Carlos Ruiz':");
        var delete = operations.delete(newPerson);
    }
}