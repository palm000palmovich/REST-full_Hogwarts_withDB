package ru.hogwarts.schoolloohcs;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.schoolloohcs.controllers.StudentController;
import ru.hogwarts.schoolloohcs.model.Student;
import ru.hogwarts.schoolloohcs.repository.AvatarRepository;
import ru.hogwarts.schoolloohcs.repository.StudentRepository;
import ru.hogwarts.schoolloohcs.services.AvatarService;
import ru.hogwarts.schoolloohcs.services.StudentServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
public class StudentTestWithMock {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StudentRepository studentRepository;
    @MockBean
    private AvatarRepository avatarRepository;
    @SpyBean
    private StudentServiceImpl studentService;
    @SpyBean
    private AvatarService avatarService;

    private StudentController studentController;

    private Student student = new Student();
    private JSONObject jsonObject = new JSONObject();

    @BeforeEach
    public void setUp() throws Exception{
        final Long id = 1L;
        final String name = "Man";
        final int age = 38;

        this.jsonObject.put("id", id);
        this.jsonObject.put("name", name);
        this.jsonObject.put("age", age);

        student.setId(id);
        student.setName(name);
        student.setAge(age);
    }

    @Test
    public void saveStudentsTest() throws Exception{

        when(studentRepository.save(any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/student")
                .content(jsonObject.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student.getId()))
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()));

    }

    @Test
    public void getAllStudentsTest() throws Exception{
        when(studentService.getAllStudents()).thenReturn(List.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student")
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }

    @Test
    public void getAllStudentsIfDBisEmpty() throws Exception{
        List<Student> emptyListOfStudents = new ArrayList<>();

        when(studentService.getAllStudents()).thenReturn(emptyListOfStudents);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> result.getResponse().getContentAsString()
                        .contains("DB of students is empty!"));
    }

    @Test
    public void getStudentsFindByIdTest() throws Exception{

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/" + student.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student.getId()))
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()));
    }

    @Test
    public void studentIsNotFoundTest() throws Exception{
        when(studentService.findStudent(any(Long.class))).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/" + student.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    public void editStudentTest() throws Exception{
        final long newId = 1L;
        final String newName = "Max";
        final int newAge = 21;

        Student newStudent = new Student();
        newStudent.setId(newId);
        newStudent.setName(newName);
        newStudent.setAge(newAge);

        JSONObject newJsonStudent = new JSONObject();
        newJsonStudent.put("id", newId);
        newJsonStudent.put("name", newName);
        newJsonStudent.put("age", newAge);


        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));
        when(studentService.editStudent(newId, newStudent)).thenReturn(newStudent);

        mockMvc.perform(MockMvcRequestBuilders
                .put("/student/{id}", newId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newJsonStudent.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(newId))
                .andExpect(jsonPath("$.name").value(newName))
                .andExpect(jsonPath("$.age").value(newAge));

    }

    @Test
    public void editStudentIs404() throws Exception{
        final long studId = 1L;
        final String newName = "Nut";
        final int newAge = 124;

        Student newStudent = null;


        when(studentService.editStudent(studId, newStudent)).thenReturn(null);


        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student/{id}", studId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString()))
                        .andExpect(status().isNotFound());
    }


    @Test
    public void testDeleteStudent() throws Exception {
        when(studentService.deleteStudent(1L)).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()));
    }

    @Test
    public void studentForDeleteIsNotFound() throws Exception{
        when(studentService.deleteStudent(1L)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void ifInputFileIsTooBig() throws Exception {
        MockMultipartFile largeFile = new MockMultipartFile("avatar", "avatar.jpg", "image/jpeg", new byte[1024 * 400]);
        long studentId = 1L;

        mockMvc.perform(MockMvcRequestBuilders
                        .multipart("http://localhost8080/student/" + studentId + "/avatar")
                        .file(largeFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest())
                .andExpect(result -> result.getResponse().getContentAsString()
                        .contains("File is too big!"));
    }
}