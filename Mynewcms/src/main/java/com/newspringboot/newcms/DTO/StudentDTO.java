package com.newspringboot.newcms.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StudentDTO {

    @JsonProperty(value = "student_name")
    private String name;
    @JsonProperty(value = "email")
    private String email;

    private String password;

    public StudentDTO(){

    }

    public StudentDTO(String name, String email){
        this.name=name;
        this.email=email;

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




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
