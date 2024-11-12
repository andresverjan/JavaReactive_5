package com.artifactory.crud.service;

import com.artifactory.crud.model.Estudiante;
import com.artifactory.crud.model.Person;
import com.artifactory.crud.repository.EstudianteRepository;
import com.artifactory.crud.repository.MateriaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Service
@AllArgsConstructor
public class EstudianteService {
    private final EstudianteRepository estudianteRepository;


    public Flux<Estudiante> getEstudiante(){
        return estudianteRepository.findAll()
                .doOnNext(estudiante -> System.out.println(" Data " + estudiante) );
    }

    public Mono<Estudiante> getEstudianteId(Long id) {
        if(id == null){
            return Mono.error(new IllegalArgumentException(" Id Cannot be null"));
        }
        return estudianteRepository.findById(id)
                .doOnNext(estudiante -> System.out.println(" Data getPersonByid " + estudiante) );
    }


}
