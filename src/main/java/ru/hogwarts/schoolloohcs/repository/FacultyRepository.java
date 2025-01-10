package ru.hogwarts.schoolloohcs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.schoolloohcs.model.Faculty;

import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    List<Faculty> findByNameOrColorIgnoreCase(String name, String color);
}
