package com.newspringboot.newcms.DTO;



import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class AdminResponseDTO {

    @JsonProperty(value = "AdminId")
    private Long id;
    @JsonProperty(value = "Name")
    private String adminName;
    @JsonProperty(value = "Email")
    private String email;
    @JsonProperty(value = "Password")
    private String password;

    @JsonProperty(value = "Status")
    private String statusMessage = "";
    private List<String> errors = new ArrayList<>();

    public AdminResponseDTO() {
    }

    public AdminResponseDTO(Long id, String adminName, String email, String password, String statusMessage, List<String> errors) {
        this.id = id;
        this.adminName = adminName;
        this.email = email;
        this.password = password;
        this.statusMessage = statusMessage;
        this.errors = errors;
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