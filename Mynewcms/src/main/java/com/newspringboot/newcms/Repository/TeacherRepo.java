package com.newspringboot.newcms.Repository;

import com.newspringboot.newcms.Model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepo extends JpaRepository<Teacher,Long> {


    boolean existsByEmail(String email);
}