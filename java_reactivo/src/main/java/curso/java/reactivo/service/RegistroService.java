package curso.java.reactivo.service;

import curso.java.reactivo.model.Estudiante;
import curso.java.reactivo.model.Registro;
import curso.java.reactivo.repository.EstudianteRepository;
import curso.java.reactivo.repository.RegistroRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class RegistroService {
    private final RegistroRepository registroRepository;
    private final EstudianteRepository estudiantesRepository;

    public Mono<Registro> registrarNota(Registro registro) {
        return registroRepository.save(registro);
    }

    public Flux<Estudiante> obtenerEstudiantesAprobados() {
        return registroRepository.findAll()
                .groupBy(Registro::getEstudianteId)
                .flatMap(group -> group.collectList()
                        .filter(lista -> lista.stream().mapToDouble(Registro::getNota).average().orElse(0.0) > 3)
                        .flatMapMany(estudianteId -> estudiantesRepository.findById(estudianteId.get(0).getEstudianteId())));
    }

    public Flux<Estudiante> obtenerEstudiantesReprobados() {
        return registroRepository.findAll()
                .groupBy(Registro::getEstudianteId)
                .flatMap(group -> group.collectList()
                        .filter(lista -> lista.stream().mapToDouble(Registro::getNota).average().orElse(0.0) <= 3)
                        .flatMapMany(estudianteId -> estudiantesRepository.findById(estudianteId.get(0).getEstudianteId())));
    }

}
