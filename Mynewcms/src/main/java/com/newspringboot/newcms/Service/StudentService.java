package com.newspringboot.newcms.Service;


import com.newspringboot.newcms.DTO.CourseDTO;
import com.newspringboot.newcms.DTO.StudentDTO;
import com.newspringboot.newcms.Model.Course;
import com.newspringboot.newcms.Model.Student;
import com.newspringboot.newcms.Repository.CourseRepository;
import com.newspringboot.newcms.Repository.StudentRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional

public class StudentService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private ModelMapper modelMapper;




    public List<CourseDTO> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return courses.stream()
                .map(course -> modelMapper.map(course, CourseDTO.class))
                .collect(Collectors.toList());
    }


    public StudentDTO updateStudent(Long studentId, StudentDTO studentDTO) {
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with id " + studentId));

        student.setName(studentDTO.getName());
        student.setEmail(studentDTO.getEmail());
        student.setPassword(studentDTO.getPassword());

        Student updatedStudent = studentRepo.save(student);

        return modelMapper.map(updatedStudent, StudentDTO.class);
    }

}

