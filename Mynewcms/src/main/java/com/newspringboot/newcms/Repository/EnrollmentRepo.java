package com.newspringboot.newcms.Repository;

import com.newspringboot.newcms.Model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentRepo extends JpaRepository<Enrollment, Long> {
}
