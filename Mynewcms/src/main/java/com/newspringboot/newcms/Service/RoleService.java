package com.newspringboot.newcms.Service;


import com.newspringboot.newcms.Model.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {
    List<Role> getAllRoles();

}
