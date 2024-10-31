package curso.java.reactivo.talleres;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

public class ejemplo_combinar_flujos {
    public static void main(String[] args) {
        Flux<String> flux1 = Flux.just("A", "B");
        Flux<String> flux2 = Flux.just("C", "D");

        Flux<String> merged = Flux.merge(flux1, flux2);
        merged.subscribe(System.out::println); // Imprime: A B C D

        Flux<String> names = Flux.just("Alice", "Bob");
        Flux<Integer> ages = Flux.just(25, 30);

        Flux<Tuple2<String, Integer>> combinedZip = Flux.zip(names, ages);
        combinedZip.subscribe(tuple -> System.out.println(tuple.getT1() + " is " + tuple.getT2() + " years old."));

        Flux<String> firstNames = Flux.just("John", "Jane");
        Flux<String> lastNames = Flux.just("Doe", "Smith");

        Flux<String> combined = Flux.combineLatest(firstNames, lastNames,
                (firstName, lastName) -> firstName + " " + lastName);

        combined.subscribe(System.out::println);

    }
}
