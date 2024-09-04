package com.newspringboot.newcms.Repository;

import com.newspringboot.newcms.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RoleRepo extends JpaRepository<Role,Long> {
   Optional<Role> findByRoleId(Long roleId);

   Role findByRoleName(String roleName);
}
