package ru.hogwarts.schoolloohcs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hogwarts.schoolloohcs.model.Faculty;
import ru.hogwarts.schoolloohcs.model.Student;
import ru.hogwarts.schoolloohcs.repository.AvatarRepository;
import ru.hogwarts.schoolloohcs.repository.StudentRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final AvatarRepository avatarRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, AvatarRepository avatarRepository) {
        this.studentRepository = studentRepository;
        this.avatarRepository = avatarRepository;
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
            studentRepository.save(studForChange);
            return student;
        } return null;
    }

    //Delete students
    @Transactional
    @Override
    public Student deleteStudent(Long id){
        Student stud = studentRepository.findById(id).orElse(null);
        if (stud != null){avatarRepository.deleteByStudentId(id);
        studentRepository.deleteById(id);}
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
    @Transactional
    @Override
    public void clearDB(){
        avatarRepository.deleteAll();
        studentRepository.deleteAll();
    }

    //Students between min and max ages
    @Override
    public List<Student> studentsBemweenAges(int min, int max){
        return studentRepository.findByAgeBetween(min, max);
    }


}
