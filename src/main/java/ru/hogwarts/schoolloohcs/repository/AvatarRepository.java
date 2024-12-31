package ru.hogwarts.schoolloohcs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.schoolloohcs.model.Avatar;
import ru.hogwarts.schoolloohcs.model.Student;

import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {

    Optional<Avatar> findByStudentId(long studentId);

    void deleteByStudentId(long studentId);
}
