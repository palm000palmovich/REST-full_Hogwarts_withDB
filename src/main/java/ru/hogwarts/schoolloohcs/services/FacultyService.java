package ru.hogwarts.schoolloohcs.services;

import org.springframework.http.ResponseEntity;
import ru.hogwarts.schoolloohcs.model.Faculty;

import java.util.List;

public interface FacultyService {
    //Output all facultys
   List<Faculty> getAllFacultys();

    //Create faculty
    Faculty createFaculty(Faculty faculty);
    //Delete faculty
    Faculty deleteFaculty(long id);

    //Edit faculty
    Faculty editFaculty(long id, Faculty faculty);
    //Find by id
    Faculty findById(long id);
    //Facultys with determine color
    List<Faculty> facultysByColor(String color);

    //Clear data base
    void clearDB();
    List<Faculty> facByColOrName(String name, String color);
}
