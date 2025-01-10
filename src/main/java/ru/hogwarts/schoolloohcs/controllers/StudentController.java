package ru.hogwarts.schoolloohcs.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.schoolloohcs.model.Avatar;
import ru.hogwarts.schoolloohcs.model.Student;
import ru.hogwarts.schoolloohcs.services.AvatarService;
import ru.hogwarts.schoolloohcs.services.StudentService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(path = "/student")
public class StudentController {
    private final StudentService studentService;
    private final AvatarService avatarService;

    @Autowired
    public StudentController(StudentService studentService,
                             AvatarService avatarService) {
        this.studentService = studentService;
        this.avatarService = avatarService;
    }

    //Output of students
    @GetMapping
    public ResponseEntity<String> allStudents(){
        List<Student> students = studentService.getAllStudents();
        if (students.size() == 0){return ResponseEntity.badRequest().body("DB of students is empty!");}
        return ResponseEntity.ok(students.toString());
    }

    //Clear DB
    @GetMapping(path = "/clear")
    public void clearDB(){
        studentService.clearDB();
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
        if (stud == null){return ResponseEntity.notFound().build();}
        return ResponseEntity.ok(stud);
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

    //Between age
    @GetMapping("/ages/{min}/{max}")
    public ResponseEntity<List<Student>> minMaxAgeStudents(
            @PathVariable("min") int min,
            @PathVariable("max") int max
    ){
        List<Student> list = studentService.studentsBemweenAges(min, max);
        if (list == null){return ResponseEntity.notFound().build();}
        return ResponseEntity.ok(list);
    }

    //Upload
    @PostMapping(value = "/{id}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadCover(@PathVariable Long id,
                                              @RequestParam MultipartFile avatar) throws IOException{
        if (avatar.getSize() > 1024*300){return ResponseEntity.badRequest().body("File is too big!");}
        avatarService.uploadAvatar(id, avatar);
        return ResponseEntity.ok().build();
    }

    //Decreased avatar
    @GetMapping(value = "/{id}/avatar/preview")
    public ResponseEntity<byte[]> downloadAvatar(@PathVariable Long id){
        Avatar avatar = avatarService.findAvatar(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
    }

    //Original-size avatar
    @GetMapping(value = "/{id}/avatar")
    public void downloadAvatar(@PathVariable Long id,
                               HttpServletResponse response) throws IOException{
        Avatar avatar = avatarService.findAvatar(id);

        Path path = Path.of(avatar.getFilePath());

        try(
                InputStream is = Files.newInputStream(path);
                OutputStream os = response.getOutputStream();
                ){
            response.setStatus(200);
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int) avatar.getFileSize());
            is.transferTo(os);
        }
    }

    //Clear DB of avatars
    @GetMapping("/avatar/clear")
    public void clearAvatars(){
        avatarService.clearDBAvatar();
    }

}