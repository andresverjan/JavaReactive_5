package com.example.combFlow;


import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

public class CombinationFlowsExample {
    public static void main(String[] args) {

        //MERGE
        /*
        se hace merge entre 2 o mas flux y crea un nuevo flux
        es util cuando el flux de resultado no importa que orden tiene
         */
        System.out.println("************* MERGE *************");
        Flux<String> flux1 = Flux.just("A", "B");
        Flux<String> flux2 = Flux.just("C", "D");

        Flux<String> merged = Flux.merge(flux1, flux2);
        merged.subscribe(System.out::println); // Imprime: A B C D


        //ZIP
        /*
        Genera una tupla(par) de informacion
        ayuda a correlacionar el flux 1 con el flux 2
        Deben tener ambos flux el mismo tamaño
        Si un flux tiene mas datos que otros no se imprimen los que no tengan par
        Zip se usar con maximo 7 flux
         */
        System.out.println("************* ZIP *************");
        Flux<String> names = Flux.just("Alice", "Bob");
        Flux<Integer> ages = Flux.just(25, 30);

        //Siempre T1 sera el fluz de nombre y el T2 el de edad
        Flux<Tuple2<String, Integer>> combinedZip = Flux.zip(names, ages); //tupla2  el numero depende de la cantidad de flux que quiera combinar
        combinedZip.subscribe(tuple -> System.out.println(tuple.getT1() + " is " + tuple.getT2() + " years old."));


        //COMBINATED
        /*
        Combina el ultimo valor emitido
        es util cuando se necesita mantener actualizado el ultimo estado conocido
        No deben ser iguales los tamaños de los Flux
         */
        System.out.println("************* COMBINATED *************");
        Flux<String> firstNames = Flux.just("John", "Jane");
        Flux<String> lastNames = Flux.just("Doe", "Smith", "vargas");

        //Toma el ultimo valor del flux1 y lo combina con cada uno de los valores del flux2
        Flux<String> combined = Flux.combineLatest(firstNames, lastNames,
                (firstName, lastName) -> firstName + " " + lastName);

        combined.subscribe(System.out::println);
    }
}
