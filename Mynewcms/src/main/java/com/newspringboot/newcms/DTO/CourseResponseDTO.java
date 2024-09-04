package com.newspringboot.newcms.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class CourseResponseDTO {

    private CourseDTO course;

    public CourseDTO getCourse() {
        return course;
    }

    public void setCourse(CourseDTO course) {
        this.course = course;
    }

    @JsonProperty(value = "CourseId")
    private Long id;

    @JsonProperty(value = "CourseName")
    private String courseName;

    @JsonProperty(value = "Duration")
    private String courseDuration;

    @JsonProperty(value = "Description")
    private String courseDescription;

    @JsonProperty(value = "TeacherId")
    private Long teacherId;

    @JsonProperty(value = "StatusMessage")
    private String statusMessage;

    @JsonProperty(value = "Errors")
    private List<String> errors = new ArrayList<>();

    public CourseResponseDTO() {
    }

    public CourseResponseDTO(Long id, String courseName, String courseDuration, String courseDescription, Long teacherId, String statusMessage, List<String> errors) {
        this.id = id;
        this.courseName = courseName;
        this.courseDuration = courseDuration;
        this.courseDescription = courseDescription;
        this.teacherId = teacherId;
        this.statusMessage = statusMessage;
        this.errors = errors;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDuration() {
        return courseDuration;
    }

    public void setCourseDuration(String courseDuration) {
        this.courseDuration = courseDuration;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}