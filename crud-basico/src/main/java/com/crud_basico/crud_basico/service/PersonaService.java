package com.crud_basico.crud_basico.service;

import com.crud_basico.crud_basico.model.Persona;
import com.crud_basico.crud_basico.repository.PersonaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PersonaService {

    private final PersonaRepository personaRepository;

    public Flux<Persona> findAll() {
        return personaRepository.findAll()
                .doOnNext(persona -> System.out.println("Persona encontrada: " + persona));
    }

    public Mono<Persona> findById(Long id) {
        return personaRepository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Persona no encontrada con id: " + id)));
    }

    public Mono<Persona> save(Persona persona){
        return personaRepository.save(persona)
                .doOnNext(person -> System.out.println("Persona guardada con id: "+ person));
    }

    public Mono<Void> deletePersonaById(Long id){
        if (id == null){
            return Mono.error(new IllegalArgumentException("Id no puede ser null"));
        }
        return personaRepository.deleteById(id)
                .doOnNext(persona -> System.out.println("Persona eliminada con id: "+ persona));
    }
}
