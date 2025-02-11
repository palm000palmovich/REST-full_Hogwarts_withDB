package ru.hogwarts.schoolloohcs.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hogwarts.schoolloohcs.Exceptions.DBisEmptyException;
import ru.hogwarts.schoolloohcs.model.Student;
import ru.hogwarts.schoolloohcs.repository.AvatarRepository;
import ru.hogwarts.schoolloohcs.repository.StudentRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final AvatarRepository avatarRepository;
    private Object flag = new Object();
    private Integer counter = 0;

    Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, AvatarRepository avatarRepository) {
        this.studentRepository = studentRepository;
        this.avatarRepository = avatarRepository;
    }


    //Output students
    @Override
    public List<Student> getAllStudents() {
        logger.debug("Was invoked method for output all students");
        return studentRepository.findAll();
    }

    //Creating students
    @Override
    public Student createStudent(Student student) {
        logger.debug("Was invoked method to create new students: " + student);
        return studentRepository.save(student);
    }

    //Find students by id
    @Override
    public Student findStudent(long id) {
        Student foundStudent = studentRepository.findById(id).get();
        logger.debug("Was invoked method to find student by id: " + id);
        if (foundStudent == null) {
            logger.error("Theres no student by this id: " + id);
        }
        return studentRepository.findById(id).orElse(null);
    }

    //Edit students
    @Override
    public Student editStudent(long id, Student student) {
        logger.debug("Was invoked method to edit student by id: " + id + " " + student);
        Student studForChange = studentRepository.findById(id).orElse(null);
        if (studForChange != null) {
            studForChange.setName(student.getName());
            studForChange.setAge(student.getAge());
            studentRepository.save(studForChange);
            return student;
        }
        return null;
    }

    //Delete students
    @Transactional
    @Override
    public Student deleteStudent(Long id) {
        logger.debug("Was invoked method to delete student by id: " + id);
        Student stud = studentRepository.findById(id).orElse(null);
        if (stud != null) {
            avatarRepository.deleteByStudentId(id);
            studentRepository.deleteById(id);
        }
        return stud;
    }

    //Find students by age
    @Override
    public List<Student> findByAge(int age) {
        logger.debug("Was invoked method to find student by age: " + age);
        List<Student> studentsInAge = studentRepository.findAll()
                .stream()
                .filter(student -> student.getAge() == age)
                .collect(Collectors.toList());
        if (studentsInAge.size() == 0) {
            logger.error("There is no studnets by this age: " + age);
        }
        return studentsInAge;
    }

    //Full reset
    @Transactional
    @Override
    public void clearDB() {
        logger.debug("Was invoked method to clear DB");
        avatarRepository.deleteAll();
    }

    //Students between min and max ages
    @Override
    public List<Student> studentsBemweenAges(int min, int max) {
        logger.debug("Was invoked method to find students determined between age");
        return studentRepository.findByAgeBetween(min, max);
    }

    //Count of students
    @Override
    public int getCountOfStudent() {
        logger.debug("Was invoked method to count students");
        return studentRepository.getCountOfStudents();
    }

    //Avarage age of students
    @Override
    public float getAvgAge() {
        logger.debug("Was invoked method to determine avarage age of students");
        return studentRepository.getAvgAge();
    }

    //Get last 5 students
    @Override
    public List<Student> getLast5Students() {
        logger.debug("Was invoked method to output 5 last students");
        return studentRepository.getLast5Students();
    }

    @Override
    public List<String> studentsWithAinStartOfName() {
        logger.info("Invoked method studentsWithAinStartOfName");
        long startTime = System.currentTimeMillis();
        List<String> studsList = studentRepository.findAll().parallelStream()
                .map(Student::getName)
                .filter(name -> name.startsWith("A"))
                .map(String::toUpperCase)
                .sorted()
                .collect(Collectors.toList());
        long finishTime = System.currentTimeMillis();
        logger.debug("Running time: " + (finishTime - startTime) + " ms.");
        return studsList;
    }

    @Override
    public double getAvgAgeThroughStreams() {
        int sum = 0;
        logger.info("Invoked method getAvgAgeThroughStreams");
        long startTime = System.currentTimeMillis();
        double avgAge = studentRepository
                .findAll()
                .parallelStream()
                .mapToInt(stud -> stud.getAge())
                .average().orElse(0);
        long finishTime = System.currentTimeMillis();
        logger.debug("Running time: " + (finishTime - startTime) + " ms.");
        return avgAge;
    }


    @Override
    public void getParallelThreadOfStudents() {
        logger.info("Invoked method for parallel output of students.");

        long size = getSizeOfDB();
        if (size != 0) {

            System.out.println(studentRepository.getFirst2Studs()[0]);
            System.out.println(studentRepository.getFirst2Studs()[1]);

            if (size >= 5) {
                Thread thread = new Thread(() ->
                {
                    System.out.println(studentRepository.getStudentExcept2BeginnigAndEnd());
                });
                thread.start();

                System.out.println(studentRepository.getLast2Studs()[0]);
                System.out.println(studentRepository.getLast2Studs()[1]);
            }

        } else {
            throw new DBisEmptyException("DB is empty!");
        }
    }


    @Override
    public void getSynhronizedParallelThreadOfStudents() {
        logger.info("Invoked method for synhronized parallel output of students.");


        long size = getSizeOfDB();

        if (size != 0) {
            System.out.println(studentRepository.getFirst2Studs()[0]);
            System.out.println(studentRepository.getFirst2Studs()[1]);

            if (size > 2) {


                Thread thread2 = new Thread(() -> {
                    printSecondList();
                }); thread2.start();

                Thread thread1 = new Thread(() -> {
                    printFirstList();
                }); thread1.start();

            }
        } else {
            throw new DBisEmptyException("DB is empty!");
        }

    }

    public void printFirstList() {
        List<Student> studentList = studentRepository.studsWithoutFirst2();
        Integer halfSize = studentList.size() / 2;

        //Lists with 1st part of student's list
        List<Student> firstHalf = studentList.parallelStream()
                .limit(halfSize)
                .collect(Collectors.toList());
        synchronized (flag) {
            System.out.println("FIRST PART");
            for (Student student : firstHalf) {
                counter++;
                System.out.println(student + " " + counter);
            }
        }

    }

    public void printSecondList() {
        List<Student> studentList = studentRepository.studsWithoutFirst2();
        Integer halfSize = studentList.size() / 2;

        //Lists with 2nd part of student's list
        List<Student> secondHalf = studentList.parallelStream()
                .skip(halfSize)
                .collect(Collectors.toList());
        synchronized (flag) {
            System.out.println("SECOND PART");
            for (Student student : secondHalf) {
                counter++;
                System.out.println(student + " " + counter);
            }
        }
    }

        //Get size of DB
        @Override
        public int getSizeOfDB(){
            int dbSize = studentRepository.getCountOfNotUniqueStudents();

            logger.debug("Size of student db: " + dbSize);
            return dbSize;
        }
}