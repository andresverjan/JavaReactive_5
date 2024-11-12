package com.crud_basico.crud_basico.service;

import com.crud_basico.crud_basico.model.Nota;
import com.crud_basico.crud_basico.repository.NotaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class NotaService {
    private final NotaRepository notaRepository;

    public Flux<Nota> findAll() {
        return notaRepository.findAll()
                .doOnNext(materia -> System.out.println("Nota encontradas: " + materia));
    }

    public Mono<Nota> findById(Long id) {
        return notaRepository.findById(id)
                .doOnNext(materia -> System.out.println("Nota encontrada con id: " + materia))
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Nota no encontrada con id: " + id)));
    }

    public Mono<Nota> save(Nota materia) {
        return notaRepository.save(materia)
                .doOnNext(materia1 -> System.out.println("Nota guardada con id: " + materia1));
    }

    public Mono<Void> deleteById(Long id){
        if (id == null){
            return Mono.error(new IllegalArgumentException("Id Nota no puede ser null"));
        }
        return notaRepository.deleteById(id)
                .doOnNext(persona -> System.out.println("Nota eliminada con id: "+ persona));
    }
}
