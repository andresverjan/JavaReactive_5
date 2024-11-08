package curso.java.reactivo.service;

import curso.java.reactivo.model.Materia;
import curso.java.reactivo.repository.MateriaRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MateriaService {
    private final MateriaRepository materiaRepository;
    public MateriaService(MateriaRepository materiaRepository) {
        this.materiaRepository = materiaRepository;
    }

    public Flux<Materia> getMateria() {
        return materiaRepository.findAll();
    }

    public Mono<Materia> getMateriaById(Long id) {
        return materiaRepository.findById(id);
    }

    public Mono<Materia> updateMateria(Materia materia) {
        return materiaRepository.save(materia);
    }

    public Mono<Materia> addMateria(Materia materia) {
        return materiaRepository.save(materia);
    }

    public Mono<Void> deleteMateria(Long id) {
        return materiaRepository.deleteById(id);
    }
}
