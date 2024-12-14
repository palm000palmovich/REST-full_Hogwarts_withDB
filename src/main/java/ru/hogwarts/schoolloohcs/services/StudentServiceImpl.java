package ru.hogwarts.schoolloohcs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.schoolloohcs.model.Student;
import ru.hogwarts.schoolloohcs.repository.StudentRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    //Output students
    @Override
    public List<Student> getAllStudents(){
         return studentRepository.findAll();
    }

    //Creating students
    @Override
    public Student createStudent(Student student){
        return studentRepository.save(student);
    }

    //Find students by id
    @Override
    public Student findStudent(long id){
        return studentRepository.findById(id).orElse(null);
    }
    //Edit students
    @Override
    public Student editStudent(long id, Student student){
        Student studForChange = studentRepository.findById(id).orElse(null);
        if (studForChange != null){
            studForChange.setName(student.getName());
            studForChange.setAge(student.getAge());
            return studentRepository.save(studForChange);
        } return null;
    }
    //Delete students
    @Override
    public Student deleteStudent(Long id){
        Student stud = studentRepository.findById(id).get();
        if (stud != null){studentRepository.deleteById(id);}
        return stud;
    }

    //Find students by age
    @Override
    public List<Student> findByAge(int age){
        List<Student> studentsInAge = studentRepository.findAll()
                .stream()
                .filter(student -> student.getAge() == age)
                .collect(Collectors.toList());
        return studentsInAge;
    }

    //Full reset
    @Override
    public String clearDB(){
        studentRepository.deleteAll();
        return "База данных успешно очищена!";
    }

    //Students between min and max ages
    @Override
    public List<Student> studentsBemweenAges(int min, int max){
        return studentRepository.findByAgeBetween(min, max);
    }


}
