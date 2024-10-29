package org.example.activityFour;

import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

public class PartThree {


    String[] direction1 = new String[]{"Calle 1", "Calle 2", "Calle 3"};
    String[] direction2 = new String[]{"Calle 4", "Calle 5", "Calle 6"};
    String[] direction3 = new String[]{"Calle 7", "Calle 8", "Calle 9"};
    String[] direction4 = new String[]{"Calle 10", "Calle 11", "Calle 12"};

    Person person1 = new Person("Juan", "Cali", direction1, 3065);
    Person person2 = new Person("Maria", "Manizales", direction2, 245);
    Person person3 = new Person("Carlos", "Medellin", direction3, -43860);
    Person person4 = new Person("Laura", "Bogota", direction4, 345545);
    Person person5 = new Person("Pedro", "Cali", direction1, 2873);
    Person person6 = new Person("Ana", "Caldas", direction2, -267542);
    Person person7 = new Person("David", "Manizales", direction3, 4235);
    Person person8 = new Person("Sofia", "Medellin", direction4, 36553);
    Person person9 = new Person("Javier", "Boston", direction1, 273);

    List<Person> peopleOne = new ArrayList<>(List.of(person1, person2, person3, person4));
    List<Person> peopleTwo = new ArrayList<>(List.of(person5, person6, person7, person8, person9));

    List<String> states = new ArrayList<>(List.of("activo", "inactivo", "activo", "inactivo"));



    Flux<Person> createFluxPersonOne() {
        return Flux.fromIterable(peopleOne);
    }

    Flux<String> fluxStates() {
        return Flux.fromIterable(states);
    }

    Flux<Person> createFluxPersonTwo() {
        return Flux.fromIterable(peopleTwo);
    }

    static Flux<Person> createEmptyFlux() {
        return Flux.empty();
    }

    /**
     * 3. Combinación de Flujos de Datos
     **/

    // 1: Uso básico de merge: Combina dos flujos en uno solo sin importar el orden y muestra el nombre del cliente junto con su ciudad.
    public void oneMerge() {
        var combinedFlow = Flux.merge(createFluxPersonOne(), createFluxPersonTwo())
                .map(person -> "Cliente: " + person.getName() + ", Ciudad: " + person.getCity());

        combinedFlow.subscribe(System.out::println);
    }

    // 2: Uso básico de zip:  Combina dos flujos emparejando sus elementos y muestra la suma del saldo total.
    public void twoZip() {
        Flux<Double> totalBalance = Flux.zip(createFluxPersonOne(), createFluxPersonTwo())
                .map(tuple -> {
                    var personOne = tuple.getT1();
                    var personTwo = tuple.getT2();
                    return personOne.getBalance() + personTwo.getBalance();
                });

        totalBalance.reduce(0.0, Double::sum)
                .subscribe(total -> System.out.println("Suma total de saldos: " + total));
    }

    //3: Uso avanzado de combineLatest:  Combina dos flujos tomando el último valor emitido por cada uno y muestra la ciudad junto con su respectivo estado.
    public void threeCombineLatest() {
        Flux<String> combinedFlow = Flux.combineLatest(
                createFluxPersonTwo().map(Person::getCity),
                fluxStates().map(states -> states ),
                (city, states) -> "Ciudad: " + city + ", Estado: " + states
        );
        combinedFlow.subscribe(System.out::println);
    }

    // 4: Uso básico de concat: Concatenar dos flujos en uno solo manteniendo el orden e imprimiendo todos los nombres.
    public void fourConcat() {

        Flux<String> concatFlow = Flux.concat(
                createFluxPersonOne().map(Person::getName),
                createFluxPersonTwo().map(Person::getName)
        );
        concatFlow.subscribe(System.out::println);
    }

    //5: Uso avanzado de switchIfEmpty: Proporcionar un flujo alternativo si el original está vacío e imprime un mensaje personalizado.
    public static void fiveSwitchIfEmtpy() {

        Flux<String> alternativeFlow = createEmptyFlux()
                .map(Person::getCity)
                .switchIfEmpty(Flux.just("No hay personas disponibles."));

        alternativeFlow.subscribe(System.out::println);

    }
}