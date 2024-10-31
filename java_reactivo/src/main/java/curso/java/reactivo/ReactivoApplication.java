package curso.java.reactivo;

import curso.java.reactivo.talleres.Persona;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@SpringBootApplication
public class ReactivoApplication {
	public static void main(String[] args) {
		SpringApplication.run(ReactivoApplication.class, args);
	}
}
