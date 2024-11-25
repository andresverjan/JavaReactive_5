package com.artifactory.crud.service;

import com.artifactory.crud.model.Estudiante;
import com.artifactory.crud.model.Materia;
import com.artifactory.crud.repository.EstudianteRepository;
import com.artifactory.crud.repository.MateriaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstudianteMateriaService {
    private final EstudianteRepository estudianteRepository;

    private final MateriaRepository materiaRepository;
    public EstudianteMateriaService(EstudianteRepository estudianteRepository, MateriaRepository materiaRepository) {
        this.estudianteRepository = estudianteRepository;
        this.materiaRepository = materiaRepository;
    }

    public Mono<Estudiante> saveEstudianteWithMaterias(Estudiante estudiante, List<Materia> materias) {
        return estudianteRepository.save(estudiante)
        .flatMap(savedEstudiante -> {
                    return Mono.when(
                            materias.stream()
                                    .map(materia -> {
                                        materia.setEstudiante(savedEstudiante.getId());
                                        return materiaRepository.save(materia);
                                    })
                                    .collect(Collectors.toList())
                    ).thenReturn(savedEstudiante);
               });
    }
}
