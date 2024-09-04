package com.newspringboot.newcms.Repository;


import com.newspringboot.newcms.Model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Long> {
}
