package com.artifactory.r2dbc;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class R2dbcApplication {

	public static void main(String[] args) {

		//monoDefer();
		//monoCallable();
		//functions();



	}

	// Método para obtener la hora actual
	private static String getCurrentTime() {
		return "Hora actual: " + System.currentTimeMillis();
	}



	private static String readFile(String fileName) throws Exception {

		Thread.sleep(1000);
		return "Contenido del archivo " + fileName;
	}

	public static void monoDefer(){
		// Crear un Mono que emite el valor actual del sistema cuando se suscribe
		Mono<String> deferMono = Mono.defer(() -> Mono.just(getCurrentTime()));
		// Suscribirse al Mono y procesar el valor emitido
		System.out.println("defer");
		deferMono.subscribe(System.out::println);
		deferMono.subscribe(System.out::println);


		Mono<Double> mono = Mono.fromSupplier(() -> {
			double randomValue = Math.random();
			return randomValue;
		});
		System.out.println("suplier");
// Suscribirse al Mono para recibir y procesar el valor generado
		mono.subscribe(System.out::println);
		mono.subscribe(System.out::println);
	}

	public static void functions(){

		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
		List<Integer> doubledNumbers = numbers.stream()
				.peek(num -> System.out.println("Número original: " + num))
				.map(num -> num * 2)
				.peek(doubledNum -> System.out.println("Número doblado: " + doubledNum))
				.map(num -> num + 1)
				.peek(doubledNum2 -> System.out.println("Número + 1: " + doubledNum2))
				.toList();
		System.out.println("Números doblados: " + doubledNumbers);

	}

	public static void monoCallable(){
		Mono<String> fileContenMono = Mono.fromCallable(() ->readFile("example.txt"));

		fileContenMono.subscribe(content->System.out.println("Contenido del archivo: " + content ),
				error-> System.out.println("Error al ejecutar el archivo" + error.getMessage()));

	}
}
