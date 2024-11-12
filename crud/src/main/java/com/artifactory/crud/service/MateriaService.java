package com.artifactory.crud.service;

import com.artifactory.crud.model.Estudiante;
import com.artifactory.crud.model.Materia;
import com.artifactory.crud.repository.MateriaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
@Service
@AllArgsConstructor
public class MateriaService {
    private final MateriaRepository materiaRepository;

    public Mono<Materia> create(Materia materia){

        return materiaRepository.save(materia);
    }
}
