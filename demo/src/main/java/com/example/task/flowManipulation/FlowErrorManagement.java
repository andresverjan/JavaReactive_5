package com.example.task.flowManipulation;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;

import static com.example.task.flowManipulation.Workshop.getPeople;

public class FlowErrorManagement {

    public List<Person> createPeopleList() {
        return getPeople();
    }

    public Flux<Person> createFluxPerson() {
        return Flux.fromIterable(createPeopleList());
    }

    /**
     *  2. Gestión de Errores en Flujos
     **/
    //1: Uso básico de onErrorReturn
    //Crea un flujo que lanza una excepción al procesar un saldo negativo y maneja el error devolviendo un saldo predeterminado
    //imprime el nombre del cliente afectado.
    public void proccessNegativeBalance(){
        createFluxPerson()
                .map(person -> {
                    if(person.getBalance() < 0){
                        System.out.println("Cliente: " + person.getName() + " | saldo negativo: " + person.getBalance());
                    }
                    return person;
                })
                .onErrorReturn(new Person("","",new String[]{},0))
                .doOnNext(System.out::println)
                .subscribe(
                        person -> System.out.println("Saldo de " + person.getName() + ": " + person.getBalance()),
                        error -> System.out.println("Error: " + error.getMessage()));
    }

    //2: Uso de onErrorResume
    //Crea un flujo que simula un error al consultar el saldo
    // maneja el error devolviendo otro flujo con un saldo predeterminado para todos los clientes.
    public void proccessErrorBalance(){
        createFluxPerson()
                .flatMap(person -> {
                    if(person.getBalance() < 0){
                        return Flux.error(new RuntimeException("Ocurrio un error al consultar saldo de: " + person.getName()));
                    }
                    return Flux.just("Cliente: " + person.getName() + ", Saldo: " + person.getBalance());
                })
                .onErrorResume(error -> {
                    System.out.println(error.getMessage());
                    return createFluxPerson()
                            .skipWhile(person -> person.getBalance() >= 0)
                            .map(person -> "Cliente: " + person.getName() + ", Saldo: " + person.getBalance());
                })
                .subscribe(
                        System.out::println,
                        error -> System.out.println("Error: " + error.getMessage()));
    }

    //3: Manejo específico con doOnError
    //Implementa un flujo que registre el error cuando ocurra usando doOnError,
    // incluyendo detalles sobre el cliente afectado.
    public void proccessError(){
        createFluxPerson()
                .flatMap(person -> {
                    if(person.getBalance() < 0){
                        return Flux.error(new RuntimeException("Ocurrió un error al consultar saldo de: " + person));
                    }
                    return Flux.just(person);
                })
                .doOnError(error -> {
                    System.out.println(error.getMessage());
                })
                .subscribe(
                        System.out::println,
                        error -> System.out.println("Error: " + error.getMessage()));
    }

    //4: Uso combinado de errores
    //Crea un flujo que maneje diferentes tipos de excepciones con onErrorResume.
    public void proccessErrorMix(){
        createFluxPerson()
                .flatMap(person -> {
                    if("Manchester".equals(person.getCity())){
                        return Flux.error(new IllegalArgumentException("fuera de Colombia: " + person.getName()));
                    }
                    return Flux.just("Cliente: " + person.getName() + " | Saldo: " + person.getBalance());
                })
                .doOnError(error -> System.err.println("Error registrado: " + error.getMessage()))
                .onErrorResume(IllegalArgumentException.class,e ->
                                Flux.just("Cliente " + e.getMessage()))
                .subscribe(
                        System.out::println,
                        error -> System.out.println("Error: " + error.getMessage()));
    }

    //5: Ignorar errores con onErrorContinue
    //Crea un flujo que continúe procesando a pesar de los errores usando onErrorContinue,
    // registrando los errores encontrados.
    public void ignoreError(){
        createFluxPerson()
                .flatMap(person -> {
                    if(person.getBalance() < 0){
                        return Flux.error(new RuntimeException("Saldo negativo"));
                    }
                    return Flux.just(person);
                })
                .doOnError(error -> System.out.println(error.getMessage()))
                .onErrorContinue((error, person) -> System.err.println("Ignorando mensaje de error: " +
                        error.getMessage() + " Para el cliente: " + person))
                .subscribe(
                        System.out::println,
                        error -> System.out.println("Error: " + error.getMessage()))
                ;
    }




}
