package org.example;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Main {
    public static void main(String[] args) {

        Flux<Persona> personas = Flux.just(
                new Persona("Juan", "Barranquilla", new String[]{"Calle 12"}, 1500.50),
                new Persona("Camila", "Medellin", new String[]{"Carrera 20"}, 2500.75),
                new Persona("Celeste", "Popayan", new String[]{"Calle 3"}, 3500.30),
                new Persona("Sebastian", "Bucaramanga", new String[]{"Carrera 44"}, -10),
                new Persona("Leonardo", "Bogota", new String[]{"Trasversal 5"}, 10.0),
                new Persona("Cindy", "Bucaramanga", new String[]{"Calle 16"}, 4500.00)
        );

        //Manipulación de Flujos de Datos
        System.out.println("\nManipulación de Flujos de Datos");

        //Ejercicio 1: Uso de map y flatMap
        System.out.println("\nEjercicio 1: Uso de map y flatMap");
        personas.map(persona -> persona.getNombre().toUpperCase())
                .flatMap(nombre -> Flux.fromArray(nombre.split("")))
                .doOnNext(System.out::println)
                .count()
                .subscribe(totalLetras -> System.out.println("Total de letras procesadas: " + totalLetras));


        //Ejercicio 2: Uso de filter y distinct
        System.out.println("\nEjercicio 2: Uso de filter y distinct");
        personas.map(Persona::getCiudad)
                .filter(ciudad -> ciudad.startsWith("B"))
                .distinct()
                .doOnNext(System.out::println)
                .count()
                .subscribe(total -> System.out.println("Total de ciudades únicas: " + total));

        //Ejercicio 3: Uso de sort y take
        System.out.println("\nEjercicio 3: Uso de sort y take");
        personas.sort((p1, p2) -> Double.compare(p2.getSaldo(), p1.getSaldo()))
                .take(5)
                .doOnNext(persona -> System.out.println(persona.getNombre() + " tiene un saldo de: " + persona.getSaldo()))
                .subscribe();

        //Ejercicio 4: Combinación de varios operadores
        System.out.println("\nEjercicio 4: Combinación de varios operadores");
        double tasaCambio = 0.85;
        personas.filter(persona -> persona.getSaldo() > 1000)
                .map(persona -> persona.getSaldo() * tasaCambio)
                .reduce(Double::sum)
                .subscribe(total -> System.out.println("Total en euros: " + total));

        //Ejercicio 5: Uso avanzado de flatMap
        System.out.println("\nEjercicio 5: Uso avanzado de flatMap");
        personas.flatMap(persona -> Flux.fromArray(persona.getDirecciones())
                        .flatMap(direccion -> obtenerEstadoCuenta(direccion)
                                .map(estado -> "Cliente: " + persona.getNombre() +
                                        " | Dirección: " + direccion + " | Estado: " + estado)))
                .doOnNext(System.out::println)
                .subscribe();


        //Gestión de Errores en Flujos
        System.out.println("\nGestión de Errores en Flujos");

        //Ejercicio 1: Uso básico de onErrorReturn
        System.out.println("\nEjercicio 1: Uso básico de onErrorReturn");
        personas.map(persona -> {
                    if (persona.getSaldo() < 0) {
                        throw new IllegalArgumentException("Saldo negativo para: " + persona.getNombre());
                    }
                    return persona;
                })
                .onErrorReturn(
                        IllegalArgumentException.class,
                        new Persona("Saldo Predeterminado", "N/A", new String[]{}, 0.0)
                )
                .doOnNext(persona -> System.out.println("Procesando a: " + persona.getNombre()))
                .subscribe(
                        persona -> System.out.println("Saldo procesado: " + persona),
                        error -> System.out.println("Error: " + error.getMessage())
                );

        //Ejercicio 2: Uso de onErrorResume
        System.out.println("\nEjercicio 2: Uso de onErrorResume");
        personas.map(persona -> {
                    if (persona.getSaldo() < 0) {
                        throw new RuntimeException("Error al consultar el saldo para: " + persona.getNombre());
                    }
                    return persona;
                })
                .onErrorResume(error -> {
                    System.out.println("Error encontrado: " + error.getMessage());
                    return Flux.just(
                            new Persona("Saldo Alternativo", "N/A", new String[]{}, 0.0)
                    );
                })
                .subscribe(persona -> System.out.println("Resultado: " + persona));

        //Ejercicio 3: Manejo específico con doOnError
        System.out.println("\nEjercicio 3: Manejo específico con doOnError");
        personas.map(persona -> {
                    if (persona.getSaldo() < 0) {
                        throw new IllegalArgumentException("Saldo negativo para: " + persona.getNombre());
                    }
                    return persona;
                })
                .doOnError(error -> System.out.println("Error encontrado: " + error.getMessage()))
                .subscribe(
                        persona -> System.out.println("Procesado: " + persona),
                        error -> System.out.println("Error en el procesamiento: " + error.getMessage())
                );

        //Ejercicio 4: Uso combinado de errores
        System.out.println("\nEjercicio 4: Uso combinado de errores");
        personas.map(persona -> {
                    if (persona.getSaldo() < 0) {
                        throw new IllegalArgumentException("Saldo negativo para: " + persona.getNombre());
                    }
                    if ("Madrid".equals(persona.getCiudad())) {
                        throw new RuntimeException("Ciudad prohibida para: " + persona.getNombre());
                    }
                    return persona;
                })
                .onErrorResume(IllegalArgumentException.class, e -> {
                    System.out.println("Manejo de IllegalArgumentException: " + e.getMessage());
                    return Flux.just(new Persona("Error en saldo", "N/A", new String[]{}, 0.0));
                })
                .onErrorResume(RuntimeException.class, e -> {
                    System.out.println("Manejo de RuntimeException: " + e.getMessage());
                    return Flux.just(new Persona("Error en ciudad", "N/A", new String[]{}, 0.0));
                })
                .subscribe(persona -> System.out.println("Resultado: " + persona));

        //Ejercicio 5: Ignorar errores con onErrorContinue
        System.out.println("\nEjercicio 5: Ignorar errores con onErrorContinue");
        personas.map(persona -> {
                    if (persona.getSaldo() < 0) {
                        throw new IllegalArgumentException("Saldo negativo para: " + persona.getNombre());
                    }
                    return persona;
                })
                .onErrorContinue((error, persona) ->
                        System.out.println("Error para " + ((Persona) persona).getNombre() + ": " + error.getMessage())
                )
                .subscribe(persona -> System.out.println("Procesado correctamente: " + persona));



        
        //Combinación de Flujos de Datos
        System.out.println("\nCombinación de Flujos de Datos");

        //Ejercicio 1: Uso básico de merge
        System.out.println("\nEjercicio 1: Uso básico de merge");
        Flux<Persona> flujo1 = Flux.just(
                new Persona("Carlos", "Madrid", new String[]{"Calle 1"}, 1500.50),
                new Persona("Ana", "Bilbao", new String[]{"Calle 2"}, 3500.30)
        );

        Flux<Persona> flujo2 = Flux.just(
                new Persona("Beatriz", "Barcelona", new String[]{"Calle 3"}, 2500.75),
                new Persona("David", "Sevilla", new String[]{"Calle 4"}, 500.10)
        );

        Flux.merge(flujo1, flujo2)
                .doOnNext(persona -> System.out.println("Cliente: " + persona.getNombre() + " | Ciudad: "
                        + persona.getCiudad()))
                .subscribe();

        //Ejercicio 2: Uso básico de zip
        System.out.println("\nEjercicio 2: Uso básico de zip");
        Flux.zip(flujo1, flujo2)
                .map(par -> par.getT1().getSaldo() + par.getT2().getSaldo())
                .doOnNext(saldoTotal -> System.out.println("Saldo total combinado: " + saldoTotal))
                .subscribe();

        //Ejercicio 3: Uso avanzado de combineLatest
        System.out.println("\nEjercicio 3: Uso avanzado de combineLatest");
        Flux<String> ciudades = Flux.just("Madrid", "Barcelona", "Bilbao");
        Flux<String> estados = Flux.just("Activo", "Inactivo");

        Flux.combineLatest(ciudades, estados, (ciudad, estado) -> "Ciudad: " + ciudad + " | Estado: " + estado)
                .doOnNext(System.out::println)
                .subscribe();

        //Ejercicio 4: Uso básico de concat
        System.out.println("\nEjercicio 4: Uso básico de concat");
        Flux.concat(flujo1, flujo2)
                .doOnNext(persona -> System.out.println("Cliente: " + persona.getNombre()))
                .subscribe();

        //Ejercicio 5: Uso avanzado de switchIfEmpty
        System.out.println("\nEjercicio 5: Uso avanzado de switchIfEmpty");
        Flux<Persona> flujoVacio = Flux.empty();
        flujoVacio.switchIfEmpty(Flux.just(new Persona("Cliente Predeterminado", "Desconocido",
                        new String[]{}, 0.0)))
                .doOnNext(persona -> System.out.println("No hay clientes, mostrando: " + persona.getNombre()))
                .subscribe();

    }


    private static Mono<String> obtenerEstadoCuenta(String direccion) {
        return Mono.just("Cuenta activa");
    }
}