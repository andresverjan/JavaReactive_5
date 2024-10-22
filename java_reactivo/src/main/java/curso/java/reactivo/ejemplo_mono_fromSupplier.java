package curso.java.reactivo;

import reactor.core.publisher.Mono;

public class ejemplo_mono_fromSupplier {
    public static void main(String[] args) {
        Mono<Double> mono = Mono.fromSupplier(() -> {
            double randomValue = Math.random();
            System.out.println("Generando valor aleatorio: " + randomValue);
            return randomValue;
        });

        // Suscribirse al Mono para recibir y procesar el valor generado
        mono.subscribe(System.out::println);
        mono.subscribe(System.out::println);
    }

}
