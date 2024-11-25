package com.artifactory.crud.component;

import com.artifactory.crud.model.Estudiante;
import com.artifactory.crud.model.EstudianteMateria;
import com.artifactory.crud.model.Materia;
import com.artifactory.crud.service.EstudianteMateriaService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

@Component
public class EstudianteMateriaComponentHandler {
    private final EstudianteMateriaService estudianteMateriaService;


    public EstudianteMateriaComponentHandler(EstudianteMateriaService estudianteMateriaService) {
        this.estudianteMateriaService = estudianteMateriaService;
    }

    public Mono<ServerResponse> createEstudianteWithMaterias(ServerRequest request){
        return request.bodyToMono(EstudianteMateria.class)
                .flatMap(req -> {
                    Estudiante estudiante = new Estudiante();
                    estudiante.setNombre(req.getNombre());
                    estudiante.setEdad(req.getEdad());

                    List<Materia> materias = req.getMateria();

                    return estudianteMateriaService.saveEstudianteWithMaterias(estudiante, materias);
                })
                .flatMap(estudiante -> ServerResponse.ok().bodyValue(estudiante));
    }

}
