package activities.activityThree;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class Operations {

    Person person1 = new Person("Juan", "Pérez", "123456789", 30, "Aries");
    Person person2 = new Person("María", "Gómez", "987654321", 25, "Virgo");
    Person person3 = new Person("Carlos", "Martínez", "555444333", 40, "Capricornio");
    Person person4 = new Person("Laura", "Rodríguez", "111222333", 35, "Tauro");
    Person person5 = new Person("Pedro", "Sánchez", "999888777", 28, "Leo");
    Person person6 = new Person("Ana", "Fernández", "666777888", 22, "Acuario");
    Person person7 = new Person("David", "López", "333222111", 45, "Cáncer");
    Person person8 = new Person("Sofía", "Díaz", "777666555", 32, "Géminis");
    Person person9 = new Person("Javier", "Hernández", "888999000", 27, "Escorpio");
    Person person10 = new Person("Elena", "García", "112233445", 33, "Libra");
    Person person11 = new Person("Pablo", "Muñoz", "554433221", 38, "Piscis");
    Person person12 = new Person("Rosa", "Jiménez", "998877665", 29, "Sagitario");

    List<Person> people = new ArrayList<>(List.of(person1, person2, person3, person4, person5, person6, person7, person8, person9, person10, person11, person12));


    // Punto 1: Crear un Flux a partir de la lista de personas
    Flux<Person> createFluxPerson() {
        return Flux.fromIterable(people);
    }

    // Punto 2 y 3: Filtrar personas mayores de 30 años y mostrar sus nombres, utilizando Disposable y doOnNext()
    public Disposable filterOver30() {
        return createFluxPerson()
                .filter(person -> person.getEdad() > 30)
                .map(Person::getNombre)
                .doOnNext(name -> System.out.println(name))
                .subscribe();
    }

    // Punto 4: Crear un Mono con la primera persona de la lista
    Mono<Person> createMonoWithFirstPerson() {
        return createFluxPerson()
                .next();
    }

    // Punto 5: Mostrar el nombre y apellido de la persona del Mono utilizando flatMap() y subscribe()
    public Disposable showFirstPerson() {
        return createMonoWithFirstPerson()
                .flatMap(person -> Mono.just(person.getNombre() + " " + person.getApellido()))
                .doOnNext(nombreCompleto -> System.out.println("Nombre y apellido: " + nombreCompleto))
                .subscribe();
    }

    // Punto 6: Agrupar personas por signo zodiacal y mostrar cantidad por signo utilizando Disposable y doOnNext()
    public Disposable groupBySign() {
        return createFluxPerson()
                .groupBy(Person::getSigno)
                .flatMap(group -> group
                        .collectList()
                        .doOnNext(list -> System.out.println(group.key() + ": " + list.size())))
                .subscribe();
    }

    // Punto 7: Obtener personas por edad utilizando Disposable y doOnNext()
    public Disposable getByAge(int edad) {
        return createFluxPerson()
                .filter(person -> person.getEdad() == edad)
                .doOnNext(person -> System.out.println("Después del filtro (edad = " + edad + "): " + person))
                .subscribe();
    }

    // Punto 8: Obtener personas por signo zodiacal utilizando Disposable y doOnNext()
    public Disposable getBySign(String sign) {
        return createFluxPerson()
                .filter(person -> person.getSigno().equalsIgnoreCase(sign))
                .doOnNext(person -> System.out.println("Después del filtro (signo = " + sign + "): " + person))
                .subscribe();
    }

    // Punto 9: Obtener persona por número de teléfono utilizando Disposable y doOnNext()
    public Disposable getByTel(String telefono) {
        return createFluxPerson()
                .filter(person -> person.getTelefono().equals(telefono))
                .singleOrEmpty()
                .doOnNext(person -> System.out.println("Persona encontrada: " + person))
                .subscribe();
    }

    // Punto 10: Agregar una nueva persona utilizando Disposable y doOnNext()
    public Disposable add(Person person) {
        people.add(person);
        return Mono.just(person)
                .doOnNext(p -> System.out.println("Persona agregadar: " + p))
                .subscribe();
    }

    // Punto 11: Eliminar una persona de la lista utilizando Disposable y doOnNext()
    public Disposable delete(Person person) {
        if (people.remove(person)) {
            return Mono.just(person)
                    .doOnNext(p -> System.out.println("Persona eliminada: " + p))
                    .subscribe();
        }
        return Mono.empty()
                .doOnNext(v -> System.out.println("Intento de eliminar persona no existente"))
                .subscribe();
    }
}