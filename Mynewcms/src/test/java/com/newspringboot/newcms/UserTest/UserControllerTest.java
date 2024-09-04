package com.newspringboot.newcms.UserTest;


import com.newspringboot.newcms.Controller.UserController;
import com.newspringboot.newcms.DTO.*;
import com.newspringboot.newcms.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.ArgumentMatchers.any;


import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testGetAllUsers() throws Exception {
        UserDTO user1 = new UserDTO();
        user1.setUserId(1L);
        user1.setUserName("Rajesh Kumar");
        user1.setUserEmail("rajesh.kumar@example.com");
        user1.setUserPassword("password123");

        UserDTO user2 = new UserDTO();
        user2.setUserId(2L);
        user2.setUserName("Anita Sharma");
        user2.setUserEmail("anita.sharma@example.com");
        user2.setUserPassword("password123");

        List<UserDTO> users = Arrays.asList(user1, user2);

        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].user_name", is("Rajesh Kumar")))
                .andExpect(jsonPath("$[1].user_name", is("Anita Sharma")));

        verify(userService, times(1)).getAllUsers();
    }

    @Test
    public void testCreateStudent_Success() throws Exception {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName("Rajesh Kumar");
        studentDTO.setEmail("rajesh.kumar@example.com");
        studentDTO.setPassword("password123");

        StudentResponseDTO responseDTO = new StudentResponseDTO(1L, "Rajesh Kumar", "rajesh.kumar@example.com");

        when(userService.studentEmailExists("rajesh.kumar@example.com")).thenReturn(false);
        when(userService.createStudent(any(StudentDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/users/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(studentDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Rajesh Kumar")))
                .andExpect(jsonPath("$.email", is("rajesh.kumar@example.com")));

        verify(userService, times(1)).studentEmailExists("rajesh.kumar@example.com");
        verify(userService, times(1)).createStudent(any(StudentDTO.class));
    }

    @Test
    public void testCreateStudent_EmailExists() throws Exception {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName("Rajesh Kumar");
        studentDTO.setEmail("rajesh.kumar@example.com");
        studentDTO.setPassword("password123");

        when(userService.studentEmailExists("rajesh.kumar@example.com")).thenReturn(true);

        mockMvc.perform(post("/api/users/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(studentDTO)))
                .andExpect(status().isBadRequest());


        verify(userService, times(1)).studentEmailExists("rajesh.kumar@example.com");
        verify(userService, times(0)).createStudent(any(StudentDTO.class));
    }

    @Test
    public void testCreateTeacher_Success() throws Exception { //testCreateStudent_Success: Tests creating a student successfully.

        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.setName("Anita Sharma");
        teacherDTO.setEmail("anita.sharma@example.com");
        teacherDTO.setPassword("password123");

        TeacherResponseDTO responseDTO = new TeacherResponseDTO(1L, "Anita Sharma", "anita.sharma@example.com");

        when(userService.teacherEmailExists("anita.sharma@example.com")).thenReturn(false);
        when(userService.createTeacher(any(TeacherDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/users/teachers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(teacherDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Anita Sharma")))
                .andExpect(jsonPath("$.Email", is("anita.sharma@example.com")));

        verify(userService, times(1)).teacherEmailExists("anita.sharma@example.com");
        verify(userService, times(1)).createTeacher(any(TeacherDTO.class));
    }

    @Test
    public void testCreateTeacher_EmailExists() throws Exception {
        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.setName("Anita Sharma");
        teacherDTO.setEmail("anita.sharma@example.com");
        teacherDTO.setPassword("password123");

        when(userService.teacherEmailExists("anita.sharma@example.com")).thenReturn(true);

        mockMvc.perform(post("/api/users/teachers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(teacherDTO)))
                .andExpect(status().isBadRequest());


        verify(userService, times(1)).teacherEmailExists("anita.sharma@example.com");
        verify(userService, times(0)).createTeacher(any(TeacherDTO.class));
    }

    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/api/users/delete")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted Successfully"));

        verify(userService, times(1)).deleteUser();
    }
}
