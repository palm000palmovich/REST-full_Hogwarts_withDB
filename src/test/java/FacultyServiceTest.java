import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.schoolloohcs.SchoolloohcsApplication;
import ru.hogwarts.schoolloohcs.controllers.FacultyController;
import ru.hogwarts.schoolloohcs.controllers.StudentController;
import ru.hogwarts.schoolloohcs.model.Faculty;
import ru.hogwarts.schoolloohcs.model.Student;
import ru.hogwarts.schoolloohcs.repository.FacultyRepository;
import ru.hogwarts.schoolloohcs.services.FacultyServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = SchoolloohcsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyServiceTest {
    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void controllerIsNotNull() throws Exception {
        Assertions.assertThat(facultyController).isNotNull();
    }

    @Test
    public void testGet() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject(
                        "http://localhost:" + port + "/faculty", String.class))
                .isNotNull();
    }

    @Test
    public void testPost() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(6L);
        faculty.setName("Gey");
        faculty.setColor("Blue");

        Assertions
                .assertThat(this.restTemplate.postForObject(
                        "http://localhost:" + port + "/faculty", faculty, String.class
                )).isNotNull();
    }

    @Test
    public void testPut() throws Exception {
        Long facultytId = 1L;
        Faculty updatedFaculty = new Faculty();
        updatedFaculty.setName("New Name");
        updatedFaculty.setColor("brown");

        final String BASE_URL = "/faculty/";


        HttpEntity<Faculty> requestEntity = new HttpEntity<>(updatedFaculty);

        ResponseEntity<Faculty> responseEntity = restTemplate.exchange(
                BASE_URL + facultytId,
                HttpMethod.PUT,
                requestEntity,
                Faculty.class
        );

        Assertions.assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        Assertions.assertThat(responseEntity.getBody()).isNotNull();
        Assertions.assertThat(responseEntity.getBody().getName()).isEqualTo("New Name");
        Assertions.assertThat(responseEntity.getBody().getColor()).isEqualTo(25);

    }
}
