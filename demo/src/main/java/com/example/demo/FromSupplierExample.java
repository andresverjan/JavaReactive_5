package com.example.demo;

import reactor.core.publisher.Mono;

public class FromSupplierExample {

    public static void main(String[] args) {
        Mono<Double> mono = Mono.fromSupplier(() -> {
            double randomValue = Math.random();
            return randomValue;
        });

        // Suscribirse al Mono para recibir y procesar el valor generado
        mono.subscribe(System.out::println);
        mono.subscribe(System.out::println);
    }
}
