package com.crud_basico.crud_basico.service;

import com.crud_basico.crud_basico.model.Materia;
import com.crud_basico.crud_basico.repository.MateriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MateriaService {

    private final MateriaRepository materiaRepository;

    public Flux<Materia> findAll() {
        return materiaRepository.findAll()
                .doOnNext(materia -> System.out.println("Materias encontradas: " + materia));
    }

    public Mono<Materia> findById(Long id) {
        return materiaRepository.findById(id)
                .doOnNext(materia -> System.out.println("Materia encontrada con id: " + materia))
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Materia no encontrada con id: " + id)));
    }

    public Mono<Materia> save(Materia materia) {
        return materiaRepository.save(materia)
                .doOnNext(materia1 -> System.out.println("Materia guardada con id: " + materia1));
    }

    public Mono<Void> deleteById(Long id){
        if (id == null){
            return Mono.error(new IllegalArgumentException("Id Materia no puede ser null"));
        }
        return materiaRepository.deleteById(id)
                .doOnNext(persona -> System.out.println("Materia eliminada con id: "+ persona));
    }
}
