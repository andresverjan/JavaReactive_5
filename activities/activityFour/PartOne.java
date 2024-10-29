package org.example.activityFour;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class PartOne {

    String[] direction1 = new String[]{"Calle 1", "Calle 2", "Calle 3"};
    String[] direction2 = new String[]{"Calle 4", "Calle 5", "Calle 6"};
    String[] direction3 = new String[]{"Calle 7", "Calle 8", "Calle 9"};
    String[] direction4 = new String[]{"Calle 10", "Calle 11", "Calle 12"};

    Person person1 = new Person("Juan", "Cali", direction1, 3065);
    Person person2 = new Person("Maria", "Manizales", direction2, 245);
    Person person3 = new Person("Carlos", "Medellin", direction3, -43860);
    Person person4 = new Person("Laura", "Bogota", direction4, 345545);
    Person person5 = new Person("Pedro", "Cali", direction1, 2873);
    Person person6 = new Person("Ana", "Caldas", direction2, 267542);
    Person person7 = new Person("David", "Manizales", direction3, 4235);
    Person person8 = new Person("Sofia", "Medellin", direction4, 36553);
    Person person9 = new Person("Javier", "Boston", direction1, 273);

    List<Person> people = new ArrayList<>(List.of(person1, person2, person3, person4, person5, person6, person7, person8, person9));

    Flux<Person> createFluxPerson() {
        return Flux.fromIterable(people);
    }

    /**
     * 1. Manipulación de Flujos de Datos
     **/

    //1. Usa map para convertir sus nombres a mayúsculas y luego usa flatMap
    // para dividir cada nombre en letras individuales. Además, imprime la cantidad total de letras procesadas.
    public Disposable toUpperAndPrint() {
        return createFluxPerson()
                .map(person -> person.getName().toUpperCase())
                .flatMap(name -> Flux.fromArray(name.split("")))
//                .doOnNext(letter -> System.out.println(letter))
                .count()
                .doOnSuccess(total -> System.out.println("Cantidad total de letras procesadas: " + total))
                .doOnError(error -> System.out.println("Error: " + error.getMessage()))
                .switchIfEmpty(Mono.just(0L).doOnSuccess(total -> System.out.println("No hay personas mayores de 30 años")))
                .subscribe();

    }


    //2.Crea un flujo que contenga las ciudades donde viven los clientes. Filtra las ciudades
    // que empiezan con la letra "B" y elimina duplicados. Luego, cuenta cuántas ciudades únicas se encontraron.
    public Disposable filterCities() {
        return createFluxPerson()
                .map(Person::getCity)
                .filter(city -> city.startsWith("B"))
                .distinct()
                .doOnNext(city -> System.out.println("Ciudad: " + city))
                .count()
                .doOnSuccess(total -> System.out.println("Cantidad total de ciudades únicas que comienzan con B: " + total))
                .subscribe();
    }


    //3.Crea un flujo con una lista desordenada de saldos de cuentas bancarias y ordénalos.
    // Luego, toma los primeros cinco saldos más altos y muestra el nombre del cliente junto con su saldo.
    public Disposable sortAndTakeTop5() {
        return createFluxPerson()
                .sort(Comparator.comparing(Person::getBalance).reversed())
                .take(5)
                .doOnNext(person -> System.out.println("Cliente: " + person.getName() + ", Saldo: " + person.getBalance()))
                .subscribe();
    }


    //4. Crea un flujo que contenga una lista de transacciones (nombres y montos). Filtra las transacciones superiores
    // a 1000 y convierte cada monto a euros (asumiendo una tasa ficticia). Además, suma todos los montos convertidos y muestra el total.
    public Disposable processTransactions() {
        double conversionRate = 0.85;
        return createFluxTransactions()
                .filter(transaction -> transaction.getValue() > 1000)
                .map(transaction -> {
                    double valueInE = transaction.getValue() * conversionRate;
                    return new Transaction(transaction.getName(), valueInE);
                })
                .doOnNext(transaction -> System.out.println("Transacción: " + transaction.getName() + ", Monto en EUR: " + transaction.getValue()))
                .map(Transaction::getValue)
                .reduce(0.0, Double::sum)
                .doOnSuccess(total -> System.out.println("Monto total en euros: " + total))
                .subscribe();
    }


    //5.Simula una llamada a un servicio externo que devuelve un flujo de datos sobre el estado de las
    // cuentas bancarias por cada dirección del cliente usando flatMap. Imprime el estado junto con el nombre del cliente.
    public Flux<String> getBankAccountStatus() {
        return createFluxPerson()
                .flatMap(person -> Flux.fromArray(person.getDirections())
                        .flatMap(direction -> getAccountStatusByAddress(direction)
                                .map(status -> "Cliente: " + person.getName() + ", Dirección: " + direction + ", Estado: " + status)))
                .doOnNext(System.out::println)
                .doOnComplete(() -> System.out.println("El flujo de estados de cuenta ha terminado."));
    }

    Mono<String> getAccountStatusByAddress(String direction) {
        var random = new Random();
        var state = random.nextBoolean() ? "ACTIVA" : "INACTIVA";

        return Mono.just("Estado de cuenta: " + state)
                .delayElement(Duration.ofSeconds(1));
    }

    Flux<Transaction> createFluxTransactions() {
        return Flux.just(
                new Transaction("Compra 1", 500),
                new Transaction("Compra 2", 1500),
                new Transaction("Compra 3", 2500),
                new Transaction("Compra 4", 800),
                new Transaction("Compra 5", 3200)
        );
    }
}