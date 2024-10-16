package org.example;

import org.example.manipulacionFlujosBasico.Persona;
import org.example.manipulacionFlujosBasico.ReportSodiaco;
import org.w3c.dom.ls.LSOutput;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;
import reactor.core.publisher.Mono;

import java.io.ObjectInputFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class ManipulacionFlujosBasico
{
    public static List<Persona> load() {
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
        return List.of(persona1, persona2, persona3, persona4, persona5, persona6, persona7, persona8, persona9, persona10, persona11, persona12);
    }
    static Function<Integer, Flux<Persona>> obtenerPersonasPorEdad = edad ->{
            List<Persona> personas = load();
            Flux<Persona> personasEdad = Flux.fromIterable(personas)
            .filter(persona -> persona.getEdad() == edad);
        return personasEdad;
    };

    static Function<String, Flux<Persona>> obtenerPersonasPorSigno = signo ->{
        List<Persona> personas = load();
        Flux<Persona> personasSigno = Flux.fromIterable(personas)
                .filter(persona -> persona.getSigno().toUpperCase().equals(signo.toUpperCase()));
        return personasSigno;
    };

    static Function<String, Mono<Persona>> obtenerPersonasPorTelefono = telefono ->{
        List<Persona> personas = load();
        Mono<Persona> personasTelefono= Flux.fromIterable(personas)
                .filter(persona -> persona.getTelefono().toUpperCase().equals(telefono.toUpperCase()))
                .single()
                .doOnError(throwable -> System.out.println("No se encontró el teléfono"));
        return personasTelefono;
    };

    static Function<Persona, Mono<List<Persona>>> agregarPersona = persona -> {
        List<Persona> personas = load();
        List<Persona> personasNuevo = new ArrayList<>(personas);
        personasNuevo.add(persona);
        return Mono.just(personasNuevo);
    };

    static Function<Persona, Mono<List<Persona>>> eliminarPersona = persona -> {
        List<Persona> personas = load();
        List<Persona> personasNuevo = new ArrayList<>(personas);
        personasNuevo.remove(persona);
        return Mono.just(personasNuevo);
    };
    public static void main(String[] args) {



        System.out.println("Personas mayores de 30");
        List<Persona> personas = load();
        Flux<Persona> personasGT30 = Flux.fromIterable(personas)
                .filter(persona -> persona.getEdad() > 30)
                .map(personaGt30 -> {
                    return personaGt30;
                });
        personasGT30.subscribe(persona -> System.out.println(persona.getNombre()));

        System.out.println("Persona Mono");
        Mono<Persona> personaMono = Mono.just(personas)
                .flatMap(persona -> {
                    return Mono.just(persona.get(0));
                });
        personaMono.subscribe(persona -> System.out.println(persona.getNombre() + " " + persona.getApellido()));

        System.out.println("Personas agrupadas por signo zodiacal");
        Flux<ReportSodiaco> personasZodiaco = Flux.fromIterable(personas)
                .groupBy(Persona::getSigno)
                .flatMap(group -> group.collectList().map(list -> {
                    return new ReportSodiaco(group.key() ,list.size());
                }));

        personasZodiaco.subscribe(reportSodiaco -> System.out.println(reportSodiaco.getSigno() + " " + reportSodiaco.getCantidad()));

        System.out.println("Personas por edad");
        obtenerPersonasPorEdad.apply(30).subscribe(persona -> System.out.println(persona.getNombre()));

        System.out.println("Personas por signo");
        obtenerPersonasPorSigno.apply("Cáncer").subscribe(persona -> System.out.println(persona.getNombre()));

        System.out.println("Persona por telefono");
        obtenerPersonasPorTelefono.apply("123456789").subscribe(persona -> System.out.println(persona.getNombre()));

        System.out.println("Agregar persona");

        agregarPersona
                .apply(new Persona("Luis", "González", "000000000", 50, "Aries"))
                        .subscribe(LSOutput -> System.out.println(LSOutput));
        System.out.println("Eliminar persona");

        eliminarPersona
                .apply(new Persona("Pablo", "Muñoz", "554433221", 38, "Piscis"))
                .subscribe(LSOutput -> System.out.println(LSOutput));
    }




}
