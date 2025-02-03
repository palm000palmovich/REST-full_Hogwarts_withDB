package ru.hogwarts.schoolloohcs.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hogwarts.schoolloohcs.model.Faculty;
import ru.hogwarts.schoolloohcs.repository.FacultyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository facultyRepository;
    Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Autowired
    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    //Output all facultys
    @Override
    public List<Faculty> getAllFacultys(){
        logger.debug("Was invoked method for output all facultees: " + facultyRepository.findAll());
        return facultyRepository.findAll();
    }

    //Create faculty
    @Override
    public Faculty createFaculty(Faculty faculty){
        logger.debug("Was invoked method for create new faculty: " + faculty);
        return facultyRepository.save(faculty);
    }

    //Delete faculty
    @Override
    public Faculty deleteFaculty(long id){
        logger.debug("Was invoked method for delete faculty: " + id);
        Faculty fac = facultyRepository.findById(id).orElse(null);
        if (fac != null){
            facultyRepository.deleteById(id);
        } return fac;
    }

    //Edit faculty
    @Override
    public Faculty editFaculty(long id, Faculty faculty){
        logger.debug("Was invoked method for edit faculty: " + id + " " + faculty);
        Faculty facultyToEdit = facultyRepository.findById(id).orElse(null);
        if (facultyToEdit != null){
            facultyToEdit.setName(faculty.getName());
            facultyToEdit.setColor(faculty.getColor());
            facultyRepository.save(facultyToEdit);
            return faculty;
        } return null;
    }

    //Find by id
    @Override
    public Faculty findById(long id){
        logger.debug("Was invoked method to find faculty by id: " + id);
        Faculty foundFaculty = facultyRepository.findById(id).get();
        if (foundFaculty == null){logger.error("There is no faculty by that id");}
        return facultyRepository.findById(id).orElse(null);
    }

    //Facultys with determine color
    @Override
    public List<Faculty> facultysByColor(String color){
        logger.debug("Was invoked method to find faculty by color: " + color);
        List<Faculty> facultysByColor = facultyRepository.findAll()
                .stream()
                .filter(faculty -> faculty.getColor().equals(color))
                .collect(Collectors.toList());
        return facultysByColor;
    }

    //Clear data base
    @Override
    public void clearDB(){
        logger.debug("Was invoked method to clear DB");
        facultyRepository.deleteAll();}

    //Facultys by name or color
    @Override
    public List<Faculty> facByColOrName(String name, String color){
        logger.debug("Was invoked method to find faculty by name or color");
        return facultyRepository.findByNameOrColorIgnoreCase(name, color);
    }

    @Override
    public Optional<Faculty> getLongestNameOfFaculty(){
        int maxLength = 0;
        Optional<Faculty> longestFaculty = facultyRepository.findAll().parallelStream()
                .max((f1, f2) -> Integer.compare(f1.getName().length(), f2.getName().length()));
        return longestFaculty;
    }

    @Override
    public int randomExpression(){
        long startTime = System.currentTimeMillis();
        int num =  Stream.iterate(1, a -> a +1)
                .parallel()
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b);
        long finishTime = System.currentTimeMillis();
        logger.debug("Running time: " + (finishTime - startTime) + " ms.");
        return num;
    }
}
