package com.newspringboot.newcms.AdminTest;

import com.newspringboot.newcms.Controller.AdminController;
import com.newspringboot.newcms.DTO.*;
import com.newspringboot.newcms.Service.AdminService;
import com.newspringboot.newcms.Service.UserService;
import com.newspringboot.newcms.Service.CourseService;
import com.newspringboot.newcms.Service.StudentService;
import com.newspringboot.newcms.Service.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.newspringboot.newcms.DTO.AdminResponseDTO;


@WebMvcTest(AdminController.class)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminService adminService;

    @MockBean
    private UserService userService;

    @MockBean
    private CourseService courseService;

    @MockBean
    private StudentService studentService;

    @MockBean
    private TeacherService teacherService;

    private AdminDTO adminDTO;
    private AdminResponseDTO adminResponseDTO;
    private TeacherDTO teacherDTO;
    private StudentDTO studentDTO;
    private CourseDTO courseDTO;

    @BeforeEach
    public void setup() {
        adminDTO = new AdminDTO();
        adminDTO.setAdminName("admin");
        adminDTO.setEmail("admin@example.com");

        adminResponseDTO = new AdminResponseDTO();
        adminResponseDTO.setAdminName("admin");
        adminResponseDTO.setEmail("admin@example.com");
        adminResponseDTO.setStatusMessage("Admin created successfully");

        teacherDTO = new TeacherDTO();
        teacherDTO.setEmail("teacher@example.com");

        studentDTO = new StudentDTO();
        studentDTO.setEmail("student@example.com");

        courseDTO = new CourseDTO();
        courseDTO.setName("Course Name");

        MockitoAnnotations.openMocks(this);

    }


    @Test
    public void testCreateAdmin() throws Exception {
        when(adminService.saveAdmin(any(AdminDTO.class))).thenReturn(adminResponseDTO);

        mockMvc.perform(post("/api/admins")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"admin\", \"email\": \"admin@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Name").value("admin"))
                .andExpect(jsonPath("$.Email").value("admin@example.com"));
    }

    @Test
    public void testGetAllAdmins() throws Exception {
        when(adminService.getAllAdmins()).thenReturn(Collections.singletonList(adminResponseDTO));

        mockMvc.perform(get("/api/admins"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].Name").value("admin"))
                .andExpect(jsonPath("$[0].Email").value("admin@example.com"));
    }

    @Test
    public void testGetAdminById() throws Exception {
        when(adminService.getAdminById(anyLong())).thenReturn(adminResponseDTO);

        mockMvc.perform(get("/api/admins/getadmin/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Name").value("admin"))
                .andExpect(jsonPath("$.Email").value("admin@example.com"));
    }




    @Test
    public void testUpdateAdmin() throws Exception {
        when(adminService.updateAdmin(anyLong(), any(AdminDTO.class))).thenReturn(adminResponseDTO);

        mockMvc.perform(put("/api/admins/update/admin/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"admin\", \"email\": \"admin@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Name").value("admin"))
                .andExpect(jsonPath("$.Email").value("admin@example.com"));
    }

    @Test
    public void testDeleteAdmin() throws Exception {
        mockMvc.perform(delete("/api/admins/delete/admin/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testCreateTeacher() throws Exception {
        when(userService.teacherEmailExists(any(String.class))).thenReturn(false);
        TeacherResponseDTO teacherResponseDTO = new TeacherResponseDTO();
        teacherResponseDTO.setEmail("teacher@example.com");
        when(userService.createTeacher(any(TeacherDTO.class))).thenReturn(teacherResponseDTO);

        mockMvc.perform(post("/api/admins/create/teachers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"teacher@example.com\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.Email").value("teacher@example.com"));
    }

    @Test
    public void testDeleteTeacher() throws Exception {
        mockMvc.perform(delete("/api/admins/delete/teacher/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testUpdateTeacher() throws Exception {
        when(teacherService.updateTeacher(anyLong(), any(TeacherDTO.class))).thenReturn(teacherDTO);

        mockMvc.perform(put("/api/admins/update/teacher/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"teacher@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.teacher_email").value("teacher@example.com"));
    }

    @Test
    public void testCreateStudent() throws Exception {
        when(userService.studentEmailExists(any(String.class))).thenReturn(false);
        StudentResponseDTO studentResponseDTO = new StudentResponseDTO();
        studentResponseDTO.setEmail("student@example.com");
        when(userService.createStudent(any(StudentDTO.class))).thenReturn(studentResponseDTO);

        mockMvc.perform(post("/api/admins/create/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"student@example.com\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("student@example.com"));
    }

    @Test
    public void testUpdateStudent() throws Exception {
        when(studentService.updateStudent(anyLong(), any(StudentDTO.class))).thenReturn(studentDTO);

        mockMvc.perform(put("/api/admins/update/student/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"student@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("student@example.com"));
    }

    @Test
    public void testDeleteStudent() throws Exception {
        mockMvc.perform(delete("/api/admins/deleteStudent"))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted Successfully"));
    }

    @Test
    public void testCreateCourse() throws Exception {
        when(courseService.createCourse(any(CourseDTO.class))).thenReturn(courseDTO);

        mockMvc.perform(post("/api/admins/create/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Course Name\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.course_name").value("Course Name"));
    }

    @Test
    public void testGetCourseById() throws Exception {
        CourseResponseDTO courseResponseDTO = new CourseResponseDTO();
        courseResponseDTO.setCourseName("Course Name");
        when(courseService.getCourseById(anyLong())).thenReturn(courseResponseDTO);

        mockMvc.perform(get("/api/admins/getcourses/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.CourseName").value("Course Name"));
    }

    @Test
    public void testUpdateCourse() throws Exception {
        CourseResponseDTO courseResponseDTO = new CourseResponseDTO();
        courseResponseDTO.setCourseName("Updated Course Name");
        when(courseService.updateCourse(anyLong(), any(CourseDTO.class))).thenReturn(courseResponseDTO);

        mockMvc.perform(put("/api/admins/update/courses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Updated Course Name\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.CourseName").value("Updated Course Name"));
    }

    @Test
    public void testDeleteCourse() throws Exception {
        CourseResponseDTO courseResponseDTO = new CourseResponseDTO();
        courseResponseDTO.setCourseName("Deleted Course");
        when(courseService.deleteCourse(anyLong())).thenReturn(courseResponseDTO);

        mockMvc.perform(delete("/api/admins/delete/courses/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.CourseName").value("Deleted Course"));
    }


    @Test
    public void testAssignTeacherToCourse() throws Exception {
        CourseResponseDTO mockResponse = new CourseResponseDTO();
        mockResponse.setStatusMessage("Teacher assigned successfully");

        when(courseService.assignTeacherToCourse(4L, 1L))
                .thenReturn(mockResponse);

        mockMvc.perform(put("/courses/1/assign-teacher/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusMessage").value("Teacher assigned successfully"));
    }
}
