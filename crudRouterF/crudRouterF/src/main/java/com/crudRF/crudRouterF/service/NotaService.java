package com.crudRF.crudRouterF.service;

import com.crudRF.crudRouterF.model.Estudiante;
import com.crudRF.crudRouterF.model.Materia;
import com.crudRF.crudRouterF.model.Nota;
import com.crudRF.crudRouterF.repository.EstudianteRepository;
import com.crudRF.crudRouterF.repository.MateriaRepository;
import com.crudRF.crudRouterF.repository.NotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Service
public class NotaService {
    private final NotaRepository notaRepository;
    private final MateriaRepository materiaRepository;
    private final EstudianteRepository estudianteRepository;
    @Autowired
    public NotaService(NotaRepository notaRepository,
                       EstudianteRepository estudianteRepository,
                       MateriaRepository materiaRepository){
        this.notaRepository = notaRepository;
        this.materiaRepository = materiaRepository;
        this.estudianteRepository = estudianteRepository;
    }


    public Mono<Nota> create(Nota nota){
        return estudianteRepository.findById(nota.getEstudianteId())
            .switchIfEmpty(Mono.error(new Exception("Estudiante no encontrado")))
            .then(materiaRepository.findById(nota.getMateriaId()))
            .switchIfEmpty(Mono.error(new Exception("Materia no encontrada")))
            .then(notaRepository.save(nota));
    }

    public Flux<Estudiante> obtenerEstudiantesAprobadosPorMateria(Long materiaId) {
        return notaRepository.findAll()
                .filter(nota -> nota.getMateriaId().equals(materiaId))
                .groupBy(Nota::getEstudianteId)
                .flatMap(group -> group.collect(Collectors.averagingDouble(Nota::getValor))
                        .filter(promedio -> promedio > 3)
                        .flatMapMany(promedio -> estudianteRepository.findById(group.key())));
    }

    public Flux<Estudiante> obtenerEstudiantesReprobadosPorMateria(Long materiaId) {
        return notaRepository.findAll()
                .filter(nota -> nota.getMateriaId().equals(materiaId))
                .groupBy(Nota::getEstudianteId)
                .flatMap(group -> group.collect(Collectors.averagingDouble(Nota::getValor))
                        .filter(promedio -> promedio <= 3.0)
                        .flatMapMany(promedio -> estudianteRepository.findById(group.key())));
    }
}
