package com.example.demo;

import reactor.core.publisher.Mono;

public class FromCallableExample {

    /*
    Collable: una interfaz que permite ejecutar un proceso para sacar unos resultados. puede lanzar excepciones
    Supplier: solo espera resultados
     */
    public static void main(String[] args) {
        //Crear un Mono a partir de una llamada a una funcion
        Mono<String> fileContentMono = Mono.fromCallable(() -> readFile("example.txt"));
        //Su
        fileContentMono.subscribe(
                content -> System.out.println("Contenido del archivo: " + content),
                error -> System.out.println("Error al leer el arvhivo " + error.getMessage())
        );
    }

    private static String readFile(String fileName) throws Exception {
        Thread.sleep(1000);
        return "Contenido del archivo " + fileName;
    }
}
