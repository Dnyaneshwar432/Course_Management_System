package com.newspringboot.newcms.Service;

import com.newspringboot.newcms.Model.Role;
import com.newspringboot.newcms.Repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleRepo roleRepo;

 public Optional<Role> getRoleById(Long roleId){
     return roleRepo.findByRoleId(roleId);
 }
    @Override
    public List<Role> getAllRoles() {
        return roleRepo.findAll();
    }

}
