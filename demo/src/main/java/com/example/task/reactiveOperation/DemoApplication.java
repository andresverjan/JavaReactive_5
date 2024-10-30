package com.example.task.reactiveOperation;

import ch.qos.logback.core.encoder.JsonEscapeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;


@RequiredArgsConstructor
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		Operation operation = new Operation();

		//2 y 3. Filtrar las personas mayores de 30 años utilizando filter().
		System.out.println("\n1, 2 y 3. Nombres de Personas Mayores de 30" + "\n-------------------------");
		operation.filterOver30();

		//4.Crear un Mono con la primera persona de la lista.
		System.out.println("\n4. Mono con la primera persona" + "\n-------------------------");
		Mono<Person> primeraPersonaMono = operation.getFirstPerson();
		// Suscribirse al Mono e imprimir la persona
		primeraPersonaMono
				.subscribe(person -> System.out.println(person.getNombre() + " " + person.getApellido()));

		//5.Mostrar el nombre y apellido de la persona del Mono utilizando flatMap() y subscribe().
		System.out.println("\n5. Mono con flatMap" + "\n-------------------------");
		operation.showFirstPerson();

		System.out.println("\n6. Agrupar por signo" + "\n-------------------------");
		operation.groupBySign();


		System.out.println("\n7. Buscar persona por edad " + "\n-------------------------");
		operation.getPeopleByAge(55)
				.subscribe(person -> System.out.println(person.getNombre() + " " + person.getEdad()));


		System.out.println("\n8. Buscar persona por Signo " + "\n-------------------------");
		operation.getPeopleBysign("lilith")
				.subscribe(person -> System.out.println(person.getNombre() + " " + person.getSigno()));

		System.out.println("\n9. Buscar persona por Telefono " + "\n-------------------------");
		operation.getPeopleByPhoneNumber("30089111")
				.switchIfEmpty(Mono.error(new RuntimeException("No existe persona con el telefono ingresado")))
				.subscribe(
						person -> System.out.println(person.getNombre()),
						error -> System.err.println(error.getMessage()));


		System.out.println("\n10. Agregar persona " + "\n-------------------------");
		operation.addPerson(new Person("Laura", "Lopéz", "30089111896", 38, "Aries"))
				.subscribe(persona -> System.out.println("Persona agregada: " + persona));


		System.out.println("\n11. Eliminar persona " + "\n-------------------------");
		operation.deletePersona(new Person("luisa", "Perez", "123456789", 30, "Aries"))
				.subscribe(person -> System.out.println("Persona eliminada: " + person));


	}
}

