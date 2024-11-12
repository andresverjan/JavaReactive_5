package com.crudRF.crudRouterF.service;

import com.crudRF.crudRouterF.model.Estudiante;
import com.crudRF.crudRouterF.model.Materia;
import com.crudRF.crudRouterF.repository.MateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Service
public class MateriaService {
    private final MateriaRepository materiaRepository;
    @Autowired
    public MateriaService(MateriaRepository materiaRepository){
        this.materiaRepository = materiaRepository;
    }

    public Flux<Materia> getMaterias(){
        return materiaRepository.findAll()
                .doOnNext(materia -> System.out.println("Data: " + materia));
    }
    public Mono<Materia> getMateriaById(Long id){
        if (id ==null){
            return Mono.empty();
        }
        return materiaRepository.findById(id)
                .doOnNext(materia -> System.out.println("Data getMateriaById: " + materia));
    }
    public Mono<Materia> create(Materia materia){
        return materiaRepository.save(materia);
    }

    public Mono<String> update(Materia materia){
        if(materia.getId() != null){
            return materiaRepository.save(materia)
                    .doOnNext(m -> System.out.println("Data Updating: " + m))
                    .then(Mono.just("Materia update"));
        }else{
            return Mono.just("Materia is not present");
        }
    }

    public Mono<Void> deleteMateriaById(Long id){
        if (id ==null){
            return Mono.error(new IllegalArgumentException("ID no puede ser nulo"));
        }
        return materiaRepository.deleteById(id)
                .doOnNext(materia -> System.out.println("Data deleteMateriaById : " + materia));
    }
}
