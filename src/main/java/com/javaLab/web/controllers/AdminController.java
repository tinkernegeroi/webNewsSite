package com.javaLab.web.controllers;


import com.javaLab.web.dto.AdminCreateUserDTO;
import com.javaLab.web.dto.AdminEditUserDTO;
import com.javaLab.web.services.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @PostMapping(value = "/create", consumes = "multipart/form-data")
    public ResponseEntity<String> createUser(@ModelAttribute AdminCreateUserDTO dto) {
        return adminService.createUser(dto);
    }

    @PatchMapping(value = "/update/{id}", consumes = "multipart/form-data")
    public ResponseEntity<String> updateUserAsAdmin(
            @PathVariable Long id,
            @ModelAttribute AdminEditUserDTO dto
    ) {
        return adminService.updateUser(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(
            @PathVariable Long id
    ) {
        return adminService.deleteUserById(id);
    }
}
