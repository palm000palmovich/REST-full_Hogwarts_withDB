package ru.hogwarts.schoolloohcs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import ru.hogwarts.schoolloohcs.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Long> {

    List<Student> findByAgeBetween(int min, int max);

    @Query(value = "SELECT COUNT(DISTINCT(name)) FROM student", nativeQuery = true)
    int getCountOfStudents();

    @Query(value = "SELECT AVG(age) AS avarage_age FROM (SELECT DISTINCT id, name, age FROM student) AS  uniq_age", nativeQuery = true)
    float getAvgAge();

    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<Student> getLast5Students();
}