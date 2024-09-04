package com.newspringboot.newcms.Controller;

import com.newspringboot.newcms.DTO.*;
import com.newspringboot.newcms.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/students")
    public ResponseEntity<?> createStudent( @RequestBody StudentDTO studentDTO) {
        if (userService.studentEmailExists(studentDTO.getEmail())) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO("Student with this email already exists"));
        }

        StudentResponseDTO responseDTO = userService.createStudent(studentDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PostMapping("/teachers")
    public ResponseEntity<?> createTeacher(@RequestBody TeacherDTO teacherDTO) {
        if (userService.teacherEmailExists(teacherDTO.getEmail())) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO("Teacher with this email already exists"));
        }
       TeacherResponseDTO responseDTO=userService.createTeacher(teacherDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteStudent() {
        userService.deleteUser();
        return ResponseEntity.ok("Deleted Successfully");
    }
}
