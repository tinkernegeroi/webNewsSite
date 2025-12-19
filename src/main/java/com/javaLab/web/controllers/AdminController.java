package com.javaLab.web.controllers;

import com.javaLab.web.dto.AdminCreateUserDTO;
import com.javaLab.web.dto.AdminEditUserDTO;
import com.javaLab.web.services.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllUsers() {
        return adminService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        return adminService.getUserById(id);
    }

    @PostMapping(value = "/create", consumes = "multipart/form-data")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> createUser(@ModelAttribute AdminCreateUserDTO dto) {
        return adminService.createUser(dto);
    }

    @PatchMapping(value = "/{id}", consumes = "multipart/form-data")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateUserAsAdmin(
            @PathVariable Long id,
            @ModelAttribute AdminEditUserDTO dto
    ) {
        return adminService.updateUser(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteUser(
            @PathVariable Long id
    ) {
        return adminService.deleteUserById(id);
    }
}
