package curso.java.reactivo.router_function.registro;

import curso.java.reactivo.model.Estudiante;
import curso.java.reactivo.model.Registro;
import curso.java.reactivo.service.RegistroService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class RegistroHandler {
    private final RegistroService registroService;

    public Mono<ServerResponse>  registrarNota(ServerRequest request) {
        return request.bodyToMono(Registro.class)
                .flatMap(body -> registroService.registrarNota(body))
                .flatMap(savedRegistro -> ServerResponse.ok().body(Mono.just(savedRegistro), Registro.class));
    }
    public Mono<ServerResponse> getEstudiantesAprobados(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(registroService.obtenerEstudiantesAprobados(), Estudiante.class);
    }

    public Mono<ServerResponse> getEstudiantesReprobados(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(registroService.obtenerEstudiantesReprobados(), Estudiante.class);
    }

}
