package com.newspringboot.newcms.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "Admin")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "admin_name",nullable = false)
    @NotEmpty(message = "Admin Name is required")
    private String adminName;

    @NotEmpty(message = "Admin email is required")
    @Column(name = "admin_email",nullable = false,unique = true)
    private String Email;

    @Column(name = "admin_password",nullable = false)
    @NotEmpty(message = "Admin Password is required")
    private  String password;

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public Admin() {
    }

    public Admin(Long id,String admin_name, String password) {
        this.id = id;
        this.adminName = admin_name;
        this.password = password;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }
}
