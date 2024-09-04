package com.newspringboot.newcms.Service;


import com.newspringboot.newcms.DTO.CourseDTO;
import com.newspringboot.newcms.DTO.TeacherDTO;
import com.newspringboot.newcms.DTO.TeacherResponseDTO;
import com.newspringboot.newcms.Exception.ResourceNotFoundException;
import com.newspringboot.newcms.Model.Course;
import com.newspringboot.newcms.Model.Teacher;
import com.newspringboot.newcms.Repository.TeacherRepo;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@Service
@Transactional
@Slf4j
public class TeacherService {

    private final TeacherRepo teacherRepository;

    @Autowired
    public TeacherService(TeacherRepo teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public List<TeacherResponseDTO> getAllTeachers() {
        log.debug("Fetching all teachers from repository");
        List<Teacher> teachers = teacherRepository.findAll();

        // Convert Teacher entities to TeacherDTOs
        return teachers.stream()
                .map(teacher -> convertToTeacherDTO(teacher))
                .collect(Collectors.toList());
    }

    public Optional<TeacherResponseDTO> getTeacherById(Long teacherId) {
        log.debug("Fetching teacher with ID: {}", teacherId);
        return teacherRepository.findById(teacherId)
                .map(teacher -> convertToTeacherDTO(teacher));
    }

    public List<CourseDTO> getCoursesByTeacherId(Long teacherId) {
        log.debug("Fetching courses for teacher with ID: {}", teacherId);
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with id " + teacherId));

        // Convert Course entities to CourseDTOs
        return teacher.getCourses().stream()
                .map(course -> convertToCourseDTO(course))
                .collect(Collectors.toList());
    }

    //update teacher method
    public TeacherDTO updateTeacher(Long teacherId, TeacherDTO teacherDTO) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found with id " + teacherId));

        teacher.setName(teacherDTO.getName());
        teacher.setEmail(teacherDTO.getEmail());
        teacher.setPassword(teacherDTO.getPassword());

        Teacher updatedTeacher = teacherRepository.save(teacher);

        return new TeacherDTO(updatedTeacher.getId(), updatedTeacher.getName(), updatedTeacher.getEmail(), updatedTeacher.getPassword());
    }



    //delete teacher method
    public void deleteTeacher(Long teacherId) {
        if (!teacherRepository.existsById(teacherId)) {
            throw new IllegalArgumentException("Teacher not found with id " + teacherId);
        }
        teacherRepository.deleteById(teacherId);
    }

    private TeacherResponseDTO convertToTeacherDTO(Teacher teacher) {
        return new TeacherResponseDTO(teacher.getId(), teacher.getName(), teacher.getEmail());
    }

    private CourseDTO convertToCourseDTO(Course course) {
        return new CourseDTO(course.getId(),course.getName(), course.getTeacher().getId());
    }
}