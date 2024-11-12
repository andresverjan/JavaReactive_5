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

    private final MateriaRepository materiaRepository;

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

    public Mono<Void> deleteEstudianteId(Long id){
        if(id == null){
            return Mono.empty();
        }
        return estudianteRepository.deleteById(id)
                .doOnNext(p-> System.out.println("Borrado :: " + p))
                .doOnError(e-> System.out.println(e));
    }

    public Mono<Estudiante> create(Estudiante estudiante){

        return estudianteRepository.save(estudiante);
    }

    public Mono<Estudiante> update(Estudiante estudiante){

        if(estudiante.getId() == null)
        {
            return Mono.error(new IllegalArgumentException(" Id Cannot be null"));
        }
        return estudianteRepository.save(estudiante);
    }
}
