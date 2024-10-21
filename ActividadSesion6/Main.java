package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Main {

    public static List<Persona> listaPersonas() {
        List<Persona> personas = new ArrayList<>();
        personas.add(new Persona("Juan", "Pérez", "123456789", 30, "Aries"));
        personas.add(new Persona("María", "Gómez", "987654321", 25, "Virgo"));
        personas.add(new Persona("Carlos", "Martínez", "555444333", 40, "Capricornio"));
        personas.add(new Persona("Laura", "Rodríguez", "111222333", 35, "Tauro"));
        personas.add(new Persona("Pedro", "Sánchez", "999888777", 28, "Leo"));
        personas.add(new Persona("Ana", "Fernández", "666777888", 22, "Acuario"));
        personas.add(new Persona("David", "López", "333222111", 45, "Cáncer"));
        personas.add(new Persona("Sofía", "Díaz", "777666555", 32, "Géminis"));
        personas.add(new Persona("Javier", "Hernández", "888999000", 27, "Escorpio"));
        personas.add(new Persona("Elena", "García", "112233445", 33, "Libra"));
        personas.add(new Persona("Pablo", "Muñoz", "554433221", 38, "Piscis"));
        personas.add(new Persona("Rosa", "Jiménez", "998877665", 29, "Sagitario"));
        return personas;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        List<Persona> personas = listaPersonas();

        //1. Crear un Flux a partir de la lista de personas.
        Flux<Persona> personaFlux = Flux.fromIterable(personas);

        //2 y 3. Filtrar las personas mayores de 30 años utilizando filter(), Mostrar los nombres de las personas
        // mayores de 30 años utilizando map(), subscribe() y filter().
        personaFlux
                .filter(persona -> persona.getEdad() > 30)
                .map(Persona::getNombre)
                .subscribe(nombre -> System.out.println("Persona mayor de 30 años: " + nombre));

        //4. Crear un Mono con la primera persona de la lista.
        Mono<Persona> primeraPersonaMono = Mono.just(personas.get(0));
        primeraPersonaMono
                .flatMap(persona -> Mono.just(persona.getNombre() + " " + persona.getApellido()))
                .subscribe(nombreCompleto -> System.out.println("Primera persona: " + nombreCompleto));

        //5. Mostrar el nombre y apellido de la persona del Mono utilizando flatMap() y subscribe().
        Mono<Persona> personaMono = Mono.just(personas.get(0));
        personaMono
                .flatMap(persona -> Mono.just(persona.getNombre() + " " + persona.getApellido()))
                .subscribe(nombreCompleto -> System.out.println("Nombre completo: " + nombreCompleto));

        //6. Agrupar a las personas por signo del zodiaco utilizando groupBy(), flatMap() y collectList().
        // Luego, mostrar el signo y la cantidad de personas para cada grupo. (Hacer uso de peek)
        personaFlux
                .groupBy(Persona::getSigno)
                .flatMap(grupo -> grupo
                        .collectList()
                        .doOnNext(personasPorSigno -> System.out.println("Signo: " + grupo.key() + ", Cantidad: "
                                + personasPorSigno.size()))
                )
                .subscribe();

        obtenerPersonasPorEdad(30).subscribe();
        obtenerPersonasPorSigno("Aries").subscribe();
        obtenerPersonaPorTelefono("123456789").subscribe();
        agregarPersona(new Persona("Luis", "González", "000111222", 50, "Virgo")).subscribe();
        eliminarPersona(personas.get(0)).subscribe();

    }

    //7. Crear una función obtenerPersonasPorEdad(int edad) que reciba una edad como parámetro y devuelva un Flux
    // con las personas que tengan esa edad. (Hacer uso de peek)
    public static Flux<Persona> obtenerPersonasPorEdad(int edad) {
        return Flux.fromIterable(listaPersonas())
                .filter(persona -> persona.getEdad() == edad)
                .doOnNext(persona -> System.out.println("Filtrando persona por edad: " + persona.getNombre()));
    }

    //8. Crear una función obtenerPersonasPorSigno(String signo) que reciba un signo del zodiaco como parámetro
    // y devuelva un Flux con las personas que tengan ese signo. (Hacer uso de peek)
    public static Flux<Persona> obtenerPersonasPorSigno(String signo) {
        return Flux.fromIterable(listaPersonas())
                .filter(persona -> persona.getSigno().equals(signo))
                .doOnNext(persona -> System.out.println("Filtrando persona por signo: " + persona.getNombre()));
    }

    //9. Crear una función obtenerPersonaPorTelefono(String telefono) que reciba un número de teléfono como
    // parámetro y devuelva un Mono con la persona que tenga ese número de teléfono. Si no se encuentra,
    // devolver un Mono vacío. (Hacer uso de peek)
    public static Mono<Persona> obtenerPersonaPorTelefono(String telefono) {
        return Flux.fromIterable(listaPersonas())
                .filter(persona -> persona.getTelefono().equals(telefono))
                .next()
                .doOnNext(persona -> System.out.println("Filtrando persona por teléfono: " + persona.getNombre()));
    }

    //10. Crear una función agregarPersona(Persona persona) que reciba una persona como parámetro y la agregue a
    // la lista de personas. Devolver un Mono con la persona agregada. (Hacer uso de peek)
    public static Mono<Persona> agregarPersona(Persona persona) {
        listaPersonas().add(persona);
        return Mono.just(persona)
                .doOnNext(p -> System.out.println("Persona agregada: " + p.getNombre()));
    }

    //11. Crear una función eliminarPersona(Persona persona) que reciba una persona como parámetro y la elimine
    // de la lista de personas. Devolver un Mono con la persona eliminada.
    public static Mono<Persona> eliminarPersona(Persona persona) {
        listaPersonas().remove(persona);
        return Mono.just(persona)
                .doOnNext(p -> System.out.println("Persona eliminada: " + p.getNombre()));
    }

}