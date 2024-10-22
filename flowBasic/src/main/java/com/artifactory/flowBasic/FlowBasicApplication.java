package com.artifactory.flowBasic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class FlowBasicApplication {

	private static List<Persona> Personas = new ArrayList<Persona>();
	private static Flux<Persona> flux;

	private static Mono<List<Persona>> personaMono;

	public static void main(String[] args) {

		SpringApplication.run(FlowBasicApplication.class, args);

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


		Personas.add(persona1);
		Personas.add(persona2);
		Personas.add(persona3);
		Personas.add(persona4);
		Personas.add(persona5);
		Personas.add(persona6);
		Personas.add(persona7);
		Personas.add(persona8);
		Personas.add(persona9);
		Personas.add(persona10);
		Personas.add(persona11);
		Personas.add(persona12);

		//#1
		Flux<Persona> flux = Flux.fromIterable(Personas);
		//#2
/*
		flux.filter(persona -> persona.getEdad() > 30)
				.map(Persona::getNombre)
				.subscribe(p -> System.out.println("Persona " + p));
*/

		//personas.forEach(n-> print(n));
		//#3
		/*
		flux.subscribe(p -> p.stream()
						.filter(per -> per.getEdad()>30)
						.collect(Collectors.toList())
						.stream()
						.map(per -> per.getNombre()+"#"+per.getApellido()+"#"+per.getEdad()+"#"+per.getTelefono()+"#"+per.getSigno())
						.forEach(per ->System.out.println(per))
						);
		*/
		//#4
/*
		Mono<Persona> personaMono =  Mono.just(persona1);
		personaMono
				.flatMap(persona -> Mono.just(persona.getNombre() + " " + persona.getApellido()))
				.subscribe(p -> System.out.println("persona mono " + p));
*/
		//#5
		/*
		personaMono.flatMap(persona -> Mono.just(persona.getNombre() + " " + persona.getApellido()))
				.subscribe(p -> System.out.println("Persona1 : " + p ));
*/
		//#6
		/*
		flux.groupBy(Persona::getSigno)
 				.flatMap(grupo -> grupo
				.collectList()
				.doOnNext(personasPorSigno -> System.out.println("Signo: " + grupo.key() + ", Cantidad: "
						+ personasPorSigno.size()))
		)
				.subscribe();
*/

		//#7
		//Flux<Persona> resultado = obtenerPersonasPorEdad(38);
		//#8
		//Flux<Persona> resultado =obtenerPersonasPorSigno("Aries");
		//#9
		//obtenerPersonaPorTelefono("554433221").subscribe();
		//#10
		//agregarPersona(new Persona("Fernando", "Montaño", "23566898989", 20, "Libra")).subscribe();
		//#11
		eliminarPersona(personas.get(0)).subscribe();
	}

	//#7

	public static Flux<Persona> obtenerPersonasPorEdad(int edad){
		return  Flux.fromIterable(Personas)
				.filter(per -> per.getEdad() == (edad))
				.doOnNext(persona -> System.out.println("Nombre " + persona.getNombre()));

	}


	//#8
	public static Flux<Persona> obtenerPersonasPorSigno(String signo){
		return Flux.fromIterable(Personas)
				.filter(per -> per.getSigno() == (signo))
				.doOnNext(persona -> System.out.println("Nombre " + persona.getNombre()));
	}
	//#9
	public static Mono<Persona> obtenerPersonaPorTelefono(String telefono){
		//mono
		return Flux.fromIterable(Personas)
				.filter(p -> p.getTelefono().contains(telefono))
				.next()
				.doOnNext(persona -> System.out.println("Filtrando persona por teléfono: " + persona.getNombre()));
	}

	public static Mono<Persona> agregarPersona(Persona persona){
		Personas.add(persona);
		return Mono.just(persona)
				.doOnNext(p -> System.out.println("Nombre agregada: " + p.getNombre()));
	}

	public static Mono<Persona> eliminarPersona(Persona persona){
		Personas.remove(persona);
		return Mono.just(persona)
				.doOnNext(p -> System.out.println("Nombre Eliminado: " + p.getNombre()));
	}
}
