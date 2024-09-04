package com.newspringboot.newcms.Controller;

import com.newspringboot.newcms.DTO.CourseDTO;
import com.newspringboot.newcms.DTO.TeacherResponseDTO;
import com.newspringboot.newcms.Exception.ResourceNotFoundException;
import com.newspringboot.newcms.Service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/teachers")
@Slf4j
public class TeacherController {

    private final TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping()
    public ResponseEntity<List<TeacherResponseDTO>> getAllTeachers() {
        log.debug("Received request to get all teachers");
        List<TeacherResponseDTO> teachers = teacherService.getAllTeachers();
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }

    @GetMapping("/{teacherId}")
    public ResponseEntity<TeacherResponseDTO> getTeacherById(@PathVariable Long teacherId) {
        log.debug("Received request to get teacher with ID: {}", teacherId);

        Optional<TeacherResponseDTO> teacher = teacherService.getTeacherById(teacherId);

        return teacher.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @GetMapping("/{teacherId}/courses")
    public ResponseEntity<List<CourseDTO>> getCoursesByTeacherId(@PathVariable Long teacherId) {
        log.debug("Received request to get courses for teacher with ID: {}", teacherId);
        try {
            List<CourseDTO> courses = teacherService.getCoursesByTeacherId(teacherId);
            return new ResponseEntity<>(courses, HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}