package curso.java.reactivo;

import io.r2dbc.spi.Connection;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@SpringBootApplication
public class ReactivoApplication {
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
	public static List<Persona> listaPersonas = List.of(persona1, persona2, persona3, persona4, persona5, persona6, persona7, persona8, persona9, persona10, persona11, persona12);
	public static void main(String[] args) {
		SpringApplication.run(ReactivoApplication.class, args);


		//Crear un Flux a partir de la lista de personas.
		System.out.println("--------------------------- PUNTO 1 ---------------------------");

		Flux<Persona> personas = Flux.fromIterable(listaPersonas);
		personas.subscribe(System.out::println);

		//Filtrar las personas mayores de 30 años utilizando filter().
		System.out.println("--------------------------- PUNTO 2 ---------------------------");
		personas
				.filter(persona -> persona.edad() > 30)
				.subscribe(System.out::println);


		//Mostrar los nombres de las personas mayores de 30 años utilizando map(), subscribe() y filter()
		System.out.println("--------------------------- PUNTO 3 ---------------------------");

		personas
				.filter(persona -> persona.edad() > 30)
				.map(Persona::nombre)
				.subscribe(System.out::println);
		//Crear un Mono con la primera persona de la lista.
		System.out.println("--------------------------- PUNTO 4 ---------------------------");

		Mono<Persona> primeraPersona = Mono.just(persona1);
		primeraPersona.subscribe(System.out::println);
		//Mostrar el nombre y apellido de la persona del Mono utilizando flatMap() y subscribe().
		System.out.println("--------------------------- PUNTO 5 ---------------------------");

		primeraPersona
				.flatMap(persona -> Mono.just(persona.nombre() + " " + persona.apellido()))
				.subscribe(System.out::println);
		//Agrupar a las personas por signo del zodiaco utilizando groupBy(), flatMap() y collectList(). Luego, mostrar el signo y la cantidad de personas para cada grupo. (Hacer uso de peek)
		System.out.println("--------------------------- PUNTO 6 ---------------------------");

		personas
				.groupBy(Persona::signoZodiacal)
				.flatMap(group -> group.collectList()
						.map(list -> group.key() + ": " + list.size()))
				.doOnNext(System.out::println)
				.subscribe();

		//Crear una función obtenerPersonasPorEdad(int edad) que reciba una edad como parámetro y devuelva un Flux con las personas que tengan esa edad. (Hacer uso de peek)
		System.out.println("--------------------------- PUNTO 7 ---------------------------");
		obtenerPersonasPorEdad(personas, 30).subscribe(System.out::println);
		//Crear una función obtenerPersonasPorSigno(String signo) que reciba un signo del zodiaco como parámetro y devuelva un Flux con las personas que tengan ese signo. (Hacer uso de peek)
		System.out.println("--------------------------- PUNTO 8 ---------------------------");
		obtenerPersonasPorSigno(personas, "Aries").subscribe(System.out::println);
		//Crear una función obtenerPersonaPorTelefono(String telefono) que reciba un número de teléfono como parámetro y devuelva un Mono con la persona que tenga ese número de teléfono. Si no se encuentra, devolver un Mono vacío. (Hacer uso de peek)
		System.out.println("--------------------------- PUNTO 9 ---------------------------");
		obtenerPersonaPorTelefono(personas, "12345678911").subscribe(System.out::println);
		//Crear una función agregarPersona(Persona persona) que reciba una persona como parámetro y la agregue a la lista de personas. Devolver un Mono con la persona agregada. (Hacer uso de peek)
		System.out.println("--------------------------- PUNTO 10 ---------------------------");
		Persona persona13 = new Persona("Rosa", "Jiménez", "998877665", 29, "Sagitario");
		agregarPersonaLista(persona13).subscribe(System.out::println);
		//Crear una función eliminarPersona(Persona persona) que reciba una persona como parámetro y la elimine de la lista de personas. Devolver un Mono con la persona eliminada.
		System.out.println("--------------------------- PUNTO 11 ---------------------------");
		eliminarPersona("998877665").subscribe(System.out::println);
	}

	public static Flux<Persona> obtenerPersonasPorEdad(Flux<Persona> persona, int edad) {
		return persona.filter(p -> p.edad() == edad);
	}

	public static Flux<Persona> obtenerPersonasPorSigno(Flux<Persona> persona, String signo) {
		return persona.filter(p -> p.signoZodiacal().equals(signo));
	}

	public static Flux<Persona> obtenerPersonaPorTelefono(Flux<Persona> persona, String telefono) {
		return persona.filter(p -> p.id()
						.equals(telefono))
				.switchIfEmpty(Mono.empty());
	}

	public static Mono<Persona> agregarPersonaLista(Persona personaAgregar){
		return Mono.just(personaAgregar)
				.map(persona -> {
					listaPersonas.add(persona);
					return persona;
				});
	}

	public static Mono<Persona> eliminarPersona( String idEliminar) {
		return Mono.just(idEliminar)
				.mapNotNull(id -> {
					Persona personaEliminar = listaPersonas.stream()
							.filter(p -> p.id().equals(id))
							.findFirst()
							.orElse(null);
					if (personaEliminar != null) {
						listaPersonas.remove(personaEliminar);
					}
					return personaEliminar;
				});
	}

}
