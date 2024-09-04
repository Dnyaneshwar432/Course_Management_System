package com.newspringboot.newcms.RoleTest;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.newspringboot.newcms.Model.Role;
import com.newspringboot.newcms.Service.RoleService;
import com.newspringboot.newcms.Controller.RoleController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

@WebMvcTest(RoleController.class)
public class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoleService roleService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllRoles_success() throws Exception {
        // Given
        Role role1 = new Role();
        role1.setRoleId(1L);
        role1.setRoleName("Admin");

        Role role2 = new Role();
        role2.setRoleId(2L);
        role2.setRoleName("User");

        List<Role> roles = Arrays.asList(role1, role2);

        when(roleService.getAllRoles()).thenReturn(roles);

        // When & Then
        mockMvc.perform(get("/api/roles")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].roleId").value(1))
                .andExpect(jsonPath("$[0].roleName").value("Admin"))
                .andExpect(jsonPath("$[1].roleId").value(2))
                .andExpect(jsonPath("$[1].roleName").value("User"));
    }
}
