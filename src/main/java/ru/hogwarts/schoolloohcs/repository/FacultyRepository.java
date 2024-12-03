package ru.hogwarts.schoolloohcs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.schoolloohcs.model.Faculty;

public interface FacultyRepository extends JpaRepository<Faculty, Long> { }
