package com.Flujos.ManipulacionFlujos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;

@SpringBootApplication
public class ManipulacionFlujos {

	private static List<Persona> personas = getPersonas();

	public static void main(String[] args) {

		SpringApplication.run(ManipulacionFlujos.class, args);

		System.out.println("Filtro de personas mayores a 60 años con Flux");
		Flux<Persona> personaFlux = Flux.fromIterable(personas);

		personaFlux
				.filter(persona -> persona.getEdad() > 60)
				.switchIfEmpty(p -> System.out.println("No se encontraron personas mayores a: "+60))
				.map(Persona::getNombre)
				.subscribe(
						nombre -> System.out.println("Persona mayor de 30 años: " + nombre),
						error -> System.err.println("Error: " + error.getMessage()),
						() -> System.out.println("Secuencia completada")
				);

		System.out.println("Uso de Mono");
		Mono<Persona> primeraPersonaMono = Mono.just(personas.get(0));
		primeraPersonaMono
				.flatMap(persona -> Mono.just(persona.getNombre() + " " + persona.getApellido()))
				.onErrorResume(error -> {
					System.out.println("Error al obtener la primer persona: " +
							error.getMessage());
					return Mono.just(String.valueOf(0));
					})
				.subscribe(
						nombreCompleto -> System.out.println("Primera persona: " + nombreCompleto),
						error -> System.err.println("Error: " + error.getMessage()),
						() -> System.out.println("Mono completado")
				);

		personaFlux
				.groupBy(Persona::getSigno)
				.flatMap(grupo -> grupo.collectList()
						.doOnNext(personasPorSigno -> {
							if ("Aries".equals(grupo.key())){
								throw new RuntimeException("Error simulando el error para el signo Aries");
							}
							System.out.println("Signo: " + grupo.key());
							System.out.println("Cantidad de personas: " + personasPorSigno.size());
						}))
				.onErrorResume(
						error -> {
							System.err.println("Error al procesar el grupo");
							return Mono.just(Collections.emptyList());
						}
				)
				.subscribe();
		System.out.println("\nPersonas de 60 años:");

		obtenerPersonasPorEdad(60).subscribe(persona ->
				System.out.println("Elemento recibido: " + persona));

		System.out.println("\nPersonas del signo 'Aris':");
		obtenerPersonasPorSigno("Aris").subscribe(persona ->
				System.out.println("Elemento recibido: " + persona));

		System.out.println("\nBuscando por telefono:");

		String telefonoBuscado = "12345679";
		obtenerPersonaPorTelefono(telefonoBuscado)
				.doOnNext(persona -> System.out.println("Persona encontrada: " + persona))
				.subscribe(
				persona -> System.out.println("Elemento recibido: " + persona),
				error -> System.err.println("Error: " + error.getMessage()),
				() -> System.out.println("No se encontró la persona.")
		);

		Persona nuevaPersona = new Persona("Error", "Torres", "333444555", 26, "Sagitario");
		agregarPersona(nuevaPersona)
				.doOnNext(persona -> System.out.println("Persona agregada: " + persona))
				.subscribe();

		eliminarPersona(personas.get(0))
				.doOnNext(persona -> System.out.println("Persona eliminada: " + persona))
				.subscribe(
						persona -> System.out.println("Elemento recibido: " + persona),
						error -> System.err.println("Error: " + error.getMessage()),
						() -> System.out.println("La persona no se encontró para eliminar.")
				);
	}


	public static Flux<Persona> obtenerPersonasPorEdad(int edad) {
		return Flux.fromIterable(getPersonas())
				.filter(persona -> persona.getEdad() == edad)
				.switchIfEmpty(
					p -> System.out.println("No se encontraron personas con la edad: " + edad)
				)
				.doOnNext(persona -> System.out.println("Filtrando persona: " + persona));
	}

	public static Flux<Persona> obtenerPersonasPorSigno(String signo) {
		return Flux.fromIterable(getPersonas())
				.filter(persona -> Objects.equals(persona.getSigno(), signo))
				.switchIfEmpty(
					p -> System.out.println("No se encontraron personas con el signo: "+ signo)
				)
				.doOnNext(persona -> System.out.println("Filtrando persona: " + persona));
	}

	public static Mono<Persona> obtenerPersonaPorTelefono(String telefono) {
		return Flux.fromIterable(personas)
				.filter(persona -> persona.getTelefono().equals(telefono))
				.singleOrEmpty()
				.doOnNext(persona -> System.out.println("Buscando persona: " + persona))
				.switchIfEmpty(
					Mono.fromRunnable(
							()-> System.out.println("Persona no encontrada con el teléfono: " + telefono)
					)
				);
	}

	public static Mono<Persona> agregarPersona(Persona persona) {
		return Mono.fromCallable(() -> {
					// Simula un error si el nombre de la persona es "Error"
					if ("Error".equals(persona.getNombre())) {
						throw new RuntimeException("Error simulado al agregar persona");
					}
					personas.add(persona);
					return persona;
				})
				.doOnNext(p -> System.out.println("Agregando persona: " + p))
				.onErrorResume(error -> {
					System.err.println("Error al agregar persona: " + error.getMessage());
					return Mono.empty();
				});
	}

	public static Mono<Persona> eliminarPersona(Persona persona) {
		return Mono.fromCallable(() -> {
					// Simula un error si el nombre de la persona es "Juan"
					if ("Juan".equals(persona.getNombre())) {
						throw new RuntimeException("Error simulado al eliminar persona con nombre Juan");
					}
					boolean removed = personas.remove(persona);
					if (!removed) {
						throw new RuntimeException("Persona no encontrada para eliminar");
					}
					return persona;
				})
				.doOnNext(p -> System.out.println("Eliminando persona: " + p))
				.onErrorResume(error -> {
					// Manejo del error
					System.err.println("Error al eliminar persona: " + error.getMessage());
					return Mono.empty();
				});
	}

	private static List<Persona> getPersonas() {
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
}
