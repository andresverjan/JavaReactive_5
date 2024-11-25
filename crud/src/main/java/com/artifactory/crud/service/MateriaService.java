package com.artifactory.crud.service;

import com.artifactory.crud.model.Estudiante;
import com.artifactory.crud.model.Materia;
import com.artifactory.crud.repository.EstudianteRepository;
import com.artifactory.crud.repository.MateriaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MateriaService {
    private final MateriaRepository materiaRepository;

}
