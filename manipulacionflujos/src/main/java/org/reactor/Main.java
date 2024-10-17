package org.reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {

    private static List<Persona> personas = new ArrayList<>();

    public static void main(String[] args) {



        Persona persona1 = new Persona("Juan", "Pérez", "123456789", 30, "Aries");
        Persona persona2 = new Persona("María", "Gómez", "987654321", 25, "Virgo");
        Persona persona3 = new Persona("Carlos", "Martínez", "555444333", 40, "Capricornio");
        Persona persona4 = new Persona("Laura", "Rodríguez", "111222333", 35, "Tauro");
        Persona persona5 = new Persona("Pedro", "Sánchez", "999888777", 28, "Leo");
        Persona persona6 = new Persona("Ana", "Fernández", "666777888", 22, "Acuario");
        Persona persona7 = new Persona("David", "López", "333222111", 45, "Cáncer");
        Persona persona8 = new Persona("Sofía", "Díaz", "777666555", 32, "Géminis");
        Persona persona9 = new Persona("Javier", "Hernández", "888999000", 27, "Escorpio");
        Persona persona10 = new Persona("Elena", "García", "112233445", 33, "Libra");
        Persona persona11 = new Persona("Pablo", "Muñoz", "554433221", 38, "Piscis");
        Persona persona12 = new Persona("Rosa", "Jiménez", "998877665", 29, "Sagitario");

        personas.add(persona1);
        personas.add(persona2);
        personas.add(persona3);
        personas.add(persona4);
        personas.add(persona5);
        personas.add(persona6);
        personas.add(persona7);
        personas.add(persona8);
        personas.add(persona9);
        personas.add(persona10);
        personas.add(persona11);
        personas.add(persona12);

        Flux<Persona> personasFlux = Flux.fromIterable(personas);

        personasFlux.filter(persona -> persona.getEdad() > 30)
                .map(Persona::getNombre)
                .subscribe(System.out::println);

        Mono<Persona> personaMono = Mono.just(personas.get(0));

        System.out.println("Personas mayores de 30 años:");
        personasFlux.filter(persona -> persona.getEdad() > 30)
                .map(Persona::getNombre)
                .subscribe(System.out::println);


        System.out.println("Nombre y apellido de la primera persona:");
        personaMono.flatMap(persona -> Mono.just(persona.getNombre() + " " + persona.getApellido()))
                .subscribe(System.out::println);

        System.out.println("Personas por signo del zodiaco:");
        personasFlux.groupBy(Persona::getSigno)
                .flatMap(group -> group.collectList()
                        .map(list -> Map.entry(group.key(), list.size())))
                .doOnNext(entry -> System.out.println("Signo: " + entry.getKey() + ", Cantidad: " + entry.getValue()))
                .subscribe();

        System.out.println("Personas por edad 30 años:");
        obtenerPersonasPorEdad(30).subscribe(persona -> System.out.println("Persona con 30 años: " + persona.getNombre()));

        System.out.println("Personas por signo (Aries):");
        obtenerPersonasPorSigno("Aries").subscribe(persona -> System.out.println("Persona con signo Aries: " + persona.getNombre()));

        System.out.println("Persona por teléfono (123456789):");
        obtenerPersonaPorTelefono("123456789").subscribe(persona -> System.out.println("Persona con teléfono 123456789: " + persona.getNombre()));

        System.out.println("Agregar nueva persona:");
        agregarPersona(new Persona("Nuevo", "Usuario", "000000000", 20, "Leo")).subscribe(persona -> System.out.println("Persona agregada: " + persona.getNombre()));

        System.out.println("Eliminar primera persona:");
        eliminarPersona(personas.get(0)).subscribe(persona -> System.out.println("Persona eliminada: " + persona.getNombre()));
    }

    public static Flux<Persona> obtenerPersonasPorEdad(int edad) {
        return Flux.fromIterable(personas)
                .filter(persona -> persona.getEdad() == edad)
                .doOnNext(persona -> System.out.println("Filtrando persona por edad: " + persona.getNombre()));
    }

    public static Flux<Persona> obtenerPersonasPorSigno(String signo) {
        return Flux.fromIterable(personas)
                .filter(persona -> persona.getSigno().equalsIgnoreCase(signo))
                .doOnNext(persona -> System.out.println("Filtrando persona por signo: " + persona.getNombre()));
    }

    public static Mono<Persona> obtenerPersonaPorTelefono(String telefono) {
        return Flux.fromIterable(personas)
                .filter(persona -> persona.getTelefono().equals(telefono))
                .next()
                .switchIfEmpty(Mono.empty())
                .doOnNext(persona -> System.out.println("Filtrando persona por teléfono: " + persona.getNombre()));
    }

    public static Mono<Persona> agregarPersona(Persona persona) {
        personas.add(persona);
        return Mono.just(persona)
                .doOnNext(p -> System.out.println("Agregando persona: " + p.getNombre()));
    }

    public static Mono<Persona> eliminarPersona(Persona persona) {
        personas.remove(persona);
        return Mono.just(persona)
                .doOnNext(p -> System.out.println("Eliminando persona: " + p.getNombre()));
    }
}