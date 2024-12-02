package ru.hogwarts.schoolloohcs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.schoolloohcs.model.Student;
import ru.hogwarts.schoolloohcs.repository.StudentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;

    //Вывод всех студентов
    @Override
    public List<Student> getAllStudents(){
         return studentRepository.findAll();
    }

    //Создание студента
    @Override
    public Student createStudent(Student student){
        return studentRepository.save(student);
    }

    //Поиск студента по id
    @Override
    public Student findStudent(long id){
        return studentRepository.findById(id).orElse(null);
    }

    //Изменение студента
    @Override
    public Student editStudent(long id, Student student){
        Student studForChange = studentRepository.findById(id).orElse(null);
        if (studForChange != null){
            studForChange.setName(student.getName());
            studForChange.setAge(student.getAge());
            return studentRepository.save(studForChange);
        } return null;
    }

    //Удаление студента
    @Override
    public Student deleteStudent(Long id){
        Student stud = studentRepository.findById(id).get();
        if (stud != null){studentRepository.deleteById(id);}
        return stud;
    }

    //Поиск студентов по возрасту
    @Override
    public List<Student> findByAge(int age){
        List<Student> studentsInAge = studentRepository.findAll()
                .stream()
                .filter(student -> student.getAge() == age)
                .collect(Collectors.toList());
        return studentsInAge;
    }

    //Полная очистка таблицы

    @Override
    public String clearDB(){
        studentRepository.deleteAll();
        return "База данных успешно очищена!";
    }
}
