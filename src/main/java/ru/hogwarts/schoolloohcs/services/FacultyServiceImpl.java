package ru.hogwarts.schoolloohcs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.schoolloohcs.model.Faculty;
import ru.hogwarts.schoolloohcs.repository.FacultyRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository facultyRepository;

    @Autowired
    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }



    //Output all facultys
    @Override
    public List<Faculty> getAllFacultys(){
        return facultyRepository.findAll();
    }

    //Create faculty
    @Override
    public Faculty createFaculty(Faculty faculty){
        return facultyRepository.save(faculty);
    }

    //Delete faculty
    @Override
    public void deleteFaculty(long id){
        facultyRepository.deleteById(id);
    }

    //Edit faculty
    @Override
    public Faculty editFaculty(long id, Faculty faculty){
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
        return facultyRepository.findById(id).orElse(null);
    }

    //Facultys with determine color
    @Override
    public List<Faculty> facultysByColor(String color){
        List<Faculty> facultysByColor = facultyRepository.findAll()
                .stream()
                .filter(faculty -> faculty.getColor().equals(color))
                .collect(Collectors.toList());
        return facultysByColor;
    }

    //Clear data base
    @Override
    public void clearDB(){facultyRepository.deleteAll();}
}
