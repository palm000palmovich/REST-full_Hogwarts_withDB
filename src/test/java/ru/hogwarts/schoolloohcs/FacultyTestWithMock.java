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
import ru.hogwarts.schoolloohcs.controllers.FacultyController;
import ru.hogwarts.schoolloohcs.controllers.StudentController;
import ru.hogwarts.schoolloohcs.model.Faculty;
import ru.hogwarts.schoolloohcs.model.Student;
import ru.hogwarts.schoolloohcs.repository.AvatarRepository;
import ru.hogwarts.schoolloohcs.repository.FacultyRepository;
import ru.hogwarts.schoolloohcs.repository.StudentRepository;
import ru.hogwarts.schoolloohcs.services.AvatarService;
import ru.hogwarts.schoolloohcs.services.FacultyService;
import ru.hogwarts.schoolloohcs.services.FacultyServiceImpl;
import ru.hogwarts.schoolloohcs.services.StudentServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FacultyController.class)
public class FacultyTestWithMock {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FacultyRepository facultyRepository;
    @SpyBean
    private FacultyServiceImpl facultyService;

    private FacultyController facultyController;

    private Faculty faculty = new Faculty();
    private JSONObject jsonObject = new JSONObject();

    @BeforeEach
    public void setUp() throws Exception{
        final long id = 1L;
        final String name = "Baum";
        final String color = "Green";

        this.jsonObject.put("id", id);
        this.jsonObject.put("name", name);
        this.jsonObject.put("color", color);

        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);
    }

    @Test
    public void saveStudentsTest() throws Exception{

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value(faculty.getId()))
                        .andExpect(jsonPath("$.name").value(faculty.getName()))
                        .andExpect(jsonPath("$.color").value(faculty.getColor()));

    }

    @Test
    public void facultyIsNotFoundTest() throws Exception{
        when(facultyService.findById(any(Long.class))).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + faculty.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }
    @Test
    public void getFaculteeByIdTest() throws Exception{

        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + faculty.getId())
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value(faculty.getId()))
                        .andExpect(jsonPath("$.name").value(faculty.getName()))
                        .andExpect(jsonPath("$.color").value(faculty.getColor()));
    }

    @Test
    public void getAllFaculteesTest() throws Exception{
        when(facultyService.getAllFacultys()).thenReturn(List.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllFaculteesIfDBisEmpty() throws Exception{
        List<Faculty> emptyListOfFacultees = new ArrayList<>();

        when(facultyService.getAllFacultys()).thenReturn(emptyListOfFacultees);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> result.getResponse().getContentAsString()
                        .contains("DB of facultees is empty!"));
    }

    @Test
    public void editFacultyTest() throws Exception{
        final long newId = 1L;
        final String newName = "MGU";
        final String newColor = "brown";

        Faculty newFaculty = new Faculty();
        newFaculty.setId(newId);
        newFaculty.setName(newName);
        newFaculty.setColor(newColor);

        JSONObject newJsonFaculty = new JSONObject();
        newJsonFaculty.put("id", newId);
        newJsonFaculty.put("name", newName);
        newJsonFaculty.put("color", newColor);


        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));
        when(facultyService.editFaculty(newId, newFaculty)).thenReturn(newFaculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty/{id}", newId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newJsonFaculty.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(newId))
                .andExpect(jsonPath("$.name").value(newName))
                .andExpect(jsonPath("$.color").value(newColor));
    }

    @Test
        public void editFacultyIs404() throws Exception{
        final long facId = 1L;
        final String newName = "NUTcrackers";
        final String newColor = "Pink";

            Faculty newFaculty = null;


        when(facultyService.editFaculty(facId, newFaculty)).thenReturn(null);


        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty/{id}", facId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteFaculty() throws Exception {
        when(facultyService.deleteFaculty(1L)).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()));
    }

    @Test
    public void facultyForDeleteIsNotFound() throws Exception{
        when(facultyService.deleteFaculty(1L)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}