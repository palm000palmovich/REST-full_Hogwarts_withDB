
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.schoolloohcs.SchoolloohcsApplication;
import ru.hogwarts.schoolloohcs.controllers.StudentController;
import ru.hogwarts.schoolloohcs.model.Student;

@SpringBootTest(classes = SchoolloohcsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class StudentServiceTest {
    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void controllerIsNotNull() throws Exception {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    public void testGetStudents() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject(
                        "http://localhost:" + port + "/student", String.class))
                .isNotNull();
    }

    @Test
    public void testPostStudent() throws Exception {
        Student student = new Student();
        student.setId(1l);
        student.setName("German");
        student.setAge(25);

        Assertions
                .assertThat(this.restTemplate.postForObject(
                        "http://localhost:" + port + "/student", student, String.class
                )).isNotNull();
    }

    @Test
    public void testPut() throws Exception {
        Long studentId = 1L;
        Student updatedStudent = new Student();
        updatedStudent.setName("New Name");
        updatedStudent.setAge(25);

        final String BASE_URL = "/student/";


        HttpEntity<Student> requestEntity = new HttpEntity<>(updatedStudent);

        ResponseEntity<Student> responseEntity = restTemplate.exchange(
                BASE_URL + studentId,
                HttpMethod.PUT,
                requestEntity,
                Student.class
        );

        Assertions.assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        Assertions.assertThat(responseEntity.getBody()).isNotNull();
        Assertions.assertThat(responseEntity.getBody().getName()).isEqualTo("New Name");
        Assertions.assertThat(responseEntity.getBody().getAge()).isEqualTo(25);

    }


}
