package com.newspringboot.newcms.Service;



import com.newspringboot.newcms.DTO.AdminDTO;
import com.newspringboot.newcms.DTO.AdminResponseDTO;
import com.newspringboot.newcms.Exception.AdminDuplicateException;
import com.newspringboot.newcms.Exception.AdminNotFoundException;
import com.newspringboot.newcms.Model.Admin;
import com.newspringboot.newcms.Repository.AdminRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ModelMapper modelMapper;


    public AdminResponseDTO saveAdmin(AdminDTO adminDTO) {
        AdminResponseDTO response = new AdminResponseDTO();
        try {
            Admin admin = modelMapper.map(adminDTO, Admin.class);
            Admin savedAdmin = adminRepository.save(admin);
            response = modelMapper.map(savedAdmin, AdminResponseDTO.class);
            response.setStatusMessage("Admin created successfully");
        } catch (DataIntegrityViolationException ex) {
            throw new AdminDuplicateException("Admin with the given email or username already exists.");
        } catch (Exception ex) {
            response.setStatusMessage("An unexpected error occurred");
            response.getErrors().add(ex.getMessage());
        }
        return response;
    }


    public List<AdminResponseDTO> getAllAdmins() {
        List<Admin> admins = adminRepository.findAll();
        return admins.stream()
                .map(admin -> {
                    AdminResponseDTO response = modelMapper.map(admin, AdminResponseDTO.class);
                    response.setStatusMessage("Admins retrieved successfully");
                    return response;
                })
                .collect(Collectors.toList());
    }


    public AdminResponseDTO getAdminById(Long id) {
        AdminResponseDTO response = new AdminResponseDTO();
        try {
            Admin admin = adminRepository.findById(id)
                    .orElseThrow(() -> new AdminNotFoundException("Admin with ID " + id + " does not exist"));
            response = modelMapper.map(admin, AdminResponseDTO.class);
            response.setStatusMessage("Admin retrieved successfully");
        } catch (AdminNotFoundException ex) {
            response.setStatusMessage("Error: Admin not found");
            response.getErrors().add(ex.getMessage());
        } catch (Exception ex) {
            response.setStatusMessage("An unexpected error occurred");
            response.getErrors().add(ex.getMessage());
        }
        return response;
    }

    public AdminResponseDTO updateAdmin(Long id, AdminDTO adminDTO) {
        AdminResponseDTO response = new AdminResponseDTO();
        try {
            if (!adminRepository.existsById(id)) {
                throw new AdminNotFoundException("Admin with ID " + id + " does not exist");
            }
            Admin admin = modelMapper.map(adminDTO, Admin.class);
            admin.setId(id);
            Admin updatedAdmin = adminRepository.save(admin);
            response = modelMapper.map(updatedAdmin, AdminResponseDTO.class);
            response.setStatusMessage("Admin updated successfully");
        } catch (AdminNotFoundException ex) {
            response.setStatusMessage("Error: Admin not found");
            response.getErrors().add(ex.getMessage());
        } catch (Exception ex) {
            response.setStatusMessage("An unexpected error occurred");
            response.getErrors().add(ex.getMessage());
        }
        return response;
    }


    public AdminResponseDTO deleteAdmin(Long id) {
        AdminResponseDTO response = new AdminResponseDTO();
        try {
            if (!adminRepository.existsById(id)) {
                throw new AdminNotFoundException("Admin with ID " + id + " does not exist");
            }
            adminRepository.deleteById(id);
            response.setStatusMessage("Admin deleted successfully");
        } catch (AdminNotFoundException ex) {
            response.setStatusMessage("Error: Admin not found");
            response.getErrors().add(ex.getMessage());
        } catch (Exception ex) {
            response.setStatusMessage("An unexpected error occurred");
            response.getErrors().add(ex.getMessage());
        }
        return response;
    }
}