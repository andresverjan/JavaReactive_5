package com.crud_basico.crud_basico.service;

import com.crud_basico.crud_basico.model.Estudiante;
import com.crud_basico.crud_basico.repository.EstudianteRepository;
import com.crud_basico.crud_basico.repository.MateriaRepository;
import com.crud_basico.crud_basico.repository.NotaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.crud_basico.crud_basico.model.Materia;
import com.crud_basico.crud_basico.model.Nota;

@Service
@RequiredArgsConstructor
public class EstudianteService {

    private final EstudianteRepository estudianteRepository;
    private final MateriaRepository materiaRepository;
    private final NotaRepository notaRepository;

    public Flux<Estudiante> findAll() {
        return estudianteRepository.findAll()
                .doOnNext(persona -> System.out.println("Estudiantes encontrados: " + persona));
    }

    public Mono<Estudiante> findById(Long id) {
        return estudianteRepository.findById(id)
                .doOnNext(persona -> System.out.println("Estudiante encontrado con id: " + persona))
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Estudiante no encontrada con id: " + id)));
    }

    public Mono<Estudiante> save(Estudiante estudiante){
        return estudianteRepository.save(estudiante)
                .doOnNext(person -> System.out.println("Estudiante guardado con id: "+ person));
    }

    public Mono<Void> deleteById(Long id){
        if (id == null){
            return Mono.error(new IllegalArgumentException("Id Estudiante no puede ser null"));
        }
        return estudianteRepository.deleteById(id)
                .doOnNext(persona -> System.out.println("Estudiante eliminado con id: "+ persona));
    }

    public Flux<Materia> findMateriasByEstudianteId(Long estudianteId) {
        return materiaRepository.findMateriasByEstudianteId(estudianteId);
    }

    public Flux<Estudiante> reporteAprobados() {
        return findAll()
                .filterWhen(estudiante -> calcularPromedio(estudiante.getId())
                        .map(promedio -> promedio > 3));
    }

    public Flux<Estudiante> reporteReprobados() {
        return findAll()
                .filterWhen(estudiante -> calcularPromedio(estudiante.getId())
                        .map(promedio -> promedio <= 3));
    }

    private Mono<Double> calcularPromedio(Long estudianteId) {
        return findMateriasByEstudianteId(estudianteId)
                .flatMap(materia -> notaRepository.findNotasByMateriaId(materia.getId()))
                .map(Nota::getValor)
                .collectList()
                .map(notas -> notas.stream()
                        .mapToDouble(Double::doubleValue)
                        .average()
                        .orElse(0));
    }
}
