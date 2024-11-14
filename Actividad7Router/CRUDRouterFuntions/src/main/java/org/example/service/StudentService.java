package org.example.service;

import org.example.model.Student;
import org.example.repository.StudentRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Mono<Student> createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Flux<Student> getAllStudent() {
        return studentRepository.findAll();
    }

    public Mono<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public Mono<Student> updateStudent(Long id, Student student) {
        return studentRepository.findById(id)
                .flatMap(existingStudent -> {
                    existingStudent.setName(student.getName());
                    existingStudent.setAge(student.getAge());
                    existingStudent.setSubjects(student.getSubjects());
                    return studentRepository.save(existingStudent);
                });
    }

    public Mono<Void> deleteStudents(Long id) {
        return studentRepository.deleteById(id);
    }

    public Flux<Student> getStudentsAprobados() {
        return studentRepository.findAllByPromedioGreaterThan(3.0);
    }

    public Flux<Student> getStudentsReprobados() {
        return studentRepository.findAllByPromedioLessThanEqual(3.0);
    }
}
