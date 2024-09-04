package com.newspringboot.newcms.Repository;


import com.newspringboot.newcms.Model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
