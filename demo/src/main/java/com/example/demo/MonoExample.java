package com.example.demo;

import reactor.core.publisher.Mono;

public class MonoExample {

    public static void main(String[] args) {
        Mono<String> mono = Mono.just("Hola, Luisa!" );
        mono.subscribe(
                element -> System.out.println( "Elemento recibido: " + element),
                error -> System.err.println( "Error: " + error.getMessage()),
                () -> System.out.println( "Secuencia completada" )
        );
    }
}
