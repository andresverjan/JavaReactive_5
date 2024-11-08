package curso.java.reactivo.router_function.materia;

import curso.java.reactivo.model.Materia;
import curso.java.reactivo.service.MateriaService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
@Component
@AllArgsConstructor
public class MateriaHandler {
    private final MateriaService materiaService;

    public Mono<ServerResponse> getMateria(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(materiaService.getMateria(), Materia.class);
    }

    public Mono<ServerResponse> getMateriaById(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return materiaService.getMateriaById(id)
                .flatMap(materia -> ServerResponse.ok().body(Mono.just(materia), Materia.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> addMateria(ServerRequest request) {
        return request.bodyToMono(Materia.class)
                .flatMap(materiaService::addMateria)
                .flatMap(savedMateria -> ServerResponse.ok().body(Mono.just(savedMateria), Materia.class));
    }

    public Mono<ServerResponse> updateMateria(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return materiaService.getMateriaById(id)
                .flatMap(existingMateria -> request.bodyToMono(Materia.class)
                        .map(materia -> {
                            if (materia.getNombre() != null) {
                                existingMateria.setNombre(materia.getNombre());
                            }
                            return existingMateria;
                        })
                        .flatMap(materiaService::updateMateria)
                        .flatMap(updatedMateria -> ServerResponse.ok().body(Mono.just(updatedMateria), Materia.class))
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deleteMateria(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return materiaService.deleteMateria(id)
                .then(ServerResponse.ok().build());
    }
}
