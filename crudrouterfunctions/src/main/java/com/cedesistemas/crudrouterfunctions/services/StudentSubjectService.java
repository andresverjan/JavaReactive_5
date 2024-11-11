package com.cedesistemas.crudrouterfunctions.services;

import com.cedesistemas.crudrouterfunctions.interfaces.StudentSubjectServiceInterface;
import com.cedesistemas.crudrouterfunctions.model.StudentSubject;
import com.cedesistemas.crudrouterfunctions.model.StudentSubjectGrade;
import com.cedesistemas.crudrouterfunctions.repository.CrudStudentSubjectRepository;
import com.cedesistemas.crudrouterfunctions.repository.StudentSubjectGradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class StudentSubjectService implements StudentSubjectServiceInterface {

    private final CrudStudentSubjectRepository studentSubjectRepository;
    private final StudentSubjectGradeRepository studentSubjectGrade;

    @Override
    public Mono<StudentSubject> saveStudentSubject(StudentSubject studentSubject) {
        return studentSubjectRepository.save(studentSubject);
    }

    @Override
    public Mono<StudentSubject> getStudentSubjectById(Integer id) {
        return studentSubjectRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("StudentSubject not found with id: " + id)));
    }

    @Override
    public Flux<StudentSubject> getAllStudentsSubjects() {
        return studentSubjectRepository.findAll()
                .switchIfEmpty(Flux.error(new RuntimeException("No students subjects found")));
    }

    @Override
    public Mono<StudentSubject> updateStudentSubjects(StudentSubject studentSubject) {
        return studentSubjectRepository.findAll()
                .switchIfEmpty(Mono.error(new RuntimeException("No students subjects found")))
                .then(studentSubjectRepository.save(studentSubject));
    }

    @Override
    public Mono<Void> deleteStudentSubjects(Integer id) {
        return studentSubjectRepository.findAll()
                .switchIfEmpty(Mono.error(new RuntimeException("No students subjects found")))
                .then(studentSubjectRepository.deleteById(id));
    }

    @Override
    public Flux<StudentSubjectGrade> getAllWithGradesAboveThree() {
        return studentSubjectGrade.findAllWithGradesAboveThree()
                .switchIfEmpty(Flux.error(new RuntimeException("No hay estudiantes con notas mayores a 3")));

    }

    @Override
    public Flux<StudentSubjectGrade> getAllWithGradesEqualsThree() {
        return studentSubjectGrade.findAllWithGradesEqualsThree()
                .switchIfEmpty(Flux.error(new RuntimeException("No hay estudiantes con notas mayores a 3")));
    }
}
