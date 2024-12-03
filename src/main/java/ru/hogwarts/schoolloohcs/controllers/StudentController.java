package ru.hogwarts.schoolloohcs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.schoolloohcs.model.Student;
import ru.hogwarts.schoolloohcs.services.StudentService;

import java.util.List;

@RestController
@RequestMapping(path = "/student")
public class StudentController {
    private final StudentService studentService;
    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    //Output of students
    @GetMapping
    public List<Student> allStudents(){
        return studentService.getAllStudents();
    }

    //Clear DB
    @GetMapping(path = "/clear")
    public String ckearDB(){
        return studentService.clearDB();
    }

    //GET
    @GetMapping("/{id}")
    public ResponseEntity<Student> findStudent(
            @PathVariable("id") long id){
        Student foundStudent = studentService.findStudent(id);
        if (foundStudent == null){return ResponseEntity.notFound().build();}
        return ResponseEntity.ok(foundStudent);
    }

    //POST
    @PostMapping
    public ResponseEntity<Student> createStud(
            @RequestBody Student student){
        return ResponseEntity.ok(studentService.createStudent(student));
    }

    //PUT
    @PutMapping("/{id}")
    public ResponseEntity<Student> editStud(
            @PathVariable("id") long id,
            @RequestBody Student student){
        Student stud = studentService.editStudent(id, student);
        if (stud == null){ResponseEntity.notFound().build();}
        return ResponseEntity.ok(studentService.editStudent(id, student));
    }

    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Student> delete(
            @PathVariable Long id){
        Student stud = studentService.deleteStudent(id);
        if (stud == null){
            return ResponseEntity.notFound().build();}
        return ResponseEntity.ok(stud);
    }

    //Students in age
    @GetMapping("{age}/age")
    public ResponseEntity<List<Student>> studInAge(
            @PathVariable("age") int age){
        List<Student> listOfStudsInAge = studentService.findByAge(age);
        if (listOfStudsInAge == null){return ResponseEntity.notFound().build();}
        return ResponseEntity.ok(listOfStudsInAge);
    }
 }
