package com.newspringboot.newcms.Controller;


import com.newspringboot.newcms.DTO.*;
import com.newspringboot.newcms.Service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/api/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;


//    @Autowired
//    public TeacherController(TeacherService teacherService) {
//        this.teacherService = teacherService;
//    }

    @GetMapping("users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<AdminResponseDTO> createAdmin(@RequestBody AdminDTO adminDTO) {
        AdminResponseDTO response = adminService.saveAdmin(adminDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<AdminResponseDTO>> getAllAdmins() {
        List<AdminResponseDTO> response = adminService.getAllAdmins();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getadmin/{id}")
    public ResponseEntity<AdminResponseDTO> getAdminById(@PathVariable Long id) {
        AdminResponseDTO response = adminService.getAdminById(id);
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/updateadmin/{id}")
    public ResponseEntity<AdminResponseDTO> updateAdmin(@PathVariable Long id, @RequestBody AdminDTO adminDTO) {
        AdminResponseDTO response = adminService.updateAdmin(id, adminDTO);
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/admin/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.noContent().build();
    }

    //Teacher Controller

    @PostMapping("/create/teachers")
    public ResponseEntity<?> createTeacher(@RequestBody TeacherDTO teacherDTO) {
        if (userService.teacherEmailExists(teacherDTO.getEmail())) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO("Teacher with this email already exists"));
        }
        TeacherResponseDTO responseDTO=userService.createTeacher(teacherDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/teacher/{teacherId}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long teacherId) {
        log.debug("Received request to delete teacher with ID: {}", teacherId);
        try {
            teacherService.deleteTeacher(teacherId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/teacher/{teacherId}")
    public ResponseEntity<TeacherDTO> updateTeacher(@PathVariable Long teacherId, @RequestBody TeacherDTO teacherDTO) {
        log.debug("Received request to update teacher with ID: {}", teacherId);
        try {
            TeacherDTO updatedTeacher = teacherService.updateTeacher(teacherId, teacherDTO);
            return new ResponseEntity<>(updatedTeacher, HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Students Controller

    @PostMapping("/create/students")
    public ResponseEntity<?> createStudent( @RequestBody StudentDTO studentDTO) {
        if (userService.studentEmailExists(studentDTO.getEmail())) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO("Student with this email already exists"));
        }

        StudentResponseDTO responseDTO = userService.createStudent(studentDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/update/student/{studentId}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable Long studentId, @RequestBody StudentDTO studentDTO) {
        try {
            StudentDTO updatedStudent = studentService.updateStudent(studentId, studentDTO);
            return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/deleteStudent")
    public ResponseEntity<String> deleteStudent() {
        userService.deleteUser();
        return ResponseEntity.ok("Deleted Successfully");
    }

    // Course Controllers

    @PostMapping("/create/courses")
    public ResponseEntity<CourseDTO> createCourse(@RequestBody CourseDTO courseDTO){
        CourseDTO createdCourse=courseService.createCourse(courseDTO);
        return new ResponseEntity<>(createdCourse,HttpStatus.OK);
    }

    @GetMapping("/getcourses/{id}")
    public ResponseEntity<CourseResponseDTO> getCourseById(@PathVariable Long id) {
        CourseResponseDTO response = courseService.getCourseById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update/courses/{id}")
    public ResponseEntity<CourseResponseDTO> updateCourse(@PathVariable Long id, @RequestBody CourseDTO courseDTO) {
        CourseResponseDTO response = courseService.updateCourse(id, courseDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/courses/{id}")
    public ResponseEntity<CourseResponseDTO> deleteCourse(@PathVariable Long id) {
        CourseResponseDTO response = courseService.deleteCourse(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/courses/{courseId}/assign-teacher/{teacherId}")
    public ResponseEntity<CourseResponseDTO> assignTeacherToCourse(@PathVariable Long courseId, @PathVariable Long teacherId) {
        CourseResponseDTO response = courseService.assignTeacherToCourse(courseId, teacherId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}