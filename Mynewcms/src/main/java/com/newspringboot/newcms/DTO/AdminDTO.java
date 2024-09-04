package com.newspringboot.newcms.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;


public class AdminDTO {

    @JsonProperty(value = "AdminId")
    private Long id;
    @JsonProperty(value = "Name")
    private String adminName;
    @JsonProperty(value = "Email")
    private String Email;
    @JsonProperty(value = "Password")
    private String password;


    public AdminDTO() {
    }

    public AdminDTO(Long id, String adminName,String Email, String password) {
        this.id = id;
        this.adminName = adminName;
        this.Email = Email;
        this.password = password;

    }

    public AdminDTO(String adminName, String email, String password) {
    }


    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
