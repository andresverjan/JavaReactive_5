package com.crudRF.crudRouterF.service;

import com.crudRF.crudRouterF.model.Estudiante;
import com.crudRF.crudRouterF.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EstudianteService {
    private final EstudianteRepository estudianteRepository;
    @Autowired
    public EstudianteService(EstudianteRepository estudianteRepository){
        this.estudianteRepository = estudianteRepository;
    }

    public Flux<Estudiante> getEstudiantes(){
        return estudianteRepository.findAll()
                .doOnNext(estudiante -> System.out.println("Data: " + estudiante));
    }
    public Mono<Estudiante> getEstudianteById(Long id){
        if (id ==null){
            return Mono.empty();
        }
        return estudianteRepository.findById(id)
                .doOnNext(person -> System.out.println("Data getEstudianteById: " + person));
    }
    public Mono<Estudiante> create(Estudiante estudiante){
        return estudianteRepository.save(estudiante);
    }
    public Mono<String> update(Estudiante estudiante){
        if(estudiante.getId() != null){
            return estudianteRepository.save(estudiante)
                    .doOnNext(p -> System.out.println("Data Updating: " + p))
                    .then(Mono.just("Estudiante update"));
        }else{
            return Mono.just("User is not present");
        }
    }
    public Mono<Void> deleteEstudianteById(Long id){
        if (id ==null){
            return Mono.error(new IllegalArgumentException("ID no puede ser nulo"));
        }
        return estudianteRepository.deleteById(id)
                .doOnNext(estudiante -> System.out.println("Data deletePersonById : " + estudiante));
    }
}
