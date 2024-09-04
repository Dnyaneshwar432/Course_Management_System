package com.newspringboot.newcms.UserTest;

import com.newspringboot.newcms.DTO.*;
import com.newspringboot.newcms.Model.Role;
import com.newspringboot.newcms.Model.Student;
import com.newspringboot.newcms.Model.Teacher;
import com.newspringboot.newcms.Model.User;
import com.newspringboot.newcms.Repository.RoleRepo;
import com.newspringboot.newcms.Repository.StudentRepo;
import com.newspringboot.newcms.Repository.TeacherRepo;
import com.newspringboot.newcms.Repository.UserRepo;
import com.newspringboot.newcms.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private StudentRepo studentRepo;

    @Mock
    private TeacherRepo teacherRepo;

    @Mock
    private RoleRepo roleRepo;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setup() {
        // we can add any setup before each test here
    }



    @Test
    public void testCreateStudent_Success() { //testCreateStudent_Success: Test creating a student successfully.

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName("Dnyaneshwar Kankale");
        studentDTO.setEmail("dnyaneshwarkankale@example.com");
        studentDTO.setPassword("password123");

        Role studentRole = new Role();
        studentRole.setRoleId(1L);
        studentRole.setRoleName("Student");

        when(roleRepo.findByRoleName("Student")).thenReturn(studentRole);
        when(studentRepo.save(any(Student.class))).thenAnswer(i -> i.getArguments()[0]);
        when(userRepo.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        StudentResponseDTO response = userService.createStudent(studentDTO);

        assertNotNull(response);
        assertEquals("Dnyaneshwar Kankale", response.getName());
        assertEquals("dnyaneshwarkankale@example.com", response.getEmail());

        verify(studentRepo, times(1)).save(any(Student.class));
        verify(userRepo, times(1)).save(any(User.class));
    }

    @Test
    public void testCreateStudent_InvalidRole() { //testCreateStudent_InvalidRole: used to test creating a student with an invalid role.

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName("Ganesh Giri");
        studentDTO.setEmail("ganesh.giri@example.com");
        studentDTO.setPassword("password1234");

        when(roleRepo.findByRoleName("Student")).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.createStudent(studentDTO);
        });

        assertEquals("Invalid role name: Student", exception.getMessage());
    }

    @Test
    public void testCreateStudent_NullPassword() { //testCreateStudent_NullPassword: Used to test creating a student with a null password.

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName("Girish verma");
        studentDTO.setEmail("girish.verma@example.com");
        studentDTO.setPassword(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.createStudent(studentDTO);
        });

        assertEquals("Password cannot be null or empty", exception.getMessage());
    }

    @Test
    public void testCreateTeacher_Success() { //testCreateTeacher_Success: here we test creating a teacher successfully.

        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.setName("Shantaram Shelke");
        teacherDTO.setEmail("shantaram.shelke@example.com");
        teacherDTO.setPassword("password1234");

        Role teacherRole = new Role();
        teacherRole.setRoleId(2L);
        teacherRole.setRoleName("Teacher");

        when(roleRepo.findByRoleName("Teacher")).thenReturn(teacherRole);
        when(teacherRepo.save(any(Teacher.class))).thenAnswer(i -> i.getArguments()[0]);
        when(userRepo.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        TeacherResponseDTO response = userService.createTeacher(teacherDTO);

        assertNotNull(response);
        assertEquals("Shantaram Shelke", response.getName());
        assertEquals("shantaram.shelke@example.com", response.getEmail());

        verify(teacherRepo, times(1)).save(any(Teacher.class));
        verify(userRepo, times(1)).save(any(User.class));
    }

    @Test
    public void testCreateTeacher_InvalidRole() { //testCreateTeacher_InvalidRole: Test creating a teacher with an invalid role.

        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.setName("Prajwal Chordiya");
        teacherDTO.setEmail("prajwal.chordiya12@example.com");
        teacherDTO.setPassword("password1238");

        when(roleRepo.findByRoleName("Teacher")).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.createTeacher(teacherDTO);
        });

        assertEquals("Invalid role name: Teacher", exception.getMessage());
    }

    @Test
    public void testCreateTeacher_NullPassword() { //testCreateTeacher_NullPassword: Test creating a teacher with a null password.

        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.setName("Jane Smith");
        teacherDTO.setEmail("jane.smith@example.com");
        teacherDTO.setPassword(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.createTeacher(teacherDTO);
        });

        assertEquals("Password cannot be null or empty", exception.getMessage());
    }

    @Test
    public void testGetAllUsers() { //testGetAllUsers: Test fetching all users.

        User user1 = new User();
        user1.setUserId(1L);
        user1.setUserName("Kundan Mishra ");
        user1.setUserEmail("kundan.mishra@example.com");
        user1.setUserPassword("password123");

        User user2 = new User();
        user2.setUserId(2L);
        user2.setUserName("Tejas Joshi");
        user2.setUserEmail("tejas.joshi@example.com");
        user2.setUserPassword("password123");

        when(userRepo.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<UserDTO> users = userService.getAllUsers();

        assertNotNull(users);
        assertEquals(2, users.size());
        assertEquals("Kundan Mishra", users.get(0).getUserName().trim());
        assertEquals("Tejas Joshi", users.get(1).getUserName().trim());

        verify(userRepo, times(1)).findAll();
    }

    @Test
    public void testDeleteUser() {
        userService.deleteUser();
        verify(userRepo, times(1)).deleteAll();
    }

    @Test
    public void testStudentEmailExists() {
        when(studentRepo.existsByEmail("girish.verma@example.com")).thenReturn(true);
        assertTrue(userService.studentEmailExists("girish.verma@example.com"));
    }

    @Test
    public void testTeacherEmailExists() {
        when(teacherRepo.existsByEmail("jane.smith@example.com")).thenReturn(true);
        assertTrue(userService.teacherEmailExists("jane.smith@example.com"));
    }
}
