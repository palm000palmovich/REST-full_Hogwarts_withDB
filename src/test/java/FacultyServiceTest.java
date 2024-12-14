import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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

@ExtendWith(MockitoExtension.class)
public class FacultyServiceTest {
    @InjectMocks
    private FacultyServiceImpl facultyService;
    @Mock
    private FacultyRepository facultyRepository;

    private Faculty faculty1;
    private Faculty faculty2;
    private Faculty faculty3;
    private List<Faculty> actual;

    @BeforeEach
    public void setUp(){
        this.faculty1 = new Faculty();
        faculty1.setId(1L);
        faculty1.setName("Harry Poopter");
        faculty1.setColor("Red");

        this.faculty2 = new Faculty();
        faculty2.setId(2L);
        faculty2.setName("Hermoine Reinger");
        faculty2.setColor("Brown");

        this.faculty3 = new Faculty();
        faculty3.setId(3L);
        faculty3.setName("Ron Grizzly");
        faculty3.setColor("Blue");

        this.actual = new ArrayList<>();
        actual.add(faculty1);
        actual.add(faculty2);
        actual.add(faculty3);
    }

    @Test
    public void getAllStudentsTest(){
        List<Faculty> newList = new ArrayList<>();
        newList.add(faculty1);
        newList.add(faculty2);
        newList.add(faculty3);

        when(facultyRepository.findAll()).thenReturn(newList);
        List<Faculty> expected = facultyService.getAllFacultys();

        assertEquals(expected.size(), actual.size());
        assertEquals(expected, actual);
    }

    @Test
    void testFindById() {
        Faculty faculty= new Faculty();
        faculty.setId(1L);
        faculty.setName("Griffindor");

        when(facultyRepository.findById(1L)).thenReturn(Optional.of(faculty));

        Faculty foundFaculty = facultyService.findById(1L);
        assertNotNull(foundFaculty);
        assertEquals("Griffindor", foundFaculty.getName());
    }
}
