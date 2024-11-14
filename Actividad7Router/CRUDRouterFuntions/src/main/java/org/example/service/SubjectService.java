package org.example.service;

import org.example.model.Student;
import org.example.model.Subject;
import org.example.repository.SubjectRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final StudentService studentService;

    public SubjectService(SubjectRepository subjectRepository, StudentService studentService) {
        this.subjectRepository = subjectRepository;
        this.studentService = studentService;
    }

    public Mono<Subject> createSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    public Flux<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    public Mono<Subject> getSubjectById(Long id) {
        return subjectRepository.findById(id);
    }

    public Mono<Subject> updateSubject(Long id, Subject subject) {
        return subjectRepository.findById(id)
                .flatMap(existingSubject -> {
                    existingSubject.setName(subject.getName());
                    existingSubject.setNote(subject.getNote());
                    return subjectRepository.save(existingSubject);
                });
    }

    public Mono<Void> deleteSubject(Long id) {
        return subjectRepository.deleteById(id);
    }

    public Mono<Student> addSubjectToStudent(Long studentId, Subject subject) {
        return studentService.getStudentById(studentId)
                .flatMap(student -> {
                    student.getSubjects().add(subject);
                    return studentService.updateStudent(studentId, student);
                });
    }
}
