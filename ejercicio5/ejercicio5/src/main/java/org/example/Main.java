package org.example;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.FileNotFoundException;

public class Main {
    /*public static void main(String[] args) {
        Mono<Double> mono = Mono.fromSupplier(() -> {
            double randomValue = Math.random();
            return randomValue;});// Suscribirse al Mono para recibir y procesar el valor generado
            mono.subscribe(System.out::println);
            mono.subscribe(System.out::println);
    }*/
    public static void main(String[] args) throws InterruptedException {
        testDefer();
        testSupplier();
        testCallable();
    }
private static void testDefer(){
    // Crear un Mono que emite el valor actual del sistema cuando se suscribe
    Mono<String> deferMono = Mono.defer(() -> Mono.just(getCurrentTime()));
    System.out.println("Hora Defer");
    // Suscribirse al Mono y procesar el valor emitido
    deferMono.subscribe(System.out::println);
    deferMono.subscribe(System.out::println);
}
    private static void testSupplier(){
        Mono<String> supplierMono = Mono.fromSupplier(() -> getCurrentTime());
        // Suscribirse al Mono y procesar el valor emitido
        System.out.println("Hora Supplier");
        supplierMono.subscribe(System.out::println);
        supplierMono.subscribe(System.out::println);
    }

    private static void testCallable(){
        System.out.println("File Callable");
        Mono<String> fileContentMono = Mono.fromCallable(() -> readFile("example.txt"));
        // Suscribirse al Mono y procesar el valor emitido

        fileContentMono.subscribe(
                content -> System.out.println("El contenido del archivo es: " + content),
                error -> System.err.println("Error al leer el archivo: " + error.getMessage())
        );
    }

    private static String readFile(String filename) throws Exception {
        Thread.sleep(1000);
        //throw new FileNotFoundException("Archivo no encontrado: " + filename);
       return "Contenido del archivo: " + filename;

    }

    // Método para obtener la hora actual
    private static String getCurrentTime() {
        return "Hora actual: " + System.currentTimeMillis();
    }
}