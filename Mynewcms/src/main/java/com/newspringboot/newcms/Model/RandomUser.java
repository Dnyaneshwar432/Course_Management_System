package com.newspringboot.newcms.Model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "RandomUser")
public class RandomUser {

    @Id
    @GeneratedValue
    private  Long id;

    @Column(name = "name")
    private String randonName;

}
