package curso.java.reactivo.service;

import curso.java.reactivo.model.Estudiante;
import curso.java.reactivo.repository.EstudianteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class EstuadianteService {
    private final EstudianteRepository estudianteRepository;


    public Flux<Estudiante> findAll(){
        return estudianteRepository.findAll();
    }

    public Mono<Estudiante> findById(Long id){
        return estudianteRepository.findById(id);
    }

    public Mono<Estudiante> save(Estudiante estudiante){
        return estudianteRepository.save(estudiante);
    }

    public Mono<Void> deleteById(Long id){
        return estudianteRepository.deleteById(id);
    }


}
