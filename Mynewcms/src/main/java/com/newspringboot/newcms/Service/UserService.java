package com.newspringboot.newcms.Service;

import com.newspringboot.newcms.DTO.*;
import com.newspringboot.newcms.Model.Role;
import com.newspringboot.newcms.Model.Student;
import com.newspringboot.newcms.Model.Teacher;
import com.newspringboot.newcms.Model.User;
import com.newspringboot.newcms.Repository.RoleRepo;
import com.newspringboot.newcms.Repository.StudentRepo;
import com.newspringboot.newcms.Repository.TeacherRepo;
import com.newspringboot.newcms.Repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    //  private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private StudentRepo studentRepo;
    @Autowired
    private TeacherRepo teacherRepo;
    @Autowired
    private RoleRepo roleRepo;

    public StudentResponseDTO createStudent(StudentDTO studentDTO) {
        if (studentDTO.getPassword() == null || studentDTO.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        // Encrypt the password
        //String encodedPassword = passwordEncoder.encode(studentDTO.getPassword());

        Student student = new Student();
        student.setName(studentDTO.getName());
        student.setEmail(studentDTO.getEmail());
        student.setPassword(studentDTO.getPassword());

        Long roleId = getRoleIdByRoleName("Student");
        if (roleId == null) {
            throw new IllegalArgumentException("Invalid role name: student");
        }

        User user = new User();
        user.setUserName(studentDTO.getName());
        user.setUserEmail(studentDTO.getEmail());
        user.setUserPassword(studentDTO.getPassword());
        user.setRoleId(roleId);

        // Save the Student entity to the database
        Student savedStudent = studentRepo.save(student);
        log.info("Student email: {}", savedStudent.getEmail());

        // Save the User entity to the database
        User savedUser = userRepo.save(user);

        // Convert the saved Student entity to StudentResponseDTO
        return new StudentResponseDTO(
                savedStudent.getId(),
                savedStudent.getName(),
                savedStudent.getEmail()
        );
    }


//    public void saveNewStudent(User user)
//    {
//        userRepo.save(user);
//    }


    public TeacherResponseDTO createTeacher(TeacherDTO teacherDTO) {
        if (teacherDTO.getEmail() == null || teacherDTO.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (teacherDTO.getPassword() == null || teacherDTO.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        Teacher teacher = new Teacher();
        teacher.setName(teacherDTO.getName());
        teacher.setEmail(teacherDTO.getEmail());
        teacher.setPassword(teacherDTO.getPassword());

        Long roleId = getRoleIdByRoleName("Teacher");
        if (roleId == null) {
            throw new IllegalArgumentException("Invalid role name: teacher");
        }

        User user = new User();
        user.setUserName(teacherDTO.getName());
        user.setUserEmail(teacherDTO.getEmail());
        user.setUserPassword(teacherDTO.getPassword());
        user.setRoleId(roleId);

        Teacher savedTeacher = teacherRepo.save(teacher);
        log.info("Teacher email: {}", savedTeacher.getEmail());

        User savedUser = userRepo.save(user);

        return new TeacherResponseDTO(
                savedTeacher.getId(),
                savedTeacher.getName(),
                savedTeacher.getEmail()
        );

    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepo.findAll();
        return users.stream()
                .map(this::convertToUserDTO)
                .collect(Collectors.toList());
    }

    private UserDTO convertToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setUserName(user.getUserName());
        userDTO.setUserPassword(user.getUserPassword());
        userDTO.setUserEmail(user.getUserEmail());
        userDTO.setRoleId(user.getRoleId() != null ? user.getRoleId() : null);
        return userDTO;
    }

    public void deleteUser() {
        userRepo.deleteAll();
    }

    private Long getRoleIdByRoleName(String roleName) {
        Role role = roleRepo.findByRoleName(roleName);
        if (role != null) {
            return role.getRoleId();
        }
        throw new IllegalArgumentException("Invalid role name: " + roleName);
    }

    public boolean studentEmailExists(String email) {
        return studentRepo.existsByEmail(email);
    }

    public boolean teacherEmailExists(String email) {
        return teacherRepo.existsByEmail(email);
    }
}
