package com.newspringboot.newcms.CourseTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import com.newspringboot.newcms.DTO.CourseDTO;
import com.newspringboot.newcms.DTO.CourseResponseDTO;
import com.newspringboot.newcms.Model.Course;
import com.newspringboot.newcms.Model.Teacher;
import com.newspringboot.newcms.Repository.CourseRepository;
import com.newspringboot.newcms.Repository.TeacherRepo;
import com.newspringboot.newcms.Service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;



@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private TeacherRepo teacherRepo;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CourseService courseService;

    private CourseDTO courseDTO;
    private Course course;
    private Teacher teacher;
    private CourseResponseDTO courseResponseDTO;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // Initialize objects
        teacher = new Teacher();
        teacher.setId(1L);
        teacher.setName("Teacher Name");

        courseDTO = new CourseDTO();
        courseDTO.setId(1L);
        courseDTO.setName("Course Name");
        courseDTO.setTeacherId(1L);

        course = new Course();
        course.setId(1L);
        course.setName("Course Name");
        course.setTeacher(teacher);

        courseResponseDTO = new CourseResponseDTO();
    }

    @Test
    public void testConvertToCourseDTO() {
        lenient().when(modelMapper.map(any(Course.class), any())).thenReturn(courseDTO);

        CourseDTO result = courseService.convertToCourseDTO(course);

        assertNotNull(result);
        assertEquals(courseDTO.getId(), result.getId());
    }

    @Test
    public void testCreateCourse_success() {
        lenient().when(teacherRepo.findById(anyLong())).thenReturn(Optional.of(teacher));
        lenient().when(courseRepository.save(any(Course.class))).thenReturn(course);
        lenient().when(modelMapper.map(any(Course.class), any())).thenReturn(courseDTO);

        CourseDTO result = courseService.createCourse(courseDTO);

        assertNotNull(result);
        assertEquals(courseDTO.getId(), result.getId());
    }

    @Test
    public void testCreateCourse_teacherNotFound() {
        when(teacherRepo.findById(anyLong())).thenReturn(Optional.empty());

        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> courseService.createCourse(courseDTO),
                "Expected createCourse() to throw, but it didn't"
        );

        assertEquals("Teacher not found with id: 1", thrown.getMessage());
    }

    @Test
    public void testGetCourseById_success() {
        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course));
        when(modelMapper.map(any(Course.class), any())).thenReturn(new CourseResponseDTO());

        CourseResponseDTO result = courseService.getCourseById(1L);

        assertNotNull(result);
        assertEquals("Course retrieved successfully", result.getStatusMessage());
    }

    @Test
    public void testGetCourseById_courseNotFound() {
        when(courseRepository.findById(anyLong())).thenReturn(Optional.empty());

        CourseResponseDTO result = courseService.getCourseById(1L);

        assertNotNull(result);
        assertEquals("Error: Course with ID 1 does not exist", result.getStatusMessage());
    }

    @Test
    public void testGetCourseById_unexpectedError() {
        // Arrange
        when(courseRepository.findById(anyLong())).thenThrow(new RuntimeException("An unexpected error occurred"));

        // Act
        CourseResponseDTO result = courseService.getCourseById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Error: An unexpected error occurred", result.getStatusMessage().trim());
    }

    @Test
    public void testUpdateCourse_success() {
        // Arrange
        when(courseRepository.existsById(anyLong())).thenReturn(true);
        when(modelMapper.map(any(CourseDTO.class), eq(Course.class))).thenReturn(course);
        when(courseRepository.save(any(Course.class))).thenReturn(course);
        when(modelMapper.map(any(Course.class), eq(CourseResponseDTO.class))).thenReturn(courseResponseDTO);
        courseResponseDTO.setStatusMessage("Course updated successfully");

        // Act
        CourseResponseDTO result = courseService.updateCourse(1L, courseDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Course updated successfully", result.getStatusMessage());
        verify(courseRepository).save(course);
    }


    @Test
    public void testUpdateCourse_courseNotFound() {
        when(courseRepository.existsById(anyLong())).thenReturn(false);

        CourseResponseDTO result = courseService.updateCourse(1L, courseDTO);

        assertNotNull(result);
        assertEquals("Error: Course with ID 1 does not exist", result.getStatusMessage());
    }

    @Test
    public void testUpdateCourse_unexpectedError() {
        when(courseRepository.existsById(anyLong())).thenReturn(true);
        when(modelMapper.map(any(CourseDTO.class), eq(Course.class))).thenReturn(new Course()); // Ensure map() returns a non-null Course object
        when(courseRepository.save(any(Course.class))).thenThrow(new RuntimeException("Database error"));

        CourseResponseDTO result = courseService.updateCourse(1L, courseDTO);

        assertNotNull(result);
        assertEquals("Error: Database error", result.getStatusMessage());
    }


    @Test
    public void testDeleteCourse_success() {
        when(courseRepository.existsById(anyLong())).thenReturn(true);

        CourseResponseDTO result = courseService.deleteCourse(1L);

        assertNotNull(result);
        assertEquals("Course deleted successfully", result.getStatusMessage());
    }

    @Test
    public void testDeleteCourse_courseNotFound() {
        when(courseRepository.existsById(anyLong())).thenReturn(false);

        CourseResponseDTO result = courseService.deleteCourse(1L);

        assertNotNull(result);
        assertEquals("Error: Course with ID 1 does not exist", result.getStatusMessage());
    }

    @Test
    public void testDeleteCourse_unexpectedError() {
        when(courseRepository.existsById(anyLong())).thenReturn(true);
        doThrow(new RuntimeException("Database error")).when(courseRepository).deleteById(anyLong());

        CourseResponseDTO result = courseService.deleteCourse(1L);

        assertNotNull(result);
        assertEquals("Error: Database error", result.getStatusMessage());
    }

    @Test
    public void testAssignTeacherToCourse_success() {
        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course));
        when(teacherRepo.findById(anyLong())).thenReturn(Optional.of(teacher));
        when(courseRepository.save(any(Course.class))).thenReturn(course);
        when(modelMapper.map(any(Course.class), any())).thenReturn(new CourseResponseDTO());

        CourseResponseDTO result = courseService.assignTeacherToCourse(1L, 1L);

        assertNotNull(result);
        assertEquals("Teacher assigned successfully", result.getStatusMessage());
    }

    @Test
    public void testAssignTeacherToCourse_courseNotFound() {
        when(courseRepository.findById(anyLong())).thenReturn(Optional.empty());

        CourseResponseDTO result = courseService.assignTeacherToCourse(1L, 1L);

        assertNotNull(result);
        assertEquals("Error: Course with ID 1 does not exist", result.getStatusMessage());
    }

    @Test
    public void testAssignTeacherToCourse_teacherNotFound() {
        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course));
        when(teacherRepo.findById(anyLong())).thenReturn(Optional.empty());

        CourseResponseDTO result = courseService.assignTeacherToCourse(1L, 1L);

        assertNotNull(result);
        assertEquals("Error: Teacher with ID 1 does not exist", result.getStatusMessage());
    }

    @Test
    public void testAssignTeacherToCourse_unexpectedError() {
        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course));
        when(teacherRepo.findById(anyLong())).thenReturn(Optional.of(teacher));
        when(courseRepository.save(any(Course.class))).thenThrow(new RuntimeException("Database error"));

        CourseResponseDTO result = courseService.assignTeacherToCourse(1L, 1L);

        assertNotNull(result);
        assertEquals("Error: Database error", result.getStatusMessage());
    }
}
