package curso.java.reactivo.router_function.estudiante;

import curso.java.reactivo.model.Estudiante;
import curso.java.reactivo.service.EstuadianteService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
@Component
@AllArgsConstructor
public class EstudianteHandler {
    private final EstuadianteService estudianteService;

    public Mono<ServerResponse> findAll(ServerRequest request){
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(estudianteService.findAll(), Estudiante.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request){
        Long id = Long.valueOf(request.pathVariable("id"));
        return estudianteService.findById(id)
                .flatMap(estudiante -> ServerResponse.ok().body(Mono.just(estudiante), Estudiante.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> save(ServerRequest request){
        return request.bodyToMono(Estudiante.class)
                .flatMap(estudianteService::save)
                .flatMap(savedEstudiante -> ServerResponse.ok().body(Mono.just(savedEstudiante), Estudiante.class));
    }

    public Mono<ServerResponse> update(ServerRequest request){
        Long id = Long.valueOf(request.pathVariable("id"));
        return estudianteService.findById(id)
                .flatMap(existingEstudiante -> request.bodyToMono(Estudiante.class)
                        .map(estudiante -> {
                            if (estudiante.getNombre() != null) {
                                existingEstudiante.setNombre(estudiante.getNombre());
                            }
                            if (estudiante.getEdad() >= 0) {
                                existingEstudiante.setEdad(estudiante.getEdad());
                            }
                            return existingEstudiante;
                        })
                        .flatMap(estudianteService::save)
                        .flatMap(updatedEstudiante -> ServerResponse.ok().body(Mono.just(updatedEstudiante), Estudiante.class))
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deleteById(ServerRequest request){
        Long id = Long.valueOf(request.pathVariable("id"));
        return estudianteService.deleteById(id)
                .then(ServerResponse.ok().build());
    }





}
