package com.artifactory.crud.component;

import com.artifactory.crud.model.Estudiante;
import com.artifactory.crud.model.EstudianteNota;
import com.artifactory.crud.service.EstudianteService;
import com.artifactory.crud.service.MateriaService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@AllArgsConstructor
public class EstudianteComponentHandler {


    private final EstudianteService estudianteService;


    public Mono<ServerResponse> getEstudianteAprobados(ServerRequest request){
        Flux<EstudianteNota> estudiante = estudianteService.getEstudianteAprobados();
        System.out.println(estudiante);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(estudiante, EstudianteNota.class);
    }
    public Mono<ServerResponse> getEstudianteReprobados(ServerRequest request){
        Flux<EstudianteNota> estudiante = estudianteService.getEstudianteReprobados();
        System.out.println(estudiante);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(estudiante, EstudianteNota.class);
    }

    public Mono<ServerResponse> getEstudiante(ServerRequest request){
        Flux<Estudiante> estudiante = estudianteService.getEstudiante();
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(estudiante, Estudiante.class);
    }

}
