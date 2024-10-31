package org.manejoerrores;

import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Persona> personaList = new ArrayList<>();
        personaList.add(new Persona("Juan", "Cali", new String[]{"Calle sur con 67", "Calle 34 56-90"}, 10000));
        personaList.add(new Persona("Pedro", "Bogotá", new String[]{"Calle 34 con 56", "Calle 78 90-12"}, 20000));
        personaList.add(new Persona("María", "Medellín", new String[]{"Calle 45 con 67", "Calle 23 45-67"}, -30000));
        personaList.add(new Persona("Luis", "Barranquilla", new String[]{"Calle 56 con 78", "Calle 12 34-56"}, 40000));
        personaList.add(new Persona("Ana", "Cartagena", new String[]{"Calle 67 con 89", "Calle 56 78-90"}, 50000));

        System.out.println("------------> EJERCICIO 1");
        Flux.fromIterable(personaList)
                .doOnNext(persona -> System.out.println("nombre persona: " + persona.getNombre()))
                .map(persona -> persona.getNombre().toUpperCase())
                .doOnNext(persona -> System.out.println("nombre persona UpperCase: " + persona))
                .flatMap(nombre -> Flux.just(nombre.split("")))
                .count()
                .subscribe(totalLetras -> System.out.println("Total letras procesadas: " + totalLetras));

        System.out.println("\n------------> EJERCICIO 2");
        Flux<String> ciudadesFlux = Flux.fromIterable(personaList)
                .map(Persona::getCiudad);
        ciudadesFlux.filter(ciudad -> ciudad.startsWith("B"))
                .distinct()
                .count()
                .subscribe(ciudadesDistintas -> System.out.println("Ciudades distintas: " + ciudadesDistintas));

        System.out.println("\n------------> EJERCICIO 3");
        Flux.fromIterable(personaList)
                .sort(Comparator.comparingDouble(Persona::getSaldo).reversed())
                .take(5)
                .subscribe(persona -> System.out.println("Cliente " + persona.getNombre() + " = " + persona.getSaldo()));

        System.out.println("\n------------> EJERCICIO 4");
        List<Transaccion> transaccionList = new ArrayList<>();
        transaccionList.add(new Transaccion("Trasaccion 1", 1500));
        transaccionList.add(new Transaccion("Trasaccion 2", 500));
        transaccionList.add(new Transaccion("Trasaccion 3", 2000));
        transaccionList.add(new Transaccion("Trasaccion 4", 750));
        transaccionList.add(new Transaccion("Trasaccion 5", 3000));

        double tasaCambio = 4250;

        Flux.fromIterable(transaccionList)
                .filter(transaccion -> transaccion.getMonto() > 1000)
                .map(transaccion -> transaccion.getMonto() * tasaCambio)
                .reduce(Double::sum)
                .subscribe(total -> System.out.println("Total en euros: " + String.format("%.0f", total)));

        System.out.println("\n------------> EJERCICIO 5");
        Flux.fromIterable(personaList)
                .flatMap(persona -> Flux.fromArray(persona.getDirecciones())
                        .flatMap(direccion -> obtenerEstadoCuenta(direccion)
                                .map(estadoCuenta -> "Cliente: " + persona.getNombre() + ", " + estadoCuenta)))
                .subscribe(System.out::println);


        System.out.println("\nGESTION DE ERRORES EN FLUJOS");
        System.out.println("------------> EJERCICIO 1: onErrorReturn");
        Flux.fromIterable(personaList)
                .map(persona -> {
                    if (persona.getSaldo() < 0) {
                        System.out.println("El cliente " + persona.getNombre() + " tiene saldo negativo: "+ persona.getSaldo());
                        throw new RuntimeException("Saldo negativo para " + persona.getNombre());
                    }
                    return persona;
                })
                .onErrorReturn(new Persona("Error", "Error", new String[]{}, 0))
                .subscribe(
                        persona -> System.out.println("Cliente: " + persona.getNombre() + ", Saldo: " + persona.getSaldo()),
                        error -> System.err.println("Error: " + error.getMessage())
                );

        System.out.println("\n------------> EJERCICIO 2: onErrorResume");
        Flux.fromIterable(personaList)
                .map(persona -> {
                    if (persona.getSaldo() < 0) {
                        throw new RuntimeException("Error al consultar el saldo para " + persona.getNombre());
                    }
                    return persona;
                })
                .onErrorResume(error -> {
                    System.err.println("Error: " + error.getMessage());
                    return Flux.fromIterable(personaList)
                            .map(persona -> new Persona(persona.getNombre(), persona.getCiudad(), persona.getDirecciones(), 10000));
                })
                .subscribe(
                        persona -> System.out.println("Cliente: " + persona.getNombre() + ", Saldo: " + persona.getSaldo())
                );

        System.out.println("\n------------> EJERCICIO 3: doOnError");
        Flux.fromIterable(personaList)
                .map(persona -> {
                    if (persona.getSaldo() < 0) {
                        throw new RuntimeException("Saldo negativo para " + persona.getNombre());
                    }
                    return persona;
                })
                .doOnError(error -> System.err.println("Error: " + error.getMessage()))
                .subscribe(
                        persona -> System.out.println("Cliente: " + persona.getNombre() + ", Saldo: " + persona.getSaldo())
                );

        System.out.println("\n------------> EJERCICIO 4: Uso combinado de errores");
        Flux.fromIterable(personaList)
                .map(persona -> {
                    if (persona.getSaldo() < 0) {
                        throw new IllegalArgumentException("Saldo negativo para " + persona.getNombre());
                    } else if (persona.getSaldo() > 30000) {
                        throw new IllegalStateException("Saldo demasiado alto para " + persona.getNombre());
                    }
                    return persona;
                })
                .onErrorResume(IllegalArgumentException.class, error -> {
                    System.err.println("Error: " + error.getMessage());
                    return Flux.just(new Persona("Error", "Desconocido", new String[]{}, 0));
                })
                .onErrorResume(IllegalStateException.class, error -> {
                    System.err.println("Error: " + error.getMessage());
                    return Flux.just(new Persona("Error", "Desconocido", new String[]{}, 30000));
                })
                .subscribe(
                        persona -> System.out.println("Cliente: " + persona.getNombre() + ", Saldo: " + persona.getSaldo())
                );

    }

    private static Flux<EstadoCuenta> obtenerEstadoCuenta(String direccion) {
        String estado = Math.random() > 0.5 ? "Activa" : "Inactiva";
        return Flux.just(new EstadoCuenta(direccion, estado));
    }
}