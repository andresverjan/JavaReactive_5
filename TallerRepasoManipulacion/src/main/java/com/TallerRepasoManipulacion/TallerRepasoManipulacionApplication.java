package com.TallerRepasoManipulacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class TallerRepasoManipulacionApplication {

	public static void main(String[] args) {

	SpringApplication.run(TallerRepasoManipulacionApplication.class, args);

	List<Persona> personas = Arrays.asList(
			new Persona("Juan", "Madrid", new String[]{"Calle 1", "Calle 2"}, 100.0),
			new Persona("Ana", "Barcelona", new String[]{"Calle 3"}, 200.0),
			new Persona("Luis", "Sevilla", new String[]{"Calle 4", "Calle 5", "Calle 6"}, 150.0),
			new Persona("Maria", "Bilbao", new String[]{"Calle 7"}, 300.0),
			new Persona("Carlos", "Barcelona", new String[]{"Calle 8"}, 400.0)
	);

	//Ejercicio N°1
		System.out.println("Ejercicio N°1" );
	Flux<Persona> personaFlux = Flux.fromIterable(personas);
		personaFlux
				.map(persona -> persona.getNombre().toUpperCase())
			.flatMap(nombre -> Flux.fromArray(nombre.split("")))
			.doOnNext(letra -> System.out.println("Letra: " + letra))
			.count()  // Contar el número total de letras
			.doOnSuccess(count -> System.out.println("Cantidad total de letras procesadas: " + count))
			.subscribe();

	//Ejercicio N°2
		System.out.println("Ejercicio N°2" );
	Flux<String> ciudadFlux = Flux.fromIterable(personas)
			.map(Persona::getCiudad);

		ciudadFlux
				.filter(ciudad -> ciudad.startsWith("B"))
			.distinct()
			.doOnNext(ciudad -> System.out.println("Ciudad: " + ciudad))
			.count()
			.doOnSuccess(count -> System.out.println("Cantidad de ciudades únicas encontradas: " + count))
			.subscribe();

	//Ejercicio N°3
		System.out.println("Ejercicio N°3" );
	Flux<Persona> saldoFlux = Flux.fromIterable(personas);

		saldoFlux
				.sort(Comparator.comparingDouble(Persona::getSaldo).reversed())
			.take(5)
			.doOnNext(persona -> System.out.println("Cliente: " + persona.getNombre() + ", Saldo: " + persona.getSaldo()))
			.subscribe();

	//Ejercicio N°4
		System.out.println("Ejercicio N°4" );
	List<Transaccion> transacciones = Arrays.asList(
			new Transaccion("Compra 1", 1200),
			new Transaccion("Compra 2", 800),
			new Transaccion("Compra 3", 1500),
			new Transaccion("Compra 4", 5000),
			new Transaccion("Compra 5", 600)
	);
	double tasaConversion = 0.85;

	Flux<Transaccion> transaccionFlux = Flux.fromIterable(transacciones);

		transaccionFlux
				.filter(transaccion -> transaccion.getMonto() > 1000)
			.map(transaccion -> new Transaccion(
			transaccion.getNombre(),
					transaccion.getMonto() * tasaConversion))
			.doOnNext(transaccion -> System.out.println("Transacción convertida: " + transaccion))
			.map(Transaccion::getMonto)
			.reduce(Double::sum)
			.doOnSuccess(total -> System.out.println("Total en euros: " + total))
			.subscribe();

	//Ejercicio N°5
		System.out.println("Ejercicio N°5");
	Flux<Persona> estadoFlux = Flux.fromIterable(personas);

		estadoFlux
				.flatMap(persona -> Flux.fromArray(persona.getDirecciones())
			.flatMap(direccion -> obtenerEstadoCuenta(direccion)
							.map(estado -> "Cliente: " + persona.getNombre() + ", Dirección: " + direccion + ", Estado: " + estado)))  // Combinar el nombre, la dirección y el estado
			.doOnNext(System.out::println)
			.subscribe();

		System.out.println("Ejercicio manejo de errores");

		System.out.println("Ejercicio 1:");
	procesarSaldo(-50.0, "Cliente1").
	subscribe(saldo -> System.out.println("Saldo procesado: " + saldo));

		System.out.println("\nEjercicio 2:");
	consultarSaldo("Cliente2").
	subscribe(saldo -> System.out.println("Saldo consultado: " + saldo));

		System.out.println("\nEjercicio 3:");
	registroErrorSaldo(-20.0, "Cliente3").
	subscribe(saldo -> System.out.println("Saldo registrado: " + saldo));

		System.out.println("\nEjercicio 4:");
	procesarSaldoConExcepciones(-10.0, "Cliente4").
	subscribe(saldo -> System.out.println("Saldo procesado: " + saldo));
	procesarSaldoConExcepciones(1500.0, "Cliente5").
	subscribe(saldo -> System.out.println("Saldo procesado: " + saldo));

		System.out.println("\nEjercicio 5:");
	procesarFlujoIgnorandoErrores().
	subscribe(saldo -> System.out.println("Saldo procesado: " + saldo));

		System.out.println("Combinación de flujos de datos");

	// Ejercicio 1: Uso básico de merge
		System.out.println("Ejercicio 1:");
	Flux<Persona> flujo1 = Flux.just(personas.get(0), personas.get(1));
	Flux<Persona> flujo2 = Flux.just(personas.get(2), personas.get(3));

		Flux.merge(flujo1, flujo2)
			.subscribe(persona -> System.out.println("Cliente: " + persona.getNombre() + ", Ciudad: " + persona.getCiudad()));

	// Ejercicio 2: Uso básico de zip
		System.out.println("\nEjercicio 2:");
	Flux<Double> saldos1 = Flux.just(personas.get(0).getSaldo(), personas.get(1).getSaldo());
	Flux<Double> saldos2 = Flux.just(personas.get(2).getSaldo(), personas.get(3).getSaldo());

		Flux.zip(saldos1, saldos2, Double::sum)
			.subscribe(suma -> System.out.println("Suma del saldo emparejado: " + suma));

	// Ejercicio 3: Uso avanzado de combineLatest
		System.out.println("\nEjercicio 3:");
	Flux<String> ciudades = Flux.just(personas.get(0).getCiudad(), personas.get(1).getCiudad());
	Flux<String> estados = Flux.just("Activo", "Inactivo");

		Flux.combineLatest(ciudades, estados, (ciudad, estado) -> "Ciudad: " + ciudad + ", Estado: " + estado)
			.subscribe(resultado -> System.out.println(resultado));

	// Ejercicio 4: Uso básico de concat
		System.out.println("\nEjercicio 4:");
	Flux<String> nombres1 = Flux.just(personas.get(0).getNombre(), personas.get(1).getNombre());
	Flux<String> nombres2 = Flux.just(personas.get(2).getNombre(), personas.get(3).getNombre());

		Flux.concat(nombres1, nombres2)
			.subscribe(nombre -> System.out.println("Nombre: " + nombre));

	// Ejercicio 5: Uso avanzado de switchIfEmpty
		System.out.println("\nEjercicio 5:");
	Flux<Persona> flujoVacio = Flux.empty();

		flujoVacio
				.switchIfEmpty(Flux.just(new Persona("Cliente Generico", "Ciudad Desconocida", new String[]{}, 0.0)))
			.subscribe(persona -> System.out.println("Flujo alternativo: Cliente " + persona.getNombre() + ", Ciudad: " + persona.getCiudad()));


}
	public static Mono<String> obtenerEstadoCuenta(String direccion) {
		return Mono.fromCallable(() -> {
			Thread.sleep(new Random().nextInt(1000));
			return "Saldo Actualizado";
		});
	}

	public static Mono<Double> procesarSaldo(double saldo, String cliente) {
		return Mono.just(saldo)
				.flatMap(s -> {
					if (s < 0) {
						throw new RuntimeException("Saldo negativo detectado");
					}
					return Mono.just(s);
				})
				.onErrorReturn(e -> {
					System.out.println("Error en el cliente: " + cliente + " - " + e.getMessage());
					return true;
				}, 0.0);
	}

	// Ejercicio 2: Uso de onErrorResume
	public static Flux<Double> consultarSaldo(String cliente) {
		return Flux.just(100.0, -50.0, 75.0)
				.flatMap(saldo -> {
					if (saldo < 0) {
						return Mono.error(new RuntimeException("Saldo no disponible para " + cliente));
					}
					return Mono.just(saldo);
				})
				.onErrorResume(e -> {
					System.out.println("Error consultando saldo para el cliente: " + cliente + " - " + e.getMessage());
					return Flux.just(50.0);
				});
	}

	// Ejercicio 3: Manejo específico con doOnError
	public static Mono<Double> registroErrorSaldo(double saldo, String cliente) {
		return Mono.just(saldo)
				.flatMap(s -> {
					if (s < 0) {
						return Mono.error(new RuntimeException("Saldo inválido"));
					}
					return Mono.just(s);
				})
				.doOnError(e -> System.out.println("Error registrado para el cliente: " + cliente + " - Detalle: " + e.getMessage()))
				.onErrorReturn(0.0);
	}

	// Ejercicio 4: Uso combinado de errores
	public static Mono<Double> procesarSaldoConExcepciones(double saldo, String cliente) {
		return Mono.just(saldo)
				.flatMap(s -> {
					if (s < 0) {
						throw new IllegalArgumentException("Saldo negativo");
					} else if (s > 1000) {
						throw new IllegalStateException("Saldo excesivo");
					}
					return Mono.just(s);
				})
				.onErrorResume(e -> {
					if (e instanceof IllegalArgumentException) {
						System.out.println("Error: Saldo negativo para el cliente " + cliente);
						return Mono.just(0.0);
					} else if (e instanceof IllegalStateException) {
						System.out.println("Error: Saldo excesivo para el cliente " + cliente);
						return Mono.just(1000.0);
					}
					return Mono.error(e);
				});

	}

	// Ejercicio 5: Ignorar errores con onErrorContinue
	public static Flux<Double> procesarFlujoIgnorandoErrores() {
		return Flux.just(100.0, -30.0, 200.0, -50.0)
				.flatMap(saldo -> {
					if (saldo < 0) {
						return Mono.error(new RuntimeException("Saldo negativo detectado"));
					}
					return Mono.just(saldo);
				})
				.onErrorContinue((error, item) ->
						System.out.println("Error procesando saldo: " + item + " - " + error.getMessage()));
	}
}
