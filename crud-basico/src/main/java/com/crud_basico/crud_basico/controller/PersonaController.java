package com.crud_basico.crud_basico.controller;

import com.crud_basico.crud_basico.model.Persona;
import com.crud_basico.crud_basico.service.PersonaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/personas")
public class PersonaController {

    private final PersonaService personaService;

    @GetMapping
    public Flux<Persona> getPersonas() {
        return personaService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Persona> getPersonaById(@PathVariable Long id) {
        return personaService.findById(id)
                .doOnSubscribe(subscription -> System.out.println("Persona encontrada con id: " + id))
                .doOnError(error -> System.out.println("Persona no encontrada con id: " + id));
    }

    @PostMapping("/crear-persona")
    public Mono<Persona> savePersona(@RequestBody Persona persona) {
        return personaService.save(persona);
    }

    @DeleteMapping("/delete-persona/{id}")
    public Mono<Void> deletePersona(@PathVariable Long id){
        return personaService.deletePersonaById(id)
                .doOnSubscribe(subscription -> System.out.println("Persona eliminada " + id))
                .doOnError(error -> System.out.println("Error eliminando usuario con id: " + id))
                .then(Mono.empty());
    }

}
