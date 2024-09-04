package com.newspringboot.newcms.DTO;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Id;

public class TeacherDTO {
    @JsonProperty(value = "teacher_name")
    private String name;
    @JsonProperty(value = "teacher_email")
    private String email;
    @JsonProperty(value = "password")
    private String password;


    public TeacherDTO() {
    }

    public TeacherDTO(Long id,String name,String email,String password) {
        this.name = name;
        this.email=email;
        this.password=password;
    }


    public TeacherDTO(Long id, String name,String email) {
        this.name = name;
        this.email=email;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}