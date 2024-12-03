package ru.hogwarts.schoolloohcs.services;

import ru.hogwarts.schoolloohcs.model.Student;

import java.util.List;

public interface StudentService {
    //Вывод всех студентов
    List<Student> getAllStudents();

    //Создание студента
    Student createStudent(Student student);

    //Поиск студента по id
    Student findStudent(long id);

    //Изменение студента
    Student editStudent(long id, Student student);

    //Удаление студента
    Student deleteStudent(Long id);

    //Поиск студентов по возрасту
    List<Student> findByAge(int age);
    //Очистка базы данных
    String clearDB();
}
