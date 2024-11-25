package com.artifactory.crud.service;

import com.artifactory.crud.model.Estudiante;
import com.artifactory.crud.model.EstudianteNota;
import com.artifactory.crud.repository.EstudianteRepository;
import com.artifactory.crud.repository.MateriaRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Service
public class EstudianteService {
    private final EstudianteRepository estudianteRepository;

    public EstudianteService(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    public Flux<EstudianteNota> getEstudianteAprobados(){
        return estudianteRepository.findEstudentsAprobados()
                .doOnNext(estudiante -> System.out.println(" Data " + estudiante) )
                .onErrorResume(e-> {
                            System.out.println("Error: " + e.getMessage());
                            return Flux.empty();
                        }
                );
    }

    public Flux<EstudianteNota> getEstudianteReprobados(){
        return estudianteRepository.findEstudentsReprobados()
                .doOnNext(estudiante -> System.out.println(" Data " + estudiante) )
                .onErrorResume(e-> {
                            System.out.println("Error: " + e.getMessage());
                            return Flux.empty();
                        }
                );
    }
    public Flux<Estudiante> getEstudiante(){
        return estudianteRepository.findAll()
                .doOnNext(estudiante -> System.out.println(" Data " + estudiante) )
                .onErrorResume(e-> {
                            System.out.println("Error: " + e.getMessage());
                            return Flux.empty();
                        }
                );
    }

}
