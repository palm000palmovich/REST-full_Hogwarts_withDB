package ru.hogwarts.schoolloohcs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import ru.hogwarts.schoolloohcs.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
