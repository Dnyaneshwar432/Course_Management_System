package com.newspringboot.newcms.StudentTest;


import com.newspringboot.newcms.DTO.CourseDTO;
import com.newspringboot.newcms.DTO.StudentDTO;
import com.newspringboot.newcms.Model.Course;
import com.newspringboot.newcms.Model.Student;
import com.newspringboot.newcms.Repository.CourseRepository;
import com.newspringboot.newcms.Repository.StudentRepo;
import com.newspringboot.newcms.Service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private StudentRepo studentRepo;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private StudentService studentService;

    private Course course;
    private CourseDTO courseDTO;

    private Student student;

    private StudentDTO studentDTO;


    @BeforeEach
    void setUp() {
        course = new Course();
        course.setId(1L);
        course.setName("Mathematics");

        courseDTO = new CourseDTO();
        courseDTO.setId(1L);
        courseDTO.setName("Mathematics");
        courseDTO.setTeacherId(1L);

        studentDTO = new StudentDTO();
        studentDTO.setName("John Doe");
        studentDTO.setEmail("john.doe@example.com");
        studentDTO.setPassword("password");

        student = new Student();
        student.setName("John Doe");
        student.setEmail("john.doe@example.com");
        student.setPassword("password");
    }

    @Test
    void testGetAllCourses() {
        List<Course> courses = List.of(course);
        when(courseRepository.findAll()).thenReturn(courses);
        when(modelMapper.map(course, CourseDTO.class)).thenReturn(courseDTO);

        List<CourseDTO> result = studentService.getAllCourses();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Mathematics", result.get(0).getName());

        verify(courseRepository, times(1)).findAll();
        verify(modelMapper, times(1)).map(course, CourseDTO.class);
    }


    @Test
    void updateStudent_ShouldReturnUpdatedStudentDTO_WhenStudentExists() {
        Long studentId = 1L;

        // Mock behavior for when student exists
        when(studentRepo.findById(studentId)).thenReturn(java.util.Optional.of(student));
        when(studentRepo.save(Mockito.any(Student.class))).thenReturn(student);
        when(modelMapper.map(student, StudentDTO.class)).thenReturn(studentDTO);

        // Call the service method
        StudentDTO updatedStudentDTO = studentService.updateStudent(studentId, studentDTO);

        // Verify results
        assertEquals(studentDTO.getName(), updatedStudentDTO.getName());
        assertEquals(studentDTO.getEmail(), updatedStudentDTO.getEmail());
        assertEquals(studentDTO.getPassword(), updatedStudentDTO.getPassword());
    }

    @Test
    void updateStudent_ShouldThrowIllegalArgumentException_WhenStudentDoesNotExist() {
        Long studentId = 1L;

        // Mock behavior for when student does not exist
        when(studentRepo.findById(studentId)).thenReturn(java.util.Optional.empty());

        // Call the service method and assert exception
        assertThrows(IllegalArgumentException.class, () -> {
            studentService.updateStudent(studentId, studentDTO);
        });
    }
}
