package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.converter.json.GsonBuilderUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

		List<Persona> people = load();


		//1. Crear un Flux a partir de la lista de personas.
		// Crear un Flux a partir de la lista de personas. permite manejar la lista de forma reactiva
		Flux<Persona> peopleFlux = Flux.fromIterable(people);


		//2 y 3. Filtrar las personas mayores de 30 años utilizando filter().
		System.out.println("\n1, 2 y 3. Nombres de Personas Mayores de 30" + "\n-------------------------");
		peopleFlux.
				filter(persona -> persona.getEdad()>30)
				.map(persona -> persona.getNombre() + " " + persona.getApellido() + "  Edad: " +persona.getEdad())
				.subscribe(System.out::println);

		//4.Crear un Mono con la primera persona de la lista.
		// Crear un Mono con la primera persona de la lista
		System.out.println("\n4. Mono con la primera persona" + "\n-------------------------");
		Mono<Persona> primeraPersonaMono = Mono.just(people.get(0));

		// Suscribirse al Mono e imprimir la persona
		primeraPersonaMono
				.subscribe(persona -> System.out.println(persona.getNombre() + " " + persona.getApellido()));

		//5.Mostrar el nombre y apellido de la persona del Mono utilizando flatMap() y subscribe().
		System.out.println("\n5. Mono con flatMap" + "\n-------------------------");
		primeraPersonaMono
				.flatMap(persona -> Mono.just(persona.getNombre() + " " + persona.getApellido()))
				.subscribe(System.out::println); // Imprime el nombre y apellido de la primera persona




		System.out.println("\n6. Agrupar por signo" + "\n-------------------------");
		/*
		GroupedFlux -> Agrupamiento de un String y persona, donde el string es la clave que es el signo zodiacal
						Persona son los elementos agrupados
		groupBy -> Agrupa las personas por signo
		 */
		Flux<GroupedFlux<String, Persona>> peopleBySign = Flux.fromIterable(people)
				.groupBy(Persona::getSigno);

		// Procesar cada grupo
		/*
		flatMap -> se utiliza para transformar cada GroupedFlux en un nuevo flujo.
		groupedFlux -> Dentro de flatMap, groupedFlux es un GroupedFlux que representa un grupo de personas con el mismo signo.
		collectList() -> Recoge todas las personas de un grupo específico en una lista.
		doOnNext -> Imprime el signo del zodiaco y el número de personas en ese grupo.
		subscribe -> Se suscribe al flujo para que se ejecute y procese la información.
		 */
		peopleBySign.flatMap(groupedFlux ->
				groupedFlux.collectList()
						.doOnNext(personas ->
								System.out.println(groupedFlux.key() + " : " + personas.size()))
		).subscribe();


		System.out.println("\n7. Buscar persona por edad " + "\n-------------------------");
		getPeopleByAge.apply(25).subscribe(persona -> System.out.println(persona.getNombre() + " " + persona.getEdad()));


		System.out.println("\n8. Buscar persona por Signo " + "\n-------------------------");
		getPeopleBysign.apply("Escorpio").subscribe(persona -> System.out.println(persona.getNombre() + " " + persona.getSigno()));

		System.out.println("\n9. Buscar persona por Telefono " + "\n-------------------------");
		getPeopleByPhoneNumber.apply("123456789").subscribe(persona -> System.out.println(persona.getNombre()));

		System.out.println("\n10. Agregar persona " + "\n-------------------------");
		addPersona.apply(new Persona("Cristian", "Lopéz", "30089111896", 38, "Aries"))
				.doOnNext(persona -> System.out.println("Persona agregada: " + persona.get(persona.size()-1)))
				.subscribe();

		System.out.println("\n11. Eliminar persona " + "\n-------------------------");
		deletePersona(new Persona("Juan", "Perez", "123456789", 30, "Aries"))
				.doOnNext(persona -> System.out.println("Persona eliminada: " + persona))
				.subscribe();

	}

	public static List<Persona> load(){
		List<Persona> people =  new ArrayList<>();
		people.add(new Persona("Juan", "Perez", "123456789", 30, "Aries"));
		people.add(new Persona("María", "Gómez", "987654321", 25, "Virgo"));
		people.add(new Persona("Carlos", "Martínez", "555444333", 40, "Capricornio"));
		people.add(new Persona("Laura", "Rodríguez", "111222333", 35, "Tauro"));
		people.add(new Persona("Pedro", "Sánchez", "999888777", 28, "Leo"));
		people.add(new Persona("Ana", "Fernández", "666777888", 22, "Acuario"));
		people.add(new Persona("David", "López", "333222111", 45, "Cáncer"));
		people.add(new Persona("Sofía", "Díaz", "777666555", 32, "Géminis"));
		people.add(new Persona("Javier", "Hernández", "888999000", 27, "Escorpio"));
		people.add(new Persona("Elena", "García", "112233445", 33, "Libra"));
		people.add(new Persona("Pablo", "Muñoz", "554433221", 38, "Piscis"));
		people.add(new Persona("Rosa", "Jiménez", "998877665", 29, "Sagitario"));

		return people;
	}

	static Function<Integer, Flux<Persona>> getPeopleByAge = age ->{
		List<Persona> people = load();
        return Flux.fromIterable(people)
                .filter(persona -> persona.getEdad() == age);
	};

	static Function<String, Flux<Persona>> getPeopleBysign = sign ->{
		List<Persona> people = load();
		return Flux.fromIterable(people)
				.filter(persona -> Objects.equals(persona.getSigno(), sign));
	};

	static Function<String, Flux<Persona>> getPeopleByPhoneNumber = phoneNumber ->{
		List<Persona> people = load();
		return Flux.fromIterable(people)
				.filter(persona -> Objects.equals(persona.getTelefono(), phoneNumber))
				.map(persona -> persona);
	};

	static Function<Persona, Mono<List<Persona>>> addPersona = persona -> {
		List<Persona> people = load();
		people.add(persona);
		people.forEach(System.out::println);
		return Mono.just(people);
	};

	public static Mono<Persona> deletePersona(Persona persona){
		List<Persona> people = load();
		return Mono.create(sink -> {
			boolean removed = people.removeIf(p->p.getNombre().equals(persona.getNombre()));
			people.forEach(System.out::println);
			if(removed){
				sink.success(persona);
			}else{
				sink.error(new RuntimeException("Persona no encontrada: " + persona.getNombre()));
			}
		});
	}
}

