package org.example.activitySix.service;

import org.example.activitySix.model.Estudiante;
import org.example.activitySix.model.EstudianteConMateriasYNotas;
import org.example.activitySix.model.Materia;
import org.example.activitySix.repository.EstudianteRepository;
import org.example.activitySix.repository.MateriaRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.stereotype.Service;

@Service
public class EstudianteService {
    private final EstudianteRepository estudianteRepository;
    private final MateriaRepository materiaRepository;

    public EstudianteService(EstudianteRepository estudianteRepository, MateriaRepository materiaRepository) {
        this.estudianteRepository = estudianteRepository;
        this.materiaRepository = materiaRepository;
    }

    // Crear estudiante
    public Mono<Estudiante> crearEstudiante(Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }

    // Registrar materia para un estudiante específico
    public Mono<Materia> registrarMateria(Long estudianteId, Materia materia) {
        return estudianteRepository.findById(estudianteId)
                .flatMap(estudiante -> {
                    materia.setEstudianteId(Double.valueOf(estudianteId));
                    return materiaRepository.save(materia);
                });
    }

    // Obtener todos los estudiantes con sus respectivas materias
    public Flux<EstudianteConMateriasYNotas> listarEstudiantesConMaterias() {
        return estudianteRepository.findAll()
                .flatMap(estudiante -> obtenerEstudianteConMateriasYNotas(estudiante.getId()));
    }

    // Obtener un estudiante específico por ID junto con sus materias
    public Mono<EstudianteConMateriasYNotas> obtenerEstudianteConMateriasYNotas(Long estudianteId) {
        return estudianteRepository.findById(estudianteId)
                .flatMap(estudiante -> {
                    EstudianteConMateriasYNotas estudianteDto = new EstudianteConMateriasYNotas();
                    estudianteDto.setId(estudiante.getId());
                    estudianteDto.setNombre(estudiante.getNombre());
                    estudianteDto.setEdad(estudiante.getEdad());

                    return materiaRepository.findByEstudianteId(estudianteId)
                            .collectList()
                            .map(materias -> {
                                estudianteDto.setMaterias(materias);
                                return estudianteDto;
                            });
                });
    }

    // Actualizar estudiante
    public Mono<Estudiante> actualizarEstudiante(Long id, Estudiante estudiante) {
        return estudianteRepository.findById(id)
                .flatMap(estudianteExistente -> {
                    estudianteExistente.setNombre(estudiante.getNombre());
                    estudianteExistente.setEdad(estudiante.getEdad());
                    return estudianteRepository.save(estudianteExistente);
                })
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Estudiante no encontrado")));
    }

    // Actualizar notas de una materia
    public Mono<Materia> actualizarNotasMateria(Long estudianteId, Long materiaId, Materia materiaActualizada) {
        return materiaRepository.findById(materiaId)
                .flatMap(materia -> {
                    if (!materia.getEstudianteId().equals(estudianteId)) {
                        return Mono.error(new IllegalArgumentException("La materia no pertenece al estudiante especificado"));
                    }

                    materia.setNota1(materiaActualizada.getNota1());
                    materia.setNota2(materiaActualizada.getNota2());
                    materia.setNota3(materiaActualizada.getNota3());
                    return materiaRepository.save(materia);
                })
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Materia no encontrada")));
    }

    // Eliminar estudiante
    public Mono<Void> eliminarEstudiante(Long estudianteId) {
        return estudianteRepository.findById(estudianteId)
                .flatMap(estudiante -> materiaRepository.deleteById(estudianteId)
                        .then(estudianteRepository.deleteById(estudianteId))
                );
    }

//    public Flux<Map<String, Object>> reporteEstudiantesAprobados() {
//        return estudianteRepository.findAll()
//                .flatMap(estudiante -> obtenerEstudianteConMateriasYNotas(estudiante.getId())
//                        .filter(this::aproboTodasLasMaterias)
//                        .map(this::crearReporteAprobados));
//    }

//    <<<<<<<<<<<<<<<<<<<<<private boolean aproboTodasLasMaterias(EstudianteConMateriasYNotas estudiante) {
//        return estudiante.getMaterias().stream()
//                .allMatch(this::materiaAprobada);
//    }
//
//    private boolean materiaAprobada(MateriasConNotas materia) {
//        var promedio = materia.getNotas().stream()
//                .mapToDouble(Double::doubleValue)
//                .average()
//                .orElse(0.0);
//        return promedio > 3.0;
//    }
//
//    private Map<String, Object> crearReporteAprobados(EstudianteConMateriasYNotas estudiante) {
//        List<Map<String, Double>> materiasAprobadas = estudiante.getMaterias().stream()
//                .map(materia -> {
//                    double promedio = materia.getNotas().stream()
//                            .mapToDouble(Double::doubleValue)
//                            .average()
//                            .orElse(0.0);
//                    Map<String, Double> materiaMap = new HashMap<>();
//                    materiaMap.put(materia.getNombre(), promedio);
//                    return materiaMap;
//                })
//                .collect(Collectors.toList());
//
//        Map<String, Object> reporte = new HashMap<>();
//        reporte.put(estudiante.getNombre(), materiasAprobadas);
//        return reporte;
//    }
//
//    // Reporte de estudiantes reprobados
//    public Flux<Map<String, Object>> reporteEstudiantesReprobados() {
//        return estudianteRepository.findAll()
//                .flatMap(estudiante -> obtenerEstudianteConMateriasYNotas(estudiante.getId())
//                        .filter(this::reproboTodasLasMaterias)
//                        .map(this::crearReporteReprobados));
//    }
//
//    private boolean reproboTodasLasMaterias(EstudianteConMateriasYNotas estudiante) {
//        return estudiante.getMaterias().stream()
//                .allMatch(this::materiaReprobada);
//    }
//
//    private boolean materiaReprobada(MateriasConNotas materia) {
//        var promedio = materia.getNotas().stream()
//                .mapToDouble(Double::doubleValue)
//                .average()
//                .orElse(0.0);
//        return promedio <= 3.0;
//    }
//
//    private Map<String, Object> crearReporteReprobados(EstudianteConMateriasYNotas estudiante) {
//        List<Map<String, Double>> materiasReprobadas = estudiante.getMaterias().stream()
//                .filter(this::materiaReprobada)
//                .map(materia -> {
//                    double promedio = materia.getNotas().stream()
//                            .mapToDouble(Double::doubleValue)
//                            .average()
//                            .orElse(0.0);
//                    Map<String, Double> materiaMap = new HashMap<>();
//                    materiaMap.put(materia.getNombre(), promedio);
//                    return materiaMap;
//                })
//                .collect(Collectors.toList());
//
//        Map<String, Object> reporte = new HashMap<>();
//        reporte.put(estudiante.getNombre(), materiasReprobadas);
//        return reporte;
//    }>>>>>>>>>>>>>>>>>>>>>
}