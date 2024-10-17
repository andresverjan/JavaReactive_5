package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@SpringBootApplication
public class SupplierVsDefer {

    public static void main(String[] args) {
        SpringApplication.run(SupplierVsDefer.class,args);
        // Crear un Mono que emite el valor actual del sistema cuando se suscribe
        Mono<String> deferMono = Mono.defer(() -> Mono.just(getCurrentTime()))
                .subscribeOn(Schedulers.parallel());
        // Suscribirse al Mono y procesar el valor emitido
        deferMono.subscribe(System.out::println);
        deferMono.subscribe(System.out::println);
        deferMono.subscribe(System.out::println);
        deferMono.subscribe(System.out::println);

        Mono<String> mono = Mono.fromSupplier(() -> "Supplier -> Hora actual: " + System.currentTimeMillis());
        mono.subscribe(System.out::println);
        mono.subscribe(System.out::println);
        mono.subscribe(System.out::println);
        mono.subscribe(System.out::println);
    }

    // Método para obtener la hora actual
    private static String getCurrentTime() {
        return "Defer -> Hora actual: " + System.currentTimeMillis();
    }

}
