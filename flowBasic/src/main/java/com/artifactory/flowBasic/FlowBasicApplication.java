package com.artifactory.flowBasic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class FlowBasicApplication {

	private static Flux<List<Persona>> flux;

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


		List<Persona> Personas = new ArrayList<Persona>();
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
		flux = Flux.just((Personas));
		//#2
		List<Persona> personas = (List<Persona>) Personas.stream()
				.filter(persona -> persona.getEdad()> 30)
				.collect(Collectors.toList());
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
		//Mono<Persona> personaMono =  Mono.just(persona1);
		//#5
		//personaMono
		//		.subscribe(p -> System.out.println("Persona1 : " + p.getNombre() + "*"+p.getApellido() ));

		//#6
		//flux.groupBy(p->p.stream().peek(per->System.out.println(per)));
		/*flux.groupBy(p -> p.stream().collect(Collectors.groupingBy(Persona::getSigno)))
				//.forEach(n-> print(n)));
				.flatMap(group -> group
						.collectList()
						.map(lis -> Map.entry(group.key(), lis))

				);

		flux.subscribe(num -> System.out.println("completada" +num.stream().forEach(p->System.out.println(p.getApellido()))));
*/


				//.flatMap(grupo -> grupo.collectList().map(lists -> "Signo" ))
				//.collect(Collectors.toList())
/*
		flux.subscribe(element -> System.out.println("persona: " +     element),
						error -> System.err.println("Error: " + error.getMessage()),
						() -> System.out.println("completada"));
*/
		//#7
		//Disposable resultado = obtenerPersonasPorEdad(38);
		//#8
		//Disposable resultadoSigno = obtenerPersonasPorSigno("Aries");
		//resultado.isDisposed(n -> System.out.println());
		//#9
		personaMono =  Mono.just(personas);

	}

	public static void print(Persona persona){
		System.out.println(persona.getNombre() + "-" +persona.getApellido() + "-" + persona.getEdad() + "-" + persona.getTelefono() + "-" + persona.getSigno());
	}
	//#7
	public static Disposable obtenerPersonasPorEdad(int edad){
		return flux.subscribe(p -> p.stream()
				.filter(per -> per.getEdad() == edad)
				.collect(Collectors.toList())
				.stream()
				.peek(pers->print(pers))
				.map(per -> per.getNombre()+"#"+per.getApellido()+"#"+per.getEdad()+"#"+per.getTelefono()+"#"+per.getSigno())
				.forEach(per ->System.out.println(per))
		);
	}
	//#8
	public static Disposable obtenerPersonasPorSigno(String signo){
		return flux.subscribe(p -> p.stream()
				.filter(per -> per.getSigno() == signo)
				.collect(Collectors.toList())
				.stream()
				.peek(pers->print(pers))
				.map(per -> per.getNombre()+"#"+per.getApellido()+"#"+per.getEdad()+"#"+per.getTelefono()+"#"+per.getSigno())
				.forEach(per ->System.out.println(per))
		);

	}
	//#9
	public static Disposable obtenerPersonaPorTelefono(String telefono){
		//mono
		return  personaMono.filter(p->p.stream().)
	}

}
