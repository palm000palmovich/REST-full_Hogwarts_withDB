package ru.hogwarts.schoolloohcs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import ru.hogwarts.schoolloohcs.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Long> {

    List<Student> findByAgeBetween(int min, int max);
}