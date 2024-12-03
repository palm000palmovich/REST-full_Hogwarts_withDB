import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.schoolloohcs.model.Student;
import ru.hogwarts.schoolloohcs.repository.StudentRepository;
import ru.hogwarts.schoolloohcs.services.StudentService;
import ru.hogwarts.schoolloohcs.services.StudentServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {
    @InjectMocks
    private StudentServiceImpl studentService;

    @Mock
    private StudentRepository studentRepository;

    private Student student1;
    private Student student2;
    private Student student3;
    private List<Student> actual;

    @BeforeEach
    public void setUp(){
        this.student1 = new Student();
        student1.setId(1L);
        student1.setName("Harry Poopter");
        student1.setAge(21);

        this.student2 = new Student();
        student2.setId(2L);
        student2.setName("Hermoine Reinger");
        student2.setAge(32);

        this.student3 = new Student();
        student3.setId(3L);
        student3.setName("Ron Grizzly");
        student3.setAge(32);

        this.actual = new ArrayList<>();
        actual.add(student1);
        actual.add(student2);
        actual.add(student3);
    }

    @Test
    public void getAllStudentsTest(){
        List<Student> newList = new ArrayList<>();
        newList.add(student1);
        newList.add(student2);
        newList.add(student3);

        when(studentRepository.findAll()).thenReturn(newList);
        List<Student> expected = studentService.getAllStudents();

        assertEquals(expected.size(), actual.size());
        assertEquals(expected, actual);
    }

    @Test
    void testFindByIdTest() {
        Student student = new Student();
        student.setId(1L);
        student.setName("Harry Potter");

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        Student foundStudent = studentService.findStudent(1L);
        assertNotNull(foundStudent);
        assertEquals("Harry Potter", foundStudent.getName());
    }

}
