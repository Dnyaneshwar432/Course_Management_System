package com.newspringboot.newcms.Repository;

import com.newspringboot.newcms.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepo extends JpaRepository<Student,Long> {

    boolean existsByEmail(String email);
}
