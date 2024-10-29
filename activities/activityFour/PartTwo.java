package org.example.activityFour;

import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

public class PartTwo {


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
    List<Person> people = new ArrayList<>(List.of(person1, person2, person3, person4, person5, person6, person7, person8, person9));


    Flux<Person> createFluxPerson() {
        return Flux.fromIterable(people);
    }

    /**
     * 2. Gestión de Errores en Flujos
     **/

    // 1: Uso básico de onErrorReturn: Crea un flujo que lanza una excepción al procesar un saldo negativo y maneja el error devolviendo
    // un saldo predeterminado, además imprime el nombre del cliente afectado
    public void oneOneErrorReturn() {
        createFluxPerson()
                .flatMap(person -> {
                    if (person.getBalance() < 0) {
                        System.out.println("Saldo negativo detectado para " + person.getName());
                        return Flux.error(new RuntimeException("Saldo negativo detectado para " + person.getName()));
                    }
                    return Flux.just("Cliente: " + person.getName() + ", Saldo: " + person.getBalance());
                })
                .onErrorReturn("Saldo predeterminado:  666")
                .doOnNext(result -> System.out.println("Resultado: " + result))
                .subscribe(
                        null,
                        error -> System.err.println("Error en el flujo: " + error.getMessage())
                );
    }


    //2: Uso de onErrorResume: Crea un flujo que simula un error al consultar el saldo y maneja el error devolviendo
    // otro flujo con un saldo predeterminado para todos los clientes.
    public void twoOnErrorResume() {
        createFluxPerson()
                .flatMap(person -> {
                    if (person.getBalance() < 0) {
                        return Flux.error(new RuntimeException("Error al consultar el saldo para " + person.getName()));
                    }
                    return Flux.just("Cliente: " + person.getName() + ", Saldo: " + person.getBalance());
                })
                .onErrorResume(error -> {
                    System.out.println(error.getMessage() + ". Asignando saldo predeterminado a los siguientes clientes.");
                    return createFluxPerson()
                            .skipWhile(person -> person.getBalance() >= 0)
                            .map(person -> "Cliente: " + person.getName() + ", Saldo: 666 ");
                })
                .subscribe(
                        System.out::println,
                        error -> System.err.println("Error en el flujo de saldos: " + error.getMessage())
                );
    }

    //3: Manejo específico con doOnError: Implementa un flujo que registre el error cuando ocurra usando doOnError,
    // incluyendo detalles sobre el cliente afectado.
    public void threeDoOnError() {
        createFluxPerson()
                .flatMap(person -> {
                    if (person.getBalance() < 0) {
                        return Flux.error(new RuntimeException("Error al consultar el saldo para " + person.getName() +
                                " (Ciudad: " + person.getCity() + ", Saldo: " + person.getBalance() + ")"));
                    }
                    return Flux.just("Cliente: " + person.getName() + ", Ciudad: " + person.getCity() + ", Saldo: " + person.getBalance());
                })
                .doOnError(error -> {
                    String mensajeError = error.getMessage();
                })
                .subscribe(
                        System.out::println,
                        error -> System.err.println(error.getMessage())
                );
    }

    //4: Uso combinado de errores: Crea un flujo que maneje diferentes tipos de excepciones con onErrorResume.
    public void fourDifferentErrors() {
        createFluxPerson()
                .flatMap(person -> {
                    if (person.getBalance() < 0) {
                        return Flux.error(new IllegalArgumentException("Saldo negativo para " + person.getName()));
                    }
                    return Flux.just("Cliente: " + person.getName() + ", Ciudad: " + person.getCity() + ", Saldo: " + person.getBalance());
                })
                .doOnError(error -> System.err.println("Error registrado: " + error.getMessage()))
                .onErrorResume(IllegalArgumentException.class, error -> Flux.just("Cliente: " + error.getMessage() + ", Saldo: 666 )"))
                .onErrorReturn(RuntimeException.class, "Cliente: Error inesperado, Saldo: 0 ")
                .concatWith(Flux.defer(() ->
                        createFluxPerson()
                                .skipWhile(person -> person.getBalance() >= 0)
                                .map(person -> "Cliente: " + person.getName() + ", Saldo: 666")
                ))
                .subscribe(
                        System.out::println,  // Imprimir cada resultado
                        error -> System.err.println("Error en el flujo de saldos: " + error.getMessage())
                );
    }

    //5: Ignorar errores con onErrorContinue: Crea un flujo que continúe procesando a pesar de los errores usando
    // onErrorContinue, registrando los errores encontrados.
    public void fiveContinueWithErrors() {
        createFluxPerson()
                .flatMap(person -> {
                    if (person.getBalance() < 0) {
                        throw new IllegalArgumentException("Saldo negativo");
                    }
                    return Flux.just("Cliente: " + person.getName() + ", Ciudad: " + person.getCity() + ", Saldo: " + person.getBalance());
                })
                .doOnError(error -> System.err.println("Error registrado: " + error.getMessage()))
                .onErrorContinue((error, persona) -> System.err.println("Se ignoró el error para el cliente: " + persona.toString() + ", " + error.getMessage()))
                .subscribe(
                        System.out::println,
                        error -> System.err.println("Error en el flujo de saldos: " + error.getMessage())
                );
    }
}
