package curso.java.reactivo.talleres;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ejemplo_error {
    public static void main(String[] args){
        Mono<Integer> source = Mono.just("error")
                .map(Integer::parseInt)
                .onErrorResume(error -> {
                    System.out.println("Error occurred: " + error.getMessage());
                    return Mono.just(0);
                    // Proporcionar un valor alternativo en caso de error
                });
        source.subscribe(System.out::println);

        Flux<Integer> numbers = Flux.just(1, 2, 3)
                .map(i -> {
                    if (i ==3) throw new RuntimeException("Error occurred on number2");
                    return i;
                })
                .onErrorReturn(-1);
        numbers.subscribe(
                value -> System.out.println("Received: " + value),
                error -> System.err.println("Error: " + error),
                () -> System.out.println("Completed!")
        );


        Flux<Integer> numbersFlux = Flux.just(1, 2, 3, 4, 5);
        Flux<Integer> transformedFlux = numbersFlux.map(number -> {
            if (number == 3) {
                throw new RuntimeException("Encountered an error processing element: " + number);
            }
            return number * 2;
        });
        transformedFlux.doOnError(error -> System.err.println("An error occurred: " + error.getMessage())).subscribe(
                System.out::println,
                // Handle errors emitted by the Flux
                error -> System.err.println("Error: " + error.getMessage())
        );
    }
}
