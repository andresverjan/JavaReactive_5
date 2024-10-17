package com.cedesistemas.sesion6;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class Sesion6Application {
    private static Logger LOGGER = LoggerFactory.getLogger(Sesion6Application.class);

    static Persona persona1 = new Persona("Juan", "Pérez", "123456789", 30, "Aries");

    static Persona persona2 = new Persona("María", "Gómez", "987654321", 25, "Virgo");

    static Persona persona3 = new Persona("Carlos", "Martínez", "555444333", 40, "Capricornio");

    static Persona persona4 = new Persona("Laura", "Rodríguez", "111222333", 35, "Tauro");

    static Persona persona5 = new Persona("Pedro", "Sánchez", "999888777", 28, "Leo");

    static Persona persona6 = new Persona("Ana", "Fernández", "666777888", 22, "Acuario");

    static Persona persona7 = new Persona("David", "López", "333222111", 45, "Cáncer");

    static Persona persona8 = new Persona("Sofía", "Díaz", "777666555", 32, "Géminis");

    static Persona persona9 = new Persona("Javier", "Hernández", "888999000", 27, "Escorpio");

    static Persona persona10 = new Persona("Elena", "García", "112233445", 33, "Libra");

    static Persona persona11 = new Persona("Pablo", "Muñoz", "554433221", 38, "Piscis");

    static Persona persona12 = new Persona("Rosa", "Jiménez", "998877665", 29, "Sagitario");

    static List<Persona> arrayPersons = new ArrayList<>(Arrays.asList(persona1, persona2, persona3, persona4, persona5, persona6, persona7,
            persona8, persona9, persona10, persona11, persona12));

    public static void main(String[] args) {
        SpringApplication.run(Sesion6Application.class, args);
        fluxPersonas().subscribe(persona -> LOGGER.info("Persona: {}", persona));
        filtrarMayoresDe30();
        mostrarNombresMayoresDe30();
        monoPrimeraPersona();
        mostrarNombreYApellido();
        agruparPersonasPorSigno().subscribe();
        obtenerPersonasPorEdad(30).subscribe();
        obtenerPersonasPorSigno("Escorpio").subscribe();
        obtenerPersonaPorTelefono("555444333").subscribe();
        agregarPersona(new Persona("Luis", "Gómez", "123456789", 30, "Aries")).subscribe();
        eliminarPersona(persona1).subscribe();
    }

    //1. Crear un Flux a partir de la lista de personas.
    public static Flux<Persona> fluxPersonas() {
        return Flux.just(persona1, persona2, persona3, persona4, persona5, persona6, persona7, persona8, persona9, persona10, persona11, persona12);
    }

    //2. Filtrar las personas mayores de 30 años utilizando filter().
    public static void filtrarMayoresDe30() {
        var persons = fluxPersonas().filter(persona -> persona.getEdad() > 30);
        persons.subscribe(persona -> LOGGER.info("Persona mayor de 30 años: {}", persona));
    }

    //3. Mostrar los nombres de las personas mayores de 30 años utilizando map(), subscribe() y filter()
    public static void mostrarNombresMayoresDe30() {
        var persons = fluxPersonas().filter(persona -> persona.getEdad() > 30).map(Persona::getNombre);
        persons.subscribe(nombre -> LOGGER.info("Nombre de la persona mayor de 30 años: {}", nombre));
    }

    //4. Crear un Mono con la primera persona de la lista.
    public static void monoPrimeraPersona() {
        var person = Flux.just(persona1).next();
        person.subscribe(persona -> LOGGER.info("Primera persona: {}", persona));
    }

    //5. Mostrar el nombre y apellido de la persona del Mono utilizando flatMap() y subscribe().
    public static void mostrarNombreYApellido() {
        var person = Flux.just(persona1).flatMap(persona -> Flux.just(persona.getNombre(), persona.getApellido()));
        person.subscribe(nombreApellido -> LOGGER.info("Nombre y apellido: {}", nombreApellido));
    }

    //6. Agrupar a las personas por signo del zodiaco utilizando groupBy(), flatMap() y collectList().
    // Luego, mostrar el signo y la cantidad de personas para cada grupo. (Hacer uso de peek)
    public static Flux<Persona> agruparPersonasPorSigno() {
       /* var persons = fluxPersonas()
                .groupBy(Persona::getSigno)
                .flatMap(group -> group.collectList())
                .flatMap(Flux::fromIterable)
                .collectList().block();
        persons.stream().peek(persona -> LOGGER.info("Signo: {}, Cantidad de personas: {}", persona.getSigno(), persons.size()));*/

        return Flux.fromIterable(arrayPersons)
                .groupBy(Persona::getSigno)
                .flatMap(group -> group.collectList())
                .flatMap(Flux::fromIterable)
                .doOnNext(persona -> LOGGER.info("Signo: {}, Cantidad de personas: {}", persona.getSigno(), arrayPersons.size()));
    }

    //7. Crear una función obtenerPersonasPorEdad(int edad) que reciba una edad como parámetro y devuelva un Flux con las personas que tengan esa edad. (Hacer uso de peek)
    public static Flux<Persona> obtenerPersonasPorEdad(int edad) {
        return Flux.fromIterable(arrayPersons)
                .filter(persona -> persona.getEdad() == edad)
                .doOnNext(persona -> LOGGER.info("Persona con edad {}: {}", edad, persona));
    }

    //8. Crear una función obtenerPersonasPorSigno(String signo) que reciba un signo del zodiaco como parámetro y devuelva un Flux con las personas que tengan ese signo. (Hacer uso de peek)
    public static Flux<Persona> obtenerPersonasPorSigno(String signo) {
        return Flux.fromIterable(arrayPersons)
                .filter(persona -> persona.getSigno().equals(signo))
                .doOnNext(persona -> LOGGER.info("Persona con signo {}: {}", signo, persona));
    }

    //9. Crear una función obtenerPersonaPorTelefono(String telefono) que reciba un número de teléfono como parámetro y
    // devuelva un Mono con la persona que tenga ese número de teléfono. Si no se encuentra, devolver un Mono vacío. (Hacer uso de peek)
    public static Mono<Persona> obtenerPersonaPorTelefono(String telefono) {
        return Flux.fromIterable(arrayPersons)
                .filter(persona -> persona.getTelefono().equals(telefono))
                .next()
                .doOnNext(persona -> LOGGER.info("Persona con teléfono {}: {}", telefono, persona));
    }

    //10. Crear una función agregarPersona(Persona persona) que reciba una persona como parámetro y
    // la agregue a la lista de personas. Devolver un Mono con la persona agregada. (Hacer uso de peek)
    public static Mono<Persona> agregarPersona(Persona persona) {
        return Mono.just(persona)
                .doOnNext(persona1 -> arrayPersons.add(persona1))
                .doOnNext(persona1 -> LOGGER.info("Persona agregada: {}", persona1));
    }

    //11. Crear una función eliminarPersona(Persona persona) que reciba una persona como parámetro y la elimine de la lista de personas. Devolver un Mono con la persona eliminada.
    public static Mono<Persona> eliminarPersona(Persona persona) {
        return Mono.just(persona)
                .doOnNext(persona1 -> arrayPersons.remove(persona1))
                .doOnNext(persona1 -> LOGGER.info("Persona eliminada: {}", persona1))
                .doOnNext(persona1 -> LOGGER.info("Arreglo con la persona eliminada: {}", arrayPersons));
    }
}
