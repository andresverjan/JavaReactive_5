package com.example.managementError;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ErrorManagementExample {
    public static void main(String[] args) {

        /*OnErrorResume
        No frena el programa.
        aunque falla la emision del codigo sigue sucediendo
        devuelve un publisher
         */

        Mono<Integer> source = Mono.just("error")
                .map(Integer::parseInt)
                .onErrorResume(error -> {
                    System.out.println("Error occurred: " + error.getMessage());
                    return Mono.just(0); // Proporcionar un valor alternativo en caso de error
                });

        source.subscribe(System.out::println);

        /*
        OnErrorReturn
        No detiene la ejecucion del programa
        Retorna un valor estatico cuando un error se presenta
         */
        System.out.println("\nFlux con OnErrorReturn"+"\n=================================");
        Flux<Integer> numbers = Flux.just(1, 2, 3)     //se crea un flux con los numeros 1, 2 y 3
                .map(i -> {              //Se hace un map para transformar los datos
                    if (i == 2) throw new RuntimeException("Error occurred on number2");    //si el numero es 2 lanza la excepcion
                    return i;            //en caso de que no pase la excepcion continua con el flujo exitoso
                })
                .onErrorReturn(-1);        //en caso de excepcion se devulve -1 (catch del flujo)

        numbers.subscribe(            // se realiza suscripcion al Flux
                value -> System.out.println("Received: " + value),     //valor que nos llega al momento del suscribe
                error -> System.err.println("Error: " + error),            //el error para cuando ocurra excepcion
                () -> System.out.println("Completed!")        //complete para cuando termina el proceso
        );

        /*
        DoOnError
        ayuda a controlar algunos tipos de errores
        No modifica el flujo
        permite que registremos el error por consola y hacer limpieza de data quitanto elementos del string o Flux
        Finaliza la ejecucion. interrumple la ejecucion cuando falla
         */
        System.out.println("\nFlux con DoOnError"+"\n=================================");
        Flux<Integer> numbersFlux = Flux.just(1, 2, 3, 4, 5); //se crea flux de numeros
        Flux<Integer> transformedFlux = numbersFlux.map(number -> { //se hace una transformacion donde se recorren los numeros
            if (number == 3) { //Si hay el numero 3 se lanza una excepcion
                throw new RuntimeException("Encountered an error processing element: " + number);
            }
            return number * 2;
        });
        transformedFlux.doOnError(error -> { //CUnado haya un error se envia a consola
            System.err.println("An error occurred: " + error.getMessage());
        }).subscribe( //suscripcion al flux
                System.out::println,
                // Handle errors emitted by the Flux
                error -> System.err.println("Error: " + error.getMessage())
        );
    }
}
