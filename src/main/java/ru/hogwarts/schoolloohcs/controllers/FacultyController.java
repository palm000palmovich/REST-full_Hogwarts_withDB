package ru.hogwarts.schoolloohcs.controllers;

import jdk.jfr.DataAmount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.schoolloohcs.model.Faculty;
import ru.hogwarts.schoolloohcs.services.FacultyService;

import java.util.List;

@RestController
@RequestMapping(path = "/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    @Autowired
    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    //All facultys
    @GetMapping
    public List<Faculty> getAll(){
        return facultyService.getAllFacultys();
    }

    //POST
    @PostMapping
    public ResponseEntity<Faculty> create(
            @RequestBody Faculty faculty){
        return ResponseEntity.ok(facultyService.createFaculty(faculty));
    }
    //GET
    @GetMapping(path = "/{id}")
    public ResponseEntity<Faculty> getById(
            @PathVariable("id") long id){
        Faculty faculty = facultyService.findById(id);
        if (faculty  == null){return ResponseEntity.notFound().build();}
        return ResponseEntity.ok(faculty);
    }

    //PUT
    @PutMapping(path = "/{id}")
    public ResponseEntity<Faculty> edit(
            @PathVariable("id") long id,
            @RequestBody Faculty faculty){
        Faculty fac = facultyService.editFaculty(id, faculty);
        if (fac == null){return ResponseEntity.notFound().build();}
        return ResponseEntity.ok(fac);
    }

    //DELETE
    @DeleteMapping(path = "/{id}")
    public ResponseEntity delete(
            @PathVariable("id") long id){
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }
    //By color
    @GetMapping(path = "/{color}/color")
    public ResponseEntity<List<Faculty>> findByColor(
            @PathVariable("color") String color){
        List<Faculty> listOfFacsInColor = facultyService.facultysByColor(color);
        if (listOfFacsInColor == null){return ResponseEntity.notFound().build();}
        return ResponseEntity.ok(listOfFacsInColor);
    }
    //Clear
    @GetMapping(path = "/clear")
    public void clear(){
        facultyService.clearDB();
    }
}
