package com.cedesistemas.crudrouterfunctions.interfaces;

import com.cedesistemas.crudrouterfunctions.model.StudentSubject;
import com.cedesistemas.crudrouterfunctions.model.StudentSubjectGrade;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StudentSubjectServiceInterface {
    Mono<StudentSubject> saveStudentSubject(StudentSubject studentSubject);

    Mono<StudentSubject> getStudentSubjectById(Integer id);

    Flux<StudentSubject> getAllStudentsSubjects();

    Mono<StudentSubject> updateStudentSubjects(StudentSubject studentSubject);

    Mono<Void> deleteStudentSubjects(Integer id);

    Flux<StudentSubjectGrade> getAllWithGradesAboveThree();

    Flux<StudentSubjectGrade> getAllWithGradesEqualsThree();
}
